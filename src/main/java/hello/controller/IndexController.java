package hello.controller;

import javax.validation.Valid;

import hello.domain.RegistrationForm;
import hello.validation.RegistrationValidator;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Controller
public class IndexController extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/register")
    public String showForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid RegistrationForm registrationForm, BindingResult bindingResult) {
        RegistrationValidator validator = new RegistrationValidator();

        validator.validate(registrationForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register";
        }

        return "redirect:/home";
    }
}