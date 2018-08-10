package app;

import app.handler.MyAuthenticationSuccessHandler;
import app.handler.MyLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsService userDetailsService;
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    public WebSecurityConfig(final UserDetailsService userDetailsService,
                             final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler,
                             final MyLogoutSuccessHandler myLogoutSuccessHandler) {
        super();

        this.userDetailsService = userDetailsService;
        this.myAuthenticationSuccessHandler = myAuthenticationSuccessHandler;
        this.myLogoutSuccessHandler = myLogoutSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login*", "/register", "/css/**", "/semantic-ui/**").permitAll()
                .anyRequest().authenticated().and()
                .formLogin().loginPage("/login").successHandler(loginHandler()).and()
                .logout().logoutUrl("/logout").logoutSuccessHandler(logoutHandler()).and()
                .httpBasic();

        // https://docs.spring.io/spring-security/site/docs/current/reference/html/jc.html
        // We must grant all users (i.e. unauthenticated users) access to our log in page. The formLogin().permitAll() method allows granting access to all users for all URLs associated with form based log in.
        /*
        http
                .authorizeRequests().antMatchers("/webjars/**", "/chat/**").permitAll()
                .antMatchers("/profile/**").authenticated()
                .and()
                .authorizeRequests().antMatchers("/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .permitAll()
                .and()
                .authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .logout().logoutUrl("/logout").clearAuthentication(true).logoutSuccessUrl("/")
                .permitAll();
                */
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler loginHandler() {
        return myAuthenticationSuccessHandler;
    }

    @Bean
    public LogoutSuccessHandler logoutHandler() {
        return myLogoutSuccessHandler;
    }
}