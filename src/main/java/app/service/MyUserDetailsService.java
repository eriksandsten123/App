package app.service;

import app.domain.UserAccount;
import app.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// https://stackoverflow.com/questions/35344135/custom-userdetailsservice-it-seems-to-be-not-autowired
@Service("authService")
public class MyUserDetailsService implements UserDetailsService {
    private UserAccountRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserAccountRepository userAccountRepository) {
        this.userRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAccount userAccount = userRepository.findByUsername(username);
        if (userAccount == null) {
            throw new UsernameNotFoundException(username);
        }
        return userAccount;
    }
}
