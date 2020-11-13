package ro.sd.a2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.Attempts;
import ro.sd.a2.mapper.UserDetailsMapper;
import ro.sd.a2.repository.AttemptsRepository;

import java.util.Date;

/**
 * Service class of the Attempts entity.
 */
@Service
public class AttemptsService {

    @Autowired
    private AttemptsRepository attemptsRepository;

    private static final Logger log = LoggerFactory.getLogger(AttemptsService.class);

    /**
     * Increments the attempt number for the row having the given email.
     * @param email The email of the entry where to add the new attempt.
     */
    public void addAttempt(String email) {
        Attempts attempts = attemptsRepository.findByEmail(email);
        if(attempts==null)
        {
            log.info("Create new attempt for user "+email);
            attempts = new Attempts();
            attempts.setLastAttemptDate(new Date());
            attempts.setEmail(email);
            attempts.setNumberOfAttempts(1);
            attempts.setBlocked(false);
            attemptsRepository.save(attempts);
        }
        else{
            if(!isBlocked(attempts))
            {
                attempts.setBlocked(false);
                if(attempts.getLastAttemptDate().getTime() + 10000 > new Date().getTime())
                {
                    attempts.setNumberOfAttempts(attempts.getNumberOfAttempts()+1);
                }
                else attempts.setNumberOfAttempts(1);
                attempts.setLastAttemptDate(new Date());
                if(attempts.getNumberOfAttempts()>=3) attempts.setBlocked(true);
                attemptsRepository.save(attempts);
            }
        }
    }

    /**
     * This method checks if the current account is blocked. It checks if there exist an entry in the table, if it is alreaady blocked. Then
     * it check if the block period expired.
     * @param attempts Attempts object we want to see if it is blocked.
     * @return true if it is blocked, false otherwise
     */
    public boolean isBlocked(Attempts attempts)
    {
        if(attempts==null) return false;
        if(!attempts.getBlocked()) return false;
        if(attempts.getLastAttemptDate().getTime() + 30000 < new Date().getTime())
        {
            attemptsRepository.delete(attempts);
            return false;
        }
        log.info("User is blocked.");
        return true;
    }

    /**
     * Same method as previus, only it is called with the email of the user. The service will get from the repo the object with that email
     * and apply the previous method for it.
     * @param email email of the user we want to search
     * @return weather that user account is blocked.
     */
    public boolean isBlocked(String email)
    {
        Attempts attempts = attemptsRepository.findByEmail(email);
        return isBlocked(attempts);
    }

    /**
     * Finds the attempts object for some user email.
     * @param name User email
     * @return Attempts object from database.
     */
    public Attempts findByEmail(String name) {
        return attemptsRepository.findByEmail(name);
    }

    /**
     * Deletes an entry from the database.
     * @param attempts entry to be deleted
     */
    public void delete(Attempts attempts) {
        attemptsRepository.delete(attempts);
    }
}
