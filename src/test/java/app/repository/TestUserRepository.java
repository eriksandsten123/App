package app.repository;

import app.domain.Gender;
import app.domain.Interest;
import app.domain.User;
import app.repository.impl.UserRepositoryImpl;
import app.support.AbstractDBTest;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUserRepository extends AbstractDBTest {
    private UserRepository userRepository;

    @Autowired
    private SessionFactory sessionFactory;

    final User user1 = new User();
    final User user2 = new User();
    final User user3 = new User();

    @Before
    public void setup() throws Exception {
        super.setup();
        userRepository = new UserRepositoryImpl(sessionFactory);
        user1.setAge(25);
        user1.setName("David");
        user1.setGender(Gender.MALE);
        //user1.setInterests(Set.of(Interest.COOKING, Interest.DANCING));
        user2.setAge(20);
        user2.setGender(Gender.MALE);
        user2.setName("Erik");
        //user2.setInterests(Set.of(Interest.COOKING));
        user3.setAge(50);
        user3.setName("Camilla");
        user3.setGender(Gender.FEMALE);
        //user3.setInterests(Set.of(Interest.COMPUTING, Interest.ART));
        sessionFactory.getCurrentSession().save(user1);
        userRepository.saveOrUpdate(user1);
        //userRepository.saveOrUpdate(user2);
        //userRepository.saveOrUpdate(user3);
    }

    @Test
    public void testGetUserById() {
        final User userById = userRepository.getUserById(user2.getId());
        assertThat(userById).isEqualTo(user2);
    }

    @Test
    public void testGetUserByName() {
        final User userByName = userRepository.getUserByName("Camilla");
        assertThat(userByName).isEqualTo(user3);
    }

    @Test
    public void testSaveOrUpdate() {
        user3.setName("Patrik");
        user3.setAge(33);
        user3.setGender(Gender.MALE);

        userRepository.saveOrUpdate(user3);

        final User updatedUser = userRepository.getUserByName("Patrik");

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getName()).isEqualTo("Patrik");
        assertThat(updatedUser.getAge()).isEqualTo(33);
        assertThat(updatedUser.getGender()).isEqualTo(Gender.MALE);
    }

    @Test
    public void testGetOnlineUserProfiles() {
        /*
        final List<User> onlineUserProfiles = userRepository.getOnlineUserProfiles(20);
        assertThat(onlineUserProfiles).containsExactly(user1, user2, user3);
        */
    }

    @Test
    public void testCountUsersWithInterest() {
        assertThat(userRepository.countUsersWithInterest(Interest.COOKING)).isEqualTo(2L);
    }
}
