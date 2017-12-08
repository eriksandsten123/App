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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
public class UserProfileController {
    private UserProfileManager userProfileManager;

    @Autowired
    public UserProfileController(UserProfileManager userProfileManager) {
        this.userProfileManager = userProfileManager;
    }

    @GetMapping("/myprofile")
    public String getMyUserProfile(final Model model) {
        User userProfile = ((UserAccount)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserProfile();
        model.addAttribute("user", userProfile);
        return "my-profile";
    }

    @GetMapping("/profile/{id}")
    public String viewUserProfile(@PathVariable final Long id, final Model model) {
        User userProfile = userProfileManager.getUserById(id);
        model.addAttribute("userprofile", userProfile);
        return "view-profile";
    }

    @PostMapping("/myprofile/uploadprofilepic")
    public String uploadProfilePicture(@RequestParam("profilePicture") final MultipartFile profilePicture,
                                     Principal principal) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (profilePicture != null && auth != null && auth.isAuthenticated() && auth.getPrincipal() != null) {
            User userProfile = ((UserAccount)auth.getPrincipal()).getUserProfile();
            userProfileManager.storeProfilePicture(userProfile, profilePicture);
        }

        return "redirect:/myprofile";
    }
}