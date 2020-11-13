package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.TransactionTableDto;
import ro.sd.a2.dto.TransactionsDatesDto;
import ro.sd.a2.mapper.TransactionTableMapper;
import ro.sd.a2.service.TransactionService;
import ro.sd.a2.service.UserService;
import ro.sd.a2.service.EmailServiceImpl;
import ro.sd.a2.utils.strategies.CsvStrategy;
import ro.sd.a2.utils.strategies.PdfStrategy;
import ro.sd.a2.utils.strategies.Strategy;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller to process the requests of the user on his transactions.
 */
@Controller
@RequestMapping("home/transactions")
public class UserTransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserTransactionController.class);

    private Date firstTimeOfDay(Date date)
    {
        Date newDate = new Date(date.getTime());
        newDate.setMinutes(0);
        newDate.setSeconds(0);
        newDate.setHours(0);
        return newDate;
    }

    private Date lastTimeOfDay(Date date)
    {
        Date newDate = new Date(date.getTime());
        newDate.setMinutes(59);
        newDate.setSeconds(59);
        newDate.setHours(23);
        return newDate;
    }

    @GetMapping("")
    public ModelAndView seeTransactions(Authentication authentication)
    {
        log.info("User "+authentication.getName()+" wants to see the transactions of the current date.");
        ModelAndView mav = new ModelAndView();
        TransactionsDatesDto transactionsDatesDto = new TransactionsDatesDto();
        transactionsDatesDto.setEndDate(lastTimeOfDay(firstTimeOfDay(new Date())));
        transactionsDatesDto.setStartDate(firstTimeOfDay(lastTimeOfDay(new Date())));
        List<TransactionTableDto> dtos = transactionService.getFromTimeIntervalAndUser(transactionsDatesDto.getStartDate(), transactionsDatesDto.getEndDate(),
                userService.findByEmail(authentication.getName())).stream().map(TransactionTableMapper::convertToDto).collect(Collectors.toList());

        mav.addObject("transactions",dtos);
        mav.addObject("transactionsDatesDto", transactionsDatesDto);
        mav.setViewName("transactions");
        return mav;
    }

    @PostMapping("")
    public ModelAndView filterTransaction(@ModelAttribute(name = "transactionsDatesDto")TransactionsDatesDto transactionsDatesDto, Authentication authentication)
    {
        log.info("Strategia ceruta pentru export este "+ transactionsDatesDto.getStrategy()+".");
        ModelAndView mav = new ModelAndView();
        transactionsDatesDto.setStartDate(firstTimeOfDay(transactionsDatesDto.getStartDate()));
        transactionsDatesDto.setEndDate(lastTimeOfDay(transactionsDatesDto.getEndDate()));
        if(transactionsDatesDto.getStartDate().getTime() > transactionsDatesDto.getEndDate().getTime())
        {
            return mav.addObject("errMsg", "Bad dates.").addObject("err", true);
        }
        List<TransactionTableDto> dtos  = transactionService.getFromTimeIntervalAndUser(transactionsDatesDto.getStartDate(), transactionsDatesDto.getEndDate(),
            userService.findByEmail(authentication.getName())).stream().map(TransactionTableMapper::convertToDto).collect(Collectors.toList());
        mav.addObject("transactions", dtos);
        mav.addObject("transactionsDatesDto", transactionsDatesDto);
        mav.setViewName("transactions");

        Strategy strategy = retrieveStrategy(transactionsDatesDto.getStrategy());
        if(strategy==null) return mav.addObject("succMsg", "Operation successful.").addObject("succ", true);

        try{
            emailService.sendMessageWithAttachment(authentication.getName(), "Transactions details", "You can see the transactions" +
                    " from "+transactionsDatesDto.getStartDate() + " until "+transactionsDatesDto.getEndDate(), strategy.generate(dtos));
        }
        catch(Exception e)
        {
            log.info("We got an error in the matrix.");
            return mav.addObject("errMsg", "Error in the matrix.").addObject("err", true);
        }
        return mav.addObject("succMsg", "Operation successful.").addObject("succ", true);
    }

    private Strategy retrieveStrategy(String s)
    {
        Strategy strategy=null;
        if(s.equals("CSV")) strategy = new CsvStrategy();
        else if(s.equals("PDF")) strategy = new PdfStrategy();
        return strategy;
    }
}
