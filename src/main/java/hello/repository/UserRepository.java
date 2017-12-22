package hello.repository;

import hello.domain.User;

import java.util.List;

public interface UserRepository {
    User getUserByName(String name);
    List<User> getOnlineUserProfiles(int maxResults);
    void saveOrUpdate(User user);
    User getUserById(Long id);
}