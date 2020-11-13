package ro.sd.a2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.sd.a2.entity.Attempts;
import ro.sd.a2.service.AttemptsService;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class UserAccountBlockingTest {

    @Autowired
    private AttemptsService attemptsService;

    @Test
    public void testUserBlock()
    {
        String email = "email@yahoo.com";
        attemptsService.addAttempt(email);
        Attempts attempts = attemptsService.findByEmail(email);
        assertThat(attempts!=null);
        assertThat(!attemptsService.isBlocked(attempts));

        attemptsService.addAttempt(email);
        attempts = attemptsService.findByEmail(email);
        assertThat(attempts!=null);
        assertThat(!attemptsService.isBlocked(attempts));

        attemptsService.addAttempt(email);
        attempts = attemptsService.findByEmail(email);
        assertThat(attempts!=null);
        assertThat(attemptsService.isBlocked(attempts));

        //we can see that the third time is bocked.
        try {
            TimeUnit.SECONDS.sleep(30);
            assertThat(!attemptsService.isBlocked(attempts));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
