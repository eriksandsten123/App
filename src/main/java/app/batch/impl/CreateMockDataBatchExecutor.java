package app.batch.impl;

import app.batch.BatchExecutor;
import app.domain.Gender;
import app.domain.Interest;
import app.domain.User;
import app.domain.UserAccount;
import app.manager.UserAccountManager;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;
import java.util.Set;

@Service
public class CreateMockDataBatchExecutor implements BatchExecutor {
    private UserAccountManager userAccountManager;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    public CreateMockDataBatchExecutor(final UserAccountManager userAccountManager) {
        this.userAccountManager = userAccountManager;
    }

    @Override
    public void executeBatch() {
        // Clear DB from old data
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        clearDatabase(transactionTemplate);

        Map<String, User> userProfiles = Map.of(
                "hejsan123", createUserProfile(23, Gender.MALE, "Olle", "My presentation!"),
                "maja324", createUserProfile(32, Gender.FEMALE, "Maja", "Min coola presentation..."),
                "mrHashy", createUserProfile(24, Gender.MALE, "Thomas", "Presentation här... :)"),
                "Olle33", createUserProfile(33, Gender.MALE, "Kenneth", "Ma cool presentation"),
                "FitGuy123", createUserProfile(48, Gender.FEMALE, "Kristin", "Prezentation..."),
                "ScienceFreak999", createUserProfile(32, Gender.FEMALE, "Ulrika", "PRESENTATIONAL TEXT!"));

        userProfiles.forEach((userName, userProfile) -> saveUserAccount(userName, userName, "abc@abc.se", userProfile));

        createFavoritesData(transactionTemplate, userProfiles);
        createInterestsData(transactionTemplate, userProfiles);
    }

    private void clearDatabase(final TransactionTemplate transactionTemplate) {
        transactionTemplate.execute(__ -> {
            sessionFactory.getCurrentSession().createQuery("delete from UserAccount").executeUpdate();
            sessionFactory.getCurrentSession().createQuery("delete from User").executeUpdate();
            return null;
        });
    }

    private void createFavoritesData(final TransactionTemplate transactionTemplate, final Map<String, User> userProfiles) {
        userProfiles.get("mrHashy").setFavorites(Set.of(userProfiles.get("Olle33"), userProfiles.get("maja324")));
        userProfiles.get("Olle33").setFavorites(Set.of(userProfiles.get("mrHashy")));

        transactionTemplate.execute(__ -> {
            sessionFactory.getCurrentSession().update(userProfiles.get("mrHashy"));
            sessionFactory.getCurrentSession().update(userProfiles.get("Olle33"));
            return null;
        });
    }

    private void createInterestsData(final TransactionTemplate transactionTemplate, final Map<String, User> userProfiles) {
        userProfiles.get("mrHashy").setInterests(Set.of(Interest.SCIENCE, Interest.COOKING));
        userProfiles.get("Olle33").setInterests(Set.of(Interest.SCIENCE));
        userProfiles.get("FitGuy123").setInterests(Set.of(Interest.DANCING, Interest.COMPUTING));
        userProfiles.get("maja324").setInterests(Set.of(Interest.DANCING, Interest.ART, Interest.SCIENCE));
        userProfiles.get("ScienceFreak999").setInterests(Set.of(Interest.ART, Interest.SCIENCE));

        transactionTemplate.execute(__ -> {
            userProfiles.values().forEach(sessionFactory.getCurrentSession()::update);
            return null;
        });
    }


    private User createUserProfile(final int age, final Gender gender, final String name, final String presentation) {
        final User userProfile = new User();

        userProfile.setAge(age);
        userProfile.setName(name);
        userProfile.setGender(gender);
        userProfile.setPresentation(presentation);

        return userProfile;
    }

    private void saveUserAccount(final String userName, final String password, final String email, final User userProfile) {
        final UserAccount userAccount = new UserAccount();

        userAccount.setUsername(userName);
        userAccount.setPassword(password);
        userAccount.setEmail(email);
        userAccount.setUserProfile(userProfile);

        userAccountManager.save(userAccount);
    }
}
