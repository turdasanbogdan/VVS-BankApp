package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.BillGeneratorDto;
import ro.sd.a2.dto.UserDetailsDto;
import ro.sd.a2.entity.Bill;
import ro.sd.a2.entity.Company;
import ro.sd.a2.entity.User;
import ro.sd.a2.mapper.UserDetailsMapper;
import ro.sd.a2.service.BillService;
import ro.sd.a2.service.CompanyService;
import ro.sd.a2.service.UserService;
import ro.sd.a2.utils.generators.BillGenerator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller dealing with the requests of the admin to see users and put bills.
 */
@Controller
@RequestMapping("/admin/users")
public class UsersController {
    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private BillService billService;

    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    @GetMapping("")
    public ModelAndView seeUsers(Authentication authentication, @RequestParam(value = "succMsg", required = false) String succMsg,
                                 @RequestParam(value = "errMsg", required = false) String errMsg){

        log.info("Request on users page from "+authentication.getName()+".");
        ModelAndView mav  = new ModelAndView("users");
        mav.addObject("billGeneratorDto", new BillGeneratorDto());
        List<UserDetailsDto> users = userService.findAllUsers().stream().map(UserDetailsMapper::convertToDto).collect(Collectors.toList());
        mav.addObject("users", users);
        if(succMsg!=null) mav.addObject("succ", true).addObject("succMsg", succMsg);
        if(errMsg!=null) mav.addObject("err", true).addObject("errMsg", errMsg);

        return mav;
    }

    @PostMapping("")
    public ModelAndView addBills( Authentication authentication, @ModelAttribute(name = "billGeneratorDto") BillGeneratorDto billGeneratorDto,
                                 BindingResult bindingResult){

        ModelAndView mav  = new ModelAndView();
        if(bindingResult.hasErrors())
        {
            mav.setViewName("users");
            mav.addObject("billGeneratorDto",billGeneratorDto);
            mav.addObject("users", userService.findAllUsers().stream().map(UserDetailsMapper::convertToDto).collect(Collectors.toList()));
            return mav;
        }
        log.info("Request to generate "+billGeneratorDto.getValue()+" bills from "+authentication.getName()+ " to user "+billGeneratorDto.getUsername());

        mav.setViewName("redirect:/admin/users");
        List<Company> companies = companyService.findAll();
        User user = userService.findByEmailOrUsername(billGeneratorDto.getUsername());
        if(user == null)
        {
            log.info("User does not exist.");
            return mav.addObject("errMsg", "User does not exist.");
        }
        if(billGeneratorDto.getValue()<0)
        {
            log.info("Invalid number of bills.");
            return mav.addObject("errMsg", "Invalid number of bills.");
        }
        List<Bill> bills = BillGenerator.generate(user, billGeneratorDto.getValue(), companies);
        bills.forEach(billService::insert);
        return mav.addObject("succMsg", "Operation successful");
    }
}
