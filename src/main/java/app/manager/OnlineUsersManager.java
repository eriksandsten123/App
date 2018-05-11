package app.manager;

import app.domain.OnlineUser;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Scheduled(fixedDelay = 2000)
    public void updateOnlineUsersList() {
        System.out.println(onlineUsers);
    }
}