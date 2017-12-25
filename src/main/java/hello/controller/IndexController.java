package hello.controller;

import hello.domain.RegistrationForm;
import hello.domain.User;
import hello.domain.UserAccount;
import hello.manager.UserAccountManager;
import hello.manager.UserProfileManager;
import hello.validation.RegistrationValidator;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class IndexController extends WebMvcConfigurerAdapter {
    @Autowired
    private UserAccountManager userAccountManager;
    private UserProfileManager userProfileManager;

    @Autowired
    public IndexController(UserProfileManager userProfileManager) {
        //this.userAccountManager = userAccountManager;
        this.userProfileManager = userProfileManager;
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("registerForm", new RegistrationForm());
        return "register";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerForm") RegistrationForm registerForm, BindingResult bindingResult) {
        RegistrationValidator validator = new RegistrationValidator();

        validator.validate(registerForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        } else {
            System.out.println("Registering......!");

            boolean usernameAlreadyExists = userAccountManager.exists(registerForm.getUsername());

            if (usernameAlreadyExists) {
                System.out.println("User already exists!");
                bindingResult.addError(new ObjectError("username", "Användarnamnet finns redan"));
                return "register";
            } else {
                saveUserAccount(registerForm);
            }
        }

        return "redirect:/home";
    }

    private void saveUserAccount(final RegistrationForm registerForm) {
        UserAccount userAccount = new UserAccount();
        User user = new User();

        userAccount.setUsername(registerForm.getUsername());
        userAccount.setPassword(registerForm.getPassword());
        userAccount.setEmail(registerForm.getEmail());
        userAccount.setUserProfile(user);

        user.setAge(registerForm.getAge());
        user.setName(registerForm.getUsername());
        user.setGender(registerForm.getGender());

        userAccountManager.save(userAccount);
    }
}