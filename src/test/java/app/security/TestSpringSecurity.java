package app.security;

import app.WebSecurityConfig;
import app.domain.UserAccount;
import app.handler.MyAuthenticationSuccessHandler;
import app.handler.MyLogoutSuccessHandler;
import app.manager.OnlineUsersManager;
import app.manager.UserAccountManager;
import app.repository.impl.UserAccountRepositoryImpl;
import app.service.MyUserDetailsService;
import app.support.AbstractDBTest;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {OnlineUsersManager.class, MyLogoutSuccessHandler.class, MyAuthenticationSuccessHandler.class, UserAccountManager.class, UserAccountRepositoryImpl.class, MyUserDetailsService.class, WebSecurityConfig.class})
@WebAppConfiguration
public class TestSpringSecurity extends AbstractDBTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserAccountManager userAccountManager;

    @Autowired
    private SessionFactory sessionFactory;

    private MockMvc mockMvc;

    private UserAccount testUserAccount;

    @Before
    public void setup() throws Exception {
        super.setup();

        testUserAccount = new UserAccount();
        testUserAccount.setUsername("testisUser");
        testUserAccount.setEmail("testmail@mail.se");
        testUserAccount.setPassword("hejsan123");
        userAccountManager.save(testUserAccount);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testLoginAsUser() throws Exception {
        mockMvc.perform(formLogin("/login").user("testisUser").password("hejsan123"))
                .andExpect(authenticated().withUsername("testisUser"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testUserLogin_invalidPassword() throws Exception {
        mockMvc.perform(formLogin("/login").user("testisUser").password("felLösenord"))
                .andExpect(unauthenticated());
    }

    @Test
    public void testUserLogin_invalidUsername() throws Exception {
        mockMvc.perform(formLogin("/login").user("invalidUser").password("hejsan123"))
                .andExpect(unauthenticated());
    }

    @Test
    public void testAccessToRestrictedResource_notLoggedIn() throws Exception {
        // Not logged in and trying to access a restricted page, should be redirected to login page
        mockMvc.perform(MockMvcRequestBuilders.get("/home")).andExpect(redirectedUrlPattern("**/login"));
    }
}