package app.handler;

import app.manager.OnlineUsersManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
    private OnlineUsersManager onlineUsersManager;

    @Autowired
    public MyLogoutSuccessHandler(final OnlineUsersManager onlineUsersManager) {
        this.onlineUsersManager = onlineUsersManager;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //final User profile = ((UserAccount) authentication.getPrincipal()).getUserProfile();
        //onlineUsersManager.removeOnlineUser(new OnlineUser(profile.getId(), profile.getName()));

        super.onLogoutSuccess(request, response, authentication);
    }
}