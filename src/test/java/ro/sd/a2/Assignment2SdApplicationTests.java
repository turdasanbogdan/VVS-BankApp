package ro.sd.a2;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ro.sd.a2.controller.HomeController;
import ro.sd.a2.repository.BillRepository;
import ro.sd.a2.service.AccountService;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class Assignment2SdApplicationTests {

    @Autowired
    private HomeController homeController;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private BillRepository billRepository;



    /**
     * Check if spring autowiring works by giving a random class from each package: config, service, repository, controller
     */
    @Test
    void contextLoads() {
        assertThat(homeController).isNotNull();
        assertThat(accountService).isNotNull();
        assertThat(bCryptPasswordEncoder).isNotNull();
        assertThat(emailSender).isNotNull();
        assertThat(billRepository).isNotNull();
    }




}
