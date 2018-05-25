package app.controller;

import app.domain.Interest;
import app.domain.User;
import app.domain.UserAccount;
import app.manager.UserAccountManager;
import app.manager.UserProfileManager;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserProfileController {
    private UserProfileManager userProfileManager;
    private UserAccountManager userAccountManager;
    private SessionFactory sessionFactory;

    @Autowired
    public UserProfileController(UserProfileManager userProfileManager, UserAccountManager userAccountManager, SessionFactory sessionFactory) {
        this.userProfileManager = userProfileManager;
        this.userAccountManager = userAccountManager;
        this.sessionFactory = sessionFactory;
    }

    @GetMapping("/profile/my")
    public String getMyUserProfile(final Model model) {
        User userProfile = ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserProfile();
        model.addAttribute("user", userProfile);
        model.addAttribute("interests", Interest.values());

        return "my-profile";
    }

    @RequestMapping(value = "/interests", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public String testis(@RequestParam(name = "id", required = false) final Long interestId, final Model model) {
        if (interestId == null) {
            // View all interests
            String hql = "select t, count(1) from User a inner join a.interests t group by t";
            List interestCounts = sessionFactory.getCurrentSession().createQuery(hql).list();
            model.addAttribute("interestCounts", interestCounts);
        } else {
            // View specific interest
            final Interest interest = Interest.COOKING;

            List<User> usersWithInterest = (List<User>) sessionFactory.getCurrentSession().createQuery("from User user join user.interests i where i = :interest")
                    .setParameter("interest", interest).list();

            model.addAttribute("interest", interest);
            model.addAttribute("usersWithInterest", usersWithInterest);
        }

        return "interests";
    }

    @PostMapping("/profile/my")
    public String updateMyUserProfile(@ModelAttribute final User userProfile) {
        // TODO: clean text from malicious characters

        User existingProfile = ((UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserProfile();

        existingProfile.setPresentation(userProfile.getPresentation());
        existingProfile.setInterests(userProfile.getInterests());

        userProfileManager.saveOrUpdate(existingProfile);

        return "redirect:/profile/my";
    }

    @GetMapping("/profile/view")
    public String viewUserProfile(@RequestParam(value = "id", required = true) final long id, final Model model) {
        User userProfile = userProfileManager.getUserById(id);
        User loggedInProfile = userAccountManager.getAuthenticatedUserAccount().getUserProfile();
        Set<User> favorites = loggedInProfile.getFavorites();

        Set<Interest> commonInterests = new HashSet<>(userProfile.getInterests());
        commonInterests.retainAll(loggedInProfile.getInterests());

        boolean isFavorite = favorites.stream().anyMatch(favorite -> favorite.getId() == id);
        model.addAttribute("isFavorite", isFavorite);
        model.addAttribute("commonInterests", commonInterests);

        // TODO: redirect to /myprofile if id is equal to logged in users id

        model.addAttribute("userprofile", userProfile);
        return "view-profile";
    }

    @GetMapping("/profile/browse")
    public String browseUserProfiles(final Model model) {
        final List<User> onlineUserProfiles = userProfileManager.getOnlineUserProfiles(20);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        /*
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() != null) {
            User userProfile = ((UserAccount) auth.getPrincipal()).getUserProfile();

            final Set<Long> favoritesIds = Optional.ofNullable(userProfile)
                    .map(profile -> profile.getFavorites().stream().mapToLong(User::getId).boxed().collect(Collectors.toSet()))
                    .orElse(null);

            model.addAttribute("favoritesIds", favoritesIds);
        }
        */

        // TODO: Remove logged in user in onlineUserProfiles

        model.addAttribute("userProfiles", onlineUserProfiles);

        return "browse-profiles";
    }

    @PostMapping("/profile/my/uploadprofilepic")
    public String uploadProfilePicture(@RequestParam("profilePicture") final MultipartFile profilePicture,
                                       Principal principal) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (profilePicture != null && auth != null && auth.isAuthenticated() && auth.getPrincipal() != null) {
            User userProfile = ((UserAccount) auth.getPrincipal()).getUserProfile();
            userProfileManager.storeProfilePicture(userProfile, profilePicture);
        }

        return "redirect:/profile/my";
    }
}