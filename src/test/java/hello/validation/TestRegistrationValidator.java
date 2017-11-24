package hello.validation;

import hello.domain.RegistrationForm;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRegistrationValidator {
    private static final String VALID_USERNAME = "användare";
    private static final String VALID_PASSWORD = "12345qwerty";

    private RegistrationValidator registrationValidator = new RegistrationValidator();
    private RegistrationForm registration;

    @Before
    public void setup() {
        registration = new RegistrationForm();
    }

    @Test
    public void testValidRegistration() {
        registration.setUsername(VALID_USERNAME);
        registration.setPassword(VALID_PASSWORD);
        registration.setRepeatPassword(VALID_PASSWORD);

        Errors errors = new BeanPropertyBindingResult(registration, "validRegistration");

        registrationValidator.validate(registration, errors);

        assertFalse(errors.hasFieldErrors());
    }

    @Test
    public void testBlankUsername() {
        registration.setUsername("");
        registration.setPassword(VALID_PASSWORD);
        registration.setRepeatPassword(VALID_PASSWORD);

        Errors errors = new BeanPropertyBindingResult(registration, "registration");

        registrationValidator.validate(registration, errors);

        assertTrue(errors.hasFieldErrors());

        registration.setUsername("     ");

        registrationValidator.validate(registration, errors);

        assertTrue(errors.hasFieldErrors());
    }

    @Test
    public void testInvalidUsername() {
        registration.setUsername("aaa");
        registration.setPassword(VALID_PASSWORD);
        registration.setRepeatPassword(VALID_PASSWORD);

        Errors errors = new BeanPropertyBindingResult(registration, "registration");

        registrationValidator.validate(registration, errors);

        //assertTrue(errors.hasFieldErrors());
    }

    @Test
    public void testPasswordMismatch() {
        registration.setPassword("12345qwerty");
        registration.setRepeatPassword("qwerty12345");

        Errors errors = new BeanPropertyBindingResult(registration, "passwordMismatchRegistration");

        registrationValidator.validate(registration, errors);

        assertTrue(errors.hasFieldErrors());
    }
}
