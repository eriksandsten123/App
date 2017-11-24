package hello.controller;

import hello.domain.RegistrationForm;
import hello.domain.UserAccount;
import hello.repository.UserAccountRepository;
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
    private UserAccountRepository userAccountRepository;

    @Autowired
    public IndexController(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
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

            boolean usernameAlreadyExists = userAccountRepository.exists(registerForm.getUsername());

            if (usernameAlreadyExists) {
                System.out.println("User already exists!");
                bindingResult.addError(new ObjectError("username", "Användarnamnet finns redan"));
                return "register";
            } else {
                UserAccount userAccount = new UserAccount();

                userAccount.setUsername(registerForm.getUsername());
                userAccount.setPassword(registerForm.getPassword());
                userAccount.setEmail(registerForm.getEmail());

                userAccountRepository.save(userAccount);
            }
        }

        return "redirect:/home";
    }
}