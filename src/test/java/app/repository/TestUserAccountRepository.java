package app.repository;

import app.domain.UserAccount;
import app.repository.impl.UserAccountRepositoryImpl;
import app.support.AbstractDBTest;
import org.assertj.core.api.Assertions;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TestUserAccountRepository extends AbstractDBTest {
    private UserAccountRepository userAccountRepository;

    @Autowired
    private SessionFactory sessionFactory;

    final UserAccount userAccount1 = new UserAccount();

    @Before
    public void setup() throws Exception {
        super.setup();

        userAccountRepository = new UserAccountRepositoryImpl(sessionFactory);

        userAccount1.setUsername("Användarnamn");
        userAccount1.setPassword("Lösenord");
        userAccount1.setEmail("email@email.se");

        userAccountRepository.saveOrUpdate(userAccount1);
    }

    @Test
    public void testFindByUsername() {
        final UserAccount userAccountByUsername = userAccountRepository.findByUsername("Användarnamn");
        assertThat(userAccountByUsername).isEqualTo(userAccount1);
    }

    @Test
    public void testSaveOrUpdate() {
        userAccount1.setEmail("uppdaterad@email.com");
        userAccount1.setPassword("updatedPass");

        userAccountRepository.saveOrUpdate(userAccount1);

        final UserAccount updatedUserAccount = userAccountRepository.findByUsername(userAccount1.getUsername());

        Assertions.assertThat(updatedUserAccount).isNotNull();
        Assertions.assertThat(updatedUserAccount.getEmail()).isEqualTo("uppdaterad@email.com");
        Assertions.assertThat(updatedUserAccount.getPassword()).isEqualTo("updatedPass");
    }
}
