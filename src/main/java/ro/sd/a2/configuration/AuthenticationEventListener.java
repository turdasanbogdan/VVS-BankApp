package ro.sd.a2.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import ro.sd.a2.controller.AccountController;

import ro.sd.a2.service.AttemptsService;
import ro.sd.a2.service.EmailServiceImpl;


/**
 * Authentication event listener class
 */
@Component
public class AuthenticationEventListener {

    @Autowired
    private AttemptsService attemptsService;

    @Autowired
    private EmailServiceImpl emailService;

    private boolean lastStateWasBlocked=false;

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    /**
     * Executes every time there is a wrong login in the site. Everytime, it will process the account on which the login failed
     * in order to block it after 3 attempts.
     * @param event
     */
    @EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {

        String email = (String) event.getAuthentication().getPrincipal();
        log.info(email + " has failed to login.");
        attemptsService.addAttempt(email);
        if(attemptsService.isBlocked(email))
        {
            log.info(email + " este blocat.");
            if(!lastStateWasBlocked)
            {
                log.info(email + " va primi mail.");
                emailService.sendSimpleMessage(email, "Account blocked", "Due to too many invalid entrances your account will be blocked" +
                        " for 30s. Thank you.");
                lastStateWasBlocked = true;
            }
        }
        else{

            lastStateWasBlocked=false;
        }

    }
}
