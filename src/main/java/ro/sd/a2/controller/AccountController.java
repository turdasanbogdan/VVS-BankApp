package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.MoneyAdditionDto;
import ro.sd.a2.dto.TransactionTableDto;
import ro.sd.a2.entity.Account;
import ro.sd.a2.entity.Transaction;
import ro.sd.a2.mapper.AccountDetailsMapper;
import ro.sd.a2.mapper.TransactionTableMapper;
import ro.sd.a2.service.AccountService;
import ro.sd.a2.service.TransactionService;
import ro.sd.a2.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller used for operations based on the accounts of the currently logged in user.
 */
@Controller
@RequestMapping("/home/accounts")
public class AccountController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    /**
     * Method that processes the GET request on an account. If the account owner is not the authenticated user, we will redirect to
     * home displaying an error. Store all the content in DTOs and display the ModelAndView.
     * @param accountId the id of the requested account.
     * @param authentication the authentication of object of the user.
     * @param succ if we need to print a success message on this page. (probably after the user introduced his money
     * @return a mav with the information of this request.
     */
    @GetMapping("/{accountId}")
    public ModelAndView accountDetails(@PathVariable(value = "accountId") Integer accountId, Authentication authentication,
                                       @RequestParam(value = "succ", required = false) String succ)
    {
        log.info("User "+authentication.getName()+" wants to see his account with id "+accountId+".");

        List<TransactionTableDto> transactions = transactionService.findByAccountId(accountId)
                .stream().map(TransactionTableMapper::convertToDto).collect(Collectors.toList());
        Account account = accountService.findById(accountId).orElse(null);
        if(account== null)
        {
            log.info("Account doesn't exist.");
            return new ModelAndView("redirect:/home").addObject("err", true).addObject("errMsg", "Account doesn't exist.");
        }
        if(!account.getUser().getEmail().equals(authentication.getName()))
        {
            log.info("User tried to access other account.");
            return new ModelAndView("redirect:/home").addObject("err", true).addObject("errMsg", "Account doesn't exist.");
        }

        ModelAndView mav = new ModelAndView();
        mav.addObject("transactions", transactions);
        mav.addObject("account", AccountDetailsMapper.convertToDto(account));
        mav.addObject("moneyAdditionDto", new MoneyAdditionDto());
        if (succ != null) {
            mav.addObject("succ", succ);
            mav.addObject("succMsg", "Deposit done.");
        }

        mav.setViewName("accountDetails");
        return mav;
    }

    /**
     * Method used in order for the user to add money to his account. If the account doesn't exist or the account doesn't belong to the
     * currently authenticated user, we return to home with an error message. We add the wanted sum to the account (processing comissions and
     * months for the saving account) and then redirect to the detailed view of the account with a success message.
     */
    @PostMapping("/{accountId}/pay")
    public ModelAndView accountDetails(@PathVariable(value = "accountId") Integer accountId, @ModelAttribute(name = "moneyAdditionDto") MoneyAdditionDto moneyAdditionDto,
                                       Authentication authentication)
    {
        log.info("User "+authentication.getName()+" wants to see his account with id "+accountId+".");
        ModelAndView mav = new ModelAndView();
        log.info("Money addition request on account id "+accountId);
        log.info("Amount of money is " + moneyAdditionDto.getSum());
        log.info("Number of months is " + moneyAdditionDto.getNumberOfMonths());
        Account account = accountService.findById(accountId).orElse(null);
        if(account== null)
        {
            log.info("Account doesn't exist.");
            return new ModelAndView("redirect:/home").addObject("err", true)
                    .addObject("errMsg", "Account doesn't exist.");
        }
        if(!account.getUser().getEmail().equals(authentication.getName()))
        {
            log.info("User tried to access other account.");
            return new ModelAndView("redirect:/home").addObject("err", true)
                    .addObject("errMsg", "Account doesn't exist.");
        }
        account.deposit(moneyAdditionDto.getSum(), moneyAdditionDto.getNumberOfMonths());
        log.info("The new sum is " + account.getSum());

        accountService.update(account);
        mav.setViewName("redirect:/home/accounts/{accountId}");
        mav.addObject("succ", true);
        return mav;
    }


}
