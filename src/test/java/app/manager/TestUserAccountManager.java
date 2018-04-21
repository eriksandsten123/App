package app.manager;

import app.WebSecurityConfig;
import app.domain.UserAccount;
import app.repository.UserAccountRepository;
import app.service.MyUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.assertj.core.api.Assertions;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MyUserDetailsService.class, WebSecurityConfig.class})
public class TestUserAccountManager {
    @MockBean
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserAccountManager userAccountManager;

    @Before
    public void setup() {
        userAccountManager = new UserAccountManager(userAccountRepository, passwordEncoder);
    }

    @Test
    public void testPasswordEncryption() {
        final String originalPassword = "KJfdwokfj5";

        final UserAccount testAccount = new UserAccount();
        testAccount.setUsername("testisAnvändarnamn");
        testAccount.setPassword(originalPassword);
        testAccount.setEmail("testEmail@email.se");

        userAccountManager.save(testAccount);
        final String savedPassword = testAccount.getPassword();

        Assertions.assertThat(savedPassword).isNotNull().isNotEqualTo(originalPassword);
        Assertions.assertThat(passwordEncoder.matches(originalPassword, savedPassword)).isTrue();

        verify(userAccountRepository, times(1)).saveOrUpdate(testAccount);
    }
}