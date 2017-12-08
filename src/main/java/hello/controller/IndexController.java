package hello.controller;

import hello.domain.RegistrationForm;
import hello.domain.User;
import hello.domain.UserAccount;
import hello.manager.UserAccountManager;
import hello.manager.UserProfileManager;
import hello.validation.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;

@Controller
public class IndexController extends WebMvcConfigurerAdapter {
    private UserAccountManager userAccountManager;
    private UserProfileManager userProfileManager;

    @Autowired
    public IndexController(UserAccountManager userAccountManager, UserProfileManager userProfileManager) {
        this.userAccountManager = userAccountManager;
        this.userProfileManager = userProfileManager;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("registerForm", new RegistrationForm());
        return "register";
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