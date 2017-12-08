package hello.manager;

import hello.domain.UserAccount;
import hello.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserAccountManager {
    private UserAccountRepository userAccountRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserAccountManager(final UserAccountRepository userAccountRepository, PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserAccount findByUsername(final String username) {
        return null;
    }

    public boolean exists(final String username) {
        return userAccountRepository.exists(username);
    }

    @Transactional
    public UserAccount save(final UserAccount userAccount) {
        // Encrypt the password
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        return userAccountRepository.save(userAccount);
    }
}