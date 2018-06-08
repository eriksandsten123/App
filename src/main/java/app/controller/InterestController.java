package app.controller;

import app.domain.Interest;
import app.domain.User;
import app.manager.UserProfileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class InterestController {
    private UserProfileManager userProfileManager;

    @Autowired
    public InterestController(final UserProfileManager userProfileManager) {
        this.userProfileManager = userProfileManager;
    }

    @GetMapping("/interest/view")
    public String viewInterest(@RequestParam("name") final String name, final Model model) {
        Interest interest = null;

        try {
            interest = Interest.valueOf(name.toUpperCase());

        } catch (final NullPointerException | IllegalArgumentException e) {
            System.out.println("testis");
        }

        List<User> usersWithInterest = userProfileManager.getUsersWithInterest(interest);

        model.addAttribute("interest", interest);
        model.addAttribute("usersWithInterest", usersWithInterest);

        return "view-interest";
    }
}