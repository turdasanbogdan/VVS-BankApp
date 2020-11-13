package ro.sd.a2.controller;

import org.apache.logging.log4j.core.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.AccountFormDto;
import ro.sd.a2.dto.AccountOverviewDto;
import ro.sd.a2.entity.*;
import ro.sd.a2.mapper.AccountFormMapper;
import ro.sd.a2.mapper.AccountOverviewMapper;
import ro.sd.a2.mapper.UserDetailsMapper;
import ro.sd.a2.service.*;

import javax.jws.WebParam;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controller dealing with the main requests of a user.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private SpendingAccountService spendingAccountService;
    @Autowired
    private SavingAccountService savingAccountService;
    @Autowired
    private ValuteService valuteService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);


    @GetMapping("")
    public ModelAndView home(Authentication authentication, @RequestParam(value = "err", required = false) String err,
                             @RequestParam(value = "errMsg", required = false) String errMsg){
        log.info("Request on home access from "+authentication.getName()+".");
        ModelAndView mav  = new ModelAndView();
        User user = userService.findByEmail(authentication.getName());
        mav.addObject("userDetailsDto", UserDetailsMapper.convertToDto(user));
        if(err!=null)
        {
            mav.addObject("err", true).addObject("errMsg", errMsg);
        }
        mav.setViewName("home");
        return mav;
    }

    @GetMapping("/savingAccounts")
    public ModelAndView savingAccount(Authentication authentication){
        log.info("Request to see saving accounts of user "+authentication.getName()+".");
        ModelAndView mav  = new ModelAndView();
        mav.setViewName("savingAccounts");
        List<SavingAccount> accounts = savingAccountService.findByUserId(userService.findByEmail(authentication.getName()).getId());
        List<AccountOverviewDto> accountOverviewDtos = accounts.stream().map(AccountOverviewMapper::convertToDto).collect(Collectors.toList());
        mav.addObject("accountOverviewDtos", accountOverviewDtos);
        return mav;
    }

    @GetMapping("/spendingAccounts")
    public ModelAndView spendingAccount(Authentication authentication){
        log.info("Request on spending accounts from "+authentication.getName());
        ModelAndView mav  = new ModelAndView();
        mav.setViewName("/spendingAccounts");
        List<SpendingAccount> accounts = spendingAccountService.findByUserId(userService.findByEmail(authentication.getName()).getId());
        List<AccountOverviewDto> accountOverviewDtos = accounts.stream().map(AccountOverviewMapper::convertToDto).collect(Collectors.toList());
        mav.addObject("accountOverviewDtos", accountOverviewDtos);
        return mav;
    }

    @GetMapping("/createAccount")
    public ModelAndView createAccount(Authentication authentication, @RequestParam(value = "succ", required = false) String succ)
    {
        log.info("Request to see the account creation page from "+authentication.getName()+".");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("accountForm");
        mav.addObject("accountFormDto", new AccountFormDto());
        List<String> valutes = valuteService.findAll().stream().map(Valute::getName).collect(Collectors.toList());
        mav.addObject("valutes", valutes);
        if(succ != null) mav.addObject("succ", true).addObject("succMsg", "Account created successfully.");
        return mav;
    }

    @PostMapping("/createAccount")
    public ModelAndView createAccount(@ModelAttribute(name = "accountFormDto") AccountFormDto accountFormDto, Authentication authentication)
    {
        log.info("Request to create a "+accountFormDto.getType() +" account of "+accountFormDto.getValute()+" from user "+authentication.getName()+".");
        ModelAndView mav = new ModelAndView();
        User user = userService.findByEmail(authentication.getName());
        Valute valute = valuteService.findByName(accountFormDto.getValute());
        Account account = AccountFormMapper.convertAccountFormDtoToAccount(accountFormDto, user, valute);
        Account result = accountService.insert(account);

        if(result == null)
        {
            log.info("Users has too many accounts of the same type.");
            mav.setViewName("redirect:/home");
            return mav.addObject("err", true).addObject("errMsg", "You have too many accounts of one type.");
        }
        mav.setViewName("redirect:/home/createAccount");
        return mav.addObject("succ", true);
    }

}
