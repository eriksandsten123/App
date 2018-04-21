package app.repository;

import app.domain.UserAccount;

public interface UserAccountRepository {
    UserAccount findByUsername(String username);
    void saveOrUpdate(UserAccount userAccount);
}