package ro.sd.a2.configuration;

import org.aspectj.weaver.bcel.BcelAccessForInlineMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * Security configuration class.
 */
@Configuration
@ComponentScan(basePackages = { "com.baeldung.security" })
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.usersEmail-query}")
    private String usersMailQuery;

    @Value("${spring.queries.usersUsername-query}")
    private String usersUsernameQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().usersByUsernameQuery(usersMailQuery).authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
        auth.jdbcAuthentication().usersByUsernameQuery(usersUsernameQuery).authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder);
    }
    protected void configure(HttpSecurity http) throws Exception {
        UserDetails userDetails;
        http.authorizeRequests()

                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .antMatchers("/login").permitAll()
                .antMatchers("/register").permitAll()
                .antMatchers("/changePassword/**").permitAll()
                .antMatchers("/forgetPassword").permitAll()
                .antMatchers("/home/**").hasAnyAuthority("USER")
                .and()
                // some more method calls
                .formLogin().loginPage("/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/default")
                .failureUrl("/login?errMsg=Invalid+credentials.")
                .passwordParameter("password")

                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/login?errMsg=Can't+access+this+page");
    }

    @Override
    public void configure(WebSecurity webSecurity)
    {
        webSecurity.ignoring().antMatchers("/reosurces/**", "/static/**");
    }




}
