package ro.sd.a2.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.AccountOverviewDto;
import ro.sd.a2.entity.Account;
import ro.sd.a2.entity.User;
import ro.sd.a2.mapper.AccountOverviewMapper;
import ro.sd.a2.service.AccountService;
import ro.sd.a2.service.UserService;
import ro.sd.a2.service.EmailServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller processing the main requests of the admin.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailServiceImpl emailService;

    /**
     * Request to see the main html of the admin, the home. It will also process all the messages coming from previous requests
     * and print them accordingly. It will also print a suggestive message.
     */
    @GetMapping("")
    public ModelAndView home(Authentication authentication, @RequestParam(value = "succMsg", required = false) String succMsg,
                             @RequestParam(value = "errMsg", required = false) String errMsg){

        log.info("Request on admin page from "+authentication.getName());
        ModelAndView mav  = new ModelAndView("admin");
        User user = new User();
        mav.addObject("name", authentication.getName());
        if(errMsg != null) mav.addObject("err", true).addObject("errMsg", errMsg);
        if(succMsg != null)mav.addObject("succ", true).addObject("succMsg", succMsg);
        return mav;
    }

    /**
     * Request to see all the accounts of a particular username. It will also display error or success messages if there are any.
     * It will use the username as path variable and display all his accounts. The admin will then choose to notify a user with a
     * negative sum in the account.
     */
    @GetMapping("/accounts/{username}")
    public ModelAndView seeAccounts(@PathVariable(value = "username") String username, @RequestParam(value = "succMsg", required = false) String succMsg,
                                    @RequestParam(value = "errMsg", required = false) String errMsg)
    {
        log.info("Request to see the accounts of "+username+".");
        User user = userService.findByUsername(username);
        if(user==null)
        {
            log.info("Username does not exist.");
            return new ModelAndView("redirect:/admin").addObject("errMsg", "Username doesn't exist.");
        }
        List<AccountOverviewDto> accounts = accountService.findByUserId(user.getId()).stream().map(AccountOverviewMapper::convertToDto).collect(Collectors.toList());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("adminAccountView");
        mav.addObject("accounts", accounts);
        mav.addObject("username", username);
        if(errMsg != null) mav.addObject("err", true).addObject("errMsg", errMsg);
        if(succMsg != null)mav.addObject("succ", true).addObject("succMsg", succMsg);
        return mav;
    }

    /**
     * Method to notify an user if his account has a negative sum. If the user doesn't exist, or the account doesn't exist we will redirect
     * to admin home page with error message. We use the EmailService class top send a simple text message with the information. Then we redirect
     * to the account view with the good message.
     */
    @GetMapping("/notifySum/{username}/{id}")
    public ModelAndView notify(@PathVariable(name = "username") String username, @PathVariable(name = "id") Integer accountId)
    {
        log.info("Cerere notificare cont "+username+" , contul "+accountId);
        User user = userService.findByUsername(username);
        if(user==null)
        {
            log.info("Username does not exist.");
            return new ModelAndView("redirect:/admin");
        }
        Account account = accountService.findById(accountId).orElse(null);
        if(account==null)
        {
            log.info("Account does not exist.");
            return new ModelAndView("redirect:/admin/accounts/{username}");
        }
        emailService.sendSimpleMessage(user.getEmail(), "Cont suma negativa", "Va informam ca aveti suma negativa" +
                " pe contul "+account.getIban());

        return new ModelAndView("redirect:/admin/accounts/{username}").addObject("succMsg", "Successfully notified");
    }


}
