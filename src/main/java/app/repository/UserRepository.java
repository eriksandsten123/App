package app.repository;

import app.domain.Interest;
import app.domain.User;

import java.util.List;

public interface UserRepository {
    User getUserByName(String name);

    List<User> getOnlineUserProfiles(int maxResults);

    void saveOrUpdate(User user);

    User getUserById(Long id);

    List<User> getUsersWithInterest(Interest interest);

    long countUsersWithInterest(Interest interest);
}