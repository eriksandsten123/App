package hello.repository;

import hello.domain.Gender;
import hello.domain.User;
import hello.repository.impl.UserRepositoryImpl;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestUserRepository {
    private UserRepository userRepository;

    @Autowired
    private SessionFactory sessionFactory;

    @Before
    public void setup() {
        userRepository = new UserRepositoryImpl(sessionFactory);
    }

    @Test
    public void testGetUserById() {
        final User user1 = new User();
        final User user2 = new User();
        final User user3 = new User();

        user2.setAge(20);
        user2.setGender(Gender.MALE);
        user2.setName("Erik");

        userRepository.saveOrUpdate(user1);
        userRepository.saveOrUpdate(user2);
        userRepository.saveOrUpdate(user3);

        final User userById = userRepository.getUserById(user2.getId());

        assertThat(userById).isEqualTo(user2);
    }
}
