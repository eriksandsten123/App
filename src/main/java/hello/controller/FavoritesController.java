package hello.controller;

import hello.domain.User;
import hello.domain.UserAccount;
import hello.manager.UserProfileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Controller
public class FavoritesController {
    private UserProfileManager userProfileManager;

    @Autowired
    public FavoritesController(final UserProfileManager userProfileManager) {
        this.userProfileManager = userProfileManager;
    }

    @GetMapping("/favorites")
    public String viewFavorites(final Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() != null) {
            User userProfile = ((UserAccount) auth.getPrincipal()).getUserProfile();
            model.addAttribute("favorites", userProfile.getFavorites());
        }

        return "favorites";
    }

    @RequestMapping("/favorites/add")
    @ResponseBody
    public String addFavorite(@RequestParam(name = "userId", required = true) final long id,
                              final HttpServletResponse response) {
        final User newFavorite = userProfileManager.getUserById(id);

        if (newFavorite == null) {
            throw new RuntimeException("No user with id " + id + " exists");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() != null) {
            User userProfile = ((UserAccount)auth.getPrincipal()).getUserProfile();

            if (id == userProfile.getId()) {
                // Trying to add self as favorite
                throw new RuntimeException("Trying to add yourself as a favorite");
            }

            Set<User> favorites = userProfile.getFavorites();
            boolean alreadyAdded = favorites.contains(newFavorite);

            if (alreadyAdded) {
                throw new RuntimeException("Favorite already added");
            } else {
                favorites.add(newFavorite);
                userProfileManager.saveOrUpdate(userProfile);
            }
        }

        return "OK";
    }
}