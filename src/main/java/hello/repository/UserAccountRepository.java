package hello.repository;

import hello.domain.UserAccount;

public interface UserAccountRepository {
    UserAccount findByUsername(String username);
    void saveOrUpdate(UserAccount userAccount);
}