package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.dto.PasswordFormDto;
import ro.sd.a2.dto.UserLoginDto;
import ro.sd.a2.dto.UserRegisterDto;
import ro.sd.a2.entity.ResetPassword;
import ro.sd.a2.entity.User;
import ro.sd.a2.mapper.UserRegisterMapper;
import ro.sd.a2.service.AttemptsService;
import ro.sd.a2.service.ResetPasswordService;
import ro.sd.a2.service.UserService;
import ro.sd.a2.service.EmailServiceImpl;

import javax.validation.Valid;
import java.util.Date;


/**
 * Controller of processing the requests of unauthenticated users.
 */
@Controller
public class FirstController {

    @Autowired
    private EmailServiceImpl emailService;

    private static final Logger log = LoggerFactory.getLogger(FirstController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private AttemptsService attemptsService;

    /**
     * request to display the login page.
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "errMsg", required = false) String errMsg,
                              @RequestParam(value = "succMsg", required = false) String succMsg)
    {
        log.info("Get request on login page.");

        ModelAndView mav = new ModelAndView();
        mav.addObject(new UserLoginDto());
        if(succMsg!=null) mav.addObject("succ", true).addObject("succMsg", succMsg);
        if(errMsg!=null) mav.addObject("err", true).addObject("errMsg", errMsg);
        mav.setViewName("login");
        return mav;
    }

    /**
     * Request to display yhe register page.
     */
    @GetMapping("/register")
    public ModelAndView register()
    {
        log.info("Get request on register page.");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("register");
        mav.addObject("userRegisterDto", new UserRegisterDto());
        return mav;
    }

    /**
     * Request to make the registration. If there are no errors (in the input, no user with same email, etc) the user will be registered.
     * @param userRegisterDto DTO having the registration data needed
     * @param bindingResult Object with the errors in the input forms.
     * @return A new view.
     */
    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute(name="userRegisterDto") UserRegisterDto userRegisterDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            System.out.println(userRegisterDto.getUsername());
            ModelAndView mav = new ModelAndView("register");
            return mav;
        }
        log.info("Register post request.");
        if(!userRegisterDto.getPassword().equals(userRegisterDto.getPasswordConfirmation())) {
            log.info("Registration passwords do not match.");
            return new ModelAndView("register").addObject("err", true)
                    .addObject("errMsg", "Passwords not Equal");
        }

        User user = UserRegisterMapper.convertToEntity(userRegisterDto);
        String message = userService.registerNewUserAccount(user);
        if(!message.equals("OK"))
        {
            log.info("User saved in database: ERROR "+message);
            return new ModelAndView("register").addObject("err", true)
                    .addObject("errMsg", "Registration failed. "+message);
        }
        log.info("User saved in database: "+message);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/login");
        return mav;
    }

    /**
     * Request to access the home page. It can be a request from admin or from user. The method will check the authentication object in order
     * to determine if the request comes from an admin or user. If the user account is blocked, he will be redirected to login page with a
     * message.
     */
    @GetMapping("/default")
    public ModelAndView defaultAfterLogin(Authentication authentication) {
        log.info("User credentials for "+authentication.getName()+" OK.");
        if(attemptsService.isBlocked(authentication.getName()))
        {
            log.info("User account "+authentication.getName()+" blocked.");
            return new ModelAndView("redirect:/login").addObject("errMsg", "Contul este blocat.");
        }
        for(GrantedAuthority grantedAuthority:authentication.getAuthorities())
        {
            System.out.println(grantedAuthority.toString());
            System.out.println(grantedAuthority.toString().equals("ADMIN"));
            if(grantedAuthority.toString().equals("ADMIN")) return new ModelAndView("redirect:/admin");
        }
        return new ModelAndView("redirect:/home");
    }

    /**
     * Request to see the page where the user introduces a new email, in order to recieve the reset token.
     */
    @GetMapping("/forgetPassword")
    public ModelAndView showResetPasswordEmail(@RequestParam(name = "errMsg", required = false) String errMsg)
    {
        log.info("Password reset requested. ");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("resetPassword");
        mav.addObject("email", new String());
        if(errMsg!=null) mav.addObject("err", true).addObject("errMsg", errMsg);
        return mav;
    }


    /**
     * Request to generate the password reset token and send it via email.
     */
    @PostMapping("/forgetPassword")
    public ModelAndView resetPasswordSendToken(@RequestParam("email") String userEmail) {
        log.info("Reset password token requested on "+userEmail+".");
        User user = userService.findByEmail(userEmail);
        if(user==null) {
            log.info("There is no user with requested email "+userEmail+".");
            return new ModelAndView("redirect:/forgetPassword").addObject("errMsg", "There is no user with requested email.");
        }

        ResetPassword resetPassword = new ResetPassword(user);
        log.info("Created new reset password token with id "+resetPassword.getId()+".");
        resetPasswordService.insert(resetPassword);
        emailService.sendSimpleMessage(user.getEmail(), "Email reset Token", "The email reset token is " + "http://localhost:7799/app/changePassword?token=" +
                resetPassword.getId());
        log.info("Email with the reset id sent to user "+user.getEmail());
        return new ModelAndView("redirect:/login").addObject("succMsg", "Token sent.");

    }

    /**
     * Request to see the page of the password reset.
     */
    @GetMapping("/changePassword")
    public ModelAndView showResetPassword(@RequestParam("token") String token, @RequestParam(name = "errMsg", required = false) String errMsg)
    {

        log.info("Password reset request with token "+token);
        ResetPassword resetPassword = resetPasswordService.findById(token).orElse(null);
        if(resetPassword==null){
            log.info("Invalid reset token.");
            return new ModelAndView("redirect:/login").addObject("errMsg", "Invalid token.");
        }
        else if(resetPassword.getExpirationDate().getTime() < new Date().getTime()) {
            log.info("Token expired");
            return new ModelAndView("redirect:/login").addObject("errMsg", "Token expired.");
        }

        ModelAndView mav = new ModelAndView();
        PasswordFormDto passwordFormDto = new PasswordFormDto();
        mav.addObject("passwordFormDto", passwordFormDto);
        mav.addObject("token", token);
        mav.setViewName("changePassword");
        if(errMsg!=null) mav.addObject("err", true).addObject("errMsg", errMsg);
        return mav;
    }

    /**
     * Request to change the password.
     */
    @PostMapping("/changePassword/")
    public ModelAndView resetPassword(@RequestParam("token") String token, @ModelAttribute("passwordFormDto") PasswordFormDto passwordFormDto)
    {
        if(passwordFormDto.getPassword().length()<8)
        {
            log.info("Passwords must be 8 characters long.");
            return new ModelAndView("redirect:/changePassword").addObject("errMsg", "Passwords do not match.")
                    .addObject("token", token);
        }
       if(!passwordFormDto.getPassword().equals(passwordFormDto.getPasswordConfirmation()))
       {
           log.info("Passwords are not equal.");
           return new ModelAndView("redirect:/changePassword").addObject("errMsg", "Passwords not equal.")
                .addObject("token", token);

       }
       ResetPassword resetPassword = resetPasswordService.findById(token).orElse(null);
       if(resetPassword==null)
       {
           return new ModelAndView("redirect:/login").addObject("errMsg", "Token expired");
       }
       User user = resetPassword.getUser();
       userService.changePassword(user, passwordFormDto.getPassword());
       log.info("Password change successful.");
       resetPasswordService.delete(resetPassword);
       return new ModelAndView("redirect:/login").addObject("succMsg", "Change successful.");
    }
}
