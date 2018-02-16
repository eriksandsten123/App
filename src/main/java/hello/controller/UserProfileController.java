package hello.controller;

import hello.domain.User;
import hello.domain.UserAccount;
import hello.manager.UserAccountManager;
import hello.manager.UserProfileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserProfileController {
    private UserProfileManager userProfileManager;
    private UserAccountManager userAccountManager;

    @Autowired
    public UserProfileController(UserProfileManager userProfileManager, UserAccountManager userAccountManager) {
        this.userProfileManager = userProfileManager;
        this.userAccountManager = userAccountManager;
    }

    @GetMapping("/myprofile")
    public String getMyUserProfile(final Model model) {
        User userProfile = ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserProfile();
        model.addAttribute("user", userProfile);
        return "my-profile";
    }

    @PostMapping("/myprofile")
    public String updateMyUserProfile(@ModelAttribute final User userProfile) {
        // Update the user profile
        System.out.println("Hello World!");
        return "redirect:/myprofile";
    }

    @GetMapping("/profile/{id}")
    public String viewUserProfile(@PathVariable final Long id, final Model model) {
        User userProfile = userProfileManager.getUserById(id);
        User loggedInProfile = userAccountManager.getAuthenticatedUserAccount().getUserProfile();
        Set<User> favorites = loggedInProfile.getFavorites();
        boolean isFavorite = favorites.stream().anyMatch(favorite -> favorite.getId() == id);
        model.addAttribute("isFavorite", isFavorite);

        // TODO: redirect to /myprofile if id is equal to logged in users id

        model.addAttribute("userprofile", userProfile);
        return "view-profile";
    }

    @GetMapping("/profile/browse")
    public String browseUserProfiles(final Model model) {
        final List<User> onlineUserProfiles = userProfileManager.getOnlineUserProfiles(20);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() != null) {
            User userProfile = ((UserAccount) auth.getPrincipal()).getUserProfile();

            final Set<Long> favoritesIds = Optional.ofNullable(userProfile)
                    .map(profile -> profile.getFavorites().stream().mapToLong(User::getId).boxed().collect(Collectors.toSet()))
                    .orElse(null);

            model.addAttribute("favoritesIds", favoritesIds);
        }

        // TODO: Remove logged in user in onlineUserProfiles

        model.addAttribute("userProfiles", onlineUserProfiles);

        return "browse-profiles";
    }

    @PostMapping("/myprofile/uploadprofilepic")
    public String uploadProfilePicture(@RequestParam("profilePicture") final MultipartFile profilePicture,
                                       Principal principal) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (profilePicture != null && auth != null && auth.isAuthenticated() && auth.getPrincipal() != null) {
            User userProfile = ((UserAccount) auth.getPrincipal()).getUserProfile();
            userProfileManager.storeProfilePicture(userProfile, profilePicture);
        }

        return "redirect:/myprofile";
    }
}