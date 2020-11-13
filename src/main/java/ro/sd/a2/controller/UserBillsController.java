package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.BillDetailsDto;
import ro.sd.a2.dto.PaymentDetailsDto;
import ro.sd.a2.entity.Account;
import ro.sd.a2.entity.Bill;
import ro.sd.a2.entity.Exchange;
import ro.sd.a2.entity.Transaction;
import ro.sd.a2.mapper.BillDetailsMapper;
import ro.sd.a2.service.*;
import ro.sd.a2.utils.builder.TransactionBuilder;
import ro.sd.a2.service.EmailServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller dealing with the requests of the user on his bills.
 */
@Controller
@RequestMapping("home/userBills")
public class UserBillsController {
    @Autowired
    private BillService billService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private ExchangeService exchangeService;
    private static final Logger log = LoggerFactory.getLogger(UserBillsController.class);


    @GetMapping("")
    public ModelAndView seeBills(Authentication authentication, @RequestParam(value = "succMsg", required = false) String succMsg,
                                 @RequestParam(value = "errMsg", required = false) String errMsg){
        log.info("User "+authentication.getName()+" wants to see his bills.");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("userBills");
        List<BillDetailsDto> billOverviews = billService.findAllByUserId(userService.findByEmail(authentication.getName()).getId())
                .stream().filter(e->e.getTransaction()==null).map(BillDetailsMapper::convertToDto).collect(Collectors.toList());
        List<String> accountsIbans = accountService.findByUserId(userService.findByEmail(authentication.getName()).getId())
                .stream().map(Account::getIban).collect(Collectors.toList());
        mav.addObject("billOverviews", billOverviews);
        mav.addObject("accountsIbans", accountsIbans);
        mav.addObject("paymentDetailsDto", new PaymentDetailsDto());
        if(errMsg != null) mav.addObject("err", true).addObject("errMsg", errMsg);
        if(succMsg != null)mav.addObject("succ", true).addObject("succMsg", succMsg);
        return mav;
    }

    @PostMapping("/pay")
    public ModelAndView payBill(@ModelAttribute (name = "paymentDetailsDto") PaymentDetailsDto paymentDetailsDto, Authentication authentication){
        log.info("User "+authentication.getName()+" wants to pay bill "+paymentDetailsDto.getBillId() +
                " with account "+paymentDetailsDto.getAccountIban());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/home/userBills");
        Account account = accountService.findByIban(paymentDetailsDto.getAccountIban());
        Bill bill = billService.findById(paymentDetailsDto.getBillId()).orElse(null);
        if(bill == null) {log.info("Bill inexistant."); return mav;}
        float ratio = 1.0f;
        if(!bill.getCompany().getValute().getId().equals(account.getValute().getId()))
        {
            Exchange exchange = exchangeService.findExchange(bill.getCompany().getValute(), account.getValute());
            ratio = exchange.getRatio();
        }
        Transaction transaction = TransactionBuilder.buildTransaction(bill, account, ratio);
        if(transaction==null)
        {
            log.info("Cannot make transaction. Account sum is too low.");
            return mav.addObject("errMsg", "Account sum too low.");
        }
        billService.update(bill);
        accountService.update(account);
        transactionService.insert(transaction);
        emailService.sendSimpleMessage(authentication.getName(), "New transaction", "Your transaction to "+bill.getCompany().getName() + " of "+bill.getSum() +
                " "+bill.getCompany().getValute().getSymbol() + " successful.");
        return mav.addObject("succMsg", "Operation successful. Money remaining in the account "+account.getSum()+" "+account.getValute().getSymbol()+".");
    }
}
