package hello.security;

import hello.WebSecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
public class TestSpringSecurity {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testLoginAsAdmin() throws Exception {
        mvc.perform(formLogin("/login").user("admin").password("admin")).andExpect(authenticated().withRoles(WebSecurityConfig.Roles.ADMIN.name()));
    }

    @Test
    public void testLoginAsUser() throws Exception {
        mvc.perform(formLogin("/login").user("user").password("password")).andExpect(authenticated().withRoles(WebSecurityConfig.Roles.USER.name()));
    }

    @Test
    public void testAdminLogout() throws Exception {
        mvc.perform(formLogin("/login").user("user").password("password")).andExpect(authenticated().withRoles(WebSecurityConfig.Roles.USER.name()));
        mvc.perform(SecurityMockMvcRequestBuilders.logout("/logout"));
    }
}