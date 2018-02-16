package hello.handler;

import hello.domain.OnlineUser;
import hello.domain.User;
import hello.domain.UserAccount;
import hello.manager.OnlineUsersManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private OnlineUsersManager onlineUsersManager;

    @Autowired
    public MyAuthenticationSuccessHandler(final OnlineUsersManager onlineUsersManager) {
        this.onlineUsersManager = onlineUsersManager;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        final User profile = ((UserAccount) authentication.getPrincipal()).getUserProfile();
        final HttpSession session = request.getSession();

        session.setAttribute("username", profile.getName());
        session.setAttribute("userId", profile.getId());
        onlineUsersManager.addOnlineUser(new OnlineUser(profile.getId(), profile.getName()));

        super.onAuthenticationSuccess(request, response, authentication);
    }
}