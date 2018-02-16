package hello.manager;

import hello.domain.OnlineUser;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class OnlineUsersManager {
    private static final Set<OnlineUser> onlineUsers = new ConcurrentSkipListSet<>();

    public boolean addOnlineUser(final OnlineUser user) {
        return onlineUsers.add(user);
    }

    public boolean removeOnlineUser(final OnlineUser user) {
        return onlineUsers.remove(user);
    }

    public Set<OnlineUser> getOnlineUsers() {
        return onlineUsers;
    }
}