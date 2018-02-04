package hello.manager;

import hello.domain.OnlineUser;
import hello.domain.User;
import hello.domain.UserAccount;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Service("onlineUsersManager")
public class OnlineUsersManager implements AuthenticationSuccessHandler, LogoutSuccessHandler {
    private static final Set<OnlineUser> onlineUsers = new ConcurrentSkipListSet<>();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        final User profile = ((UserAccount) authentication.getPrincipal()).getUserProfile();
        final HttpSession session = request.getSession();

        session.setAttribute("username", profile.getName());
        session.setAttribute("userId", profile.getId());
        onlineUsers.add(new OnlineUser(profile.getId(), profile.getName()));

        response.sendRedirect("home");
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final User profile = ((UserAccount) authentication.getPrincipal()).getUserProfile();

        onlineUsers.remove(new OnlineUser(profile.getId(), profile.getName()));
    }

    public void addOnlineUser(final OnlineUser user) {
        onlineUsers.add(user);
    }

    public Set<OnlineUser> getOnlineUsers() {
        return onlineUsers;
    }
}