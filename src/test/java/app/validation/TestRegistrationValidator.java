package app.validation;

import app.domain.RegistrationForm;
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
    public void testInvalidUsername() {
        registration.setPassword(VALID_PASSWORD);
        registration.setRepeatPassword(VALID_PASSWORD);

        Errors errors = new BeanPropertyBindingResult(registration, "registration");

        // Username empty
        registration.setUsername("");
        registrationValidator.validate(registration, errors);
        assertTrue(errors.hasFieldErrors("username"));

        // Username blank
        registration.setUsername("          ");
        registrationValidator.validate(registration, errors);
        assertTrue(errors.hasFieldErrors("username"));

        // Username too short
        registration.setUsername("Short");
        registrationValidator.validate(registration, errors);
        assertTrue(errors.hasFieldErrors("username"));

        // Username too long
        registration.setUsername("ThisUsernameIsAsAMatterOfFactWayTooLong");
        registrationValidator.validate(registration, errors);
        assertTrue(errors.hasFieldErrors("username"));
    }

    @Test
    public void testInvalidPassword() {
        registration.setUsername(VALID_PASSWORD);

        Errors errors = new BeanPropertyBindingResult(registration, "registration");

        // Password empty
        setPasswordFields(registration, "");
        registrationValidator.validate(registration, errors);
        assertTrue(errors.hasFieldErrors("password"));

        // Password blank
        setPasswordFields(registration, "          ");
        registrationValidator.validate(registration, errors);
        assertTrue(errors.hasFieldErrors("password"));

        // Password too short
        setPasswordFields(registration, "Sh0rt");
        registrationValidator.validate(registration, errors);
        assertTrue(errors.hasFieldErrors("password"));

        // Password too long
        setPasswordFields(registration, "Th1sPasswordIsAsAMatterOfFactWayTooLong");
        registrationValidator.validate(registration, errors);
        assertTrue(errors.hasFieldErrors("password"));

        // Password without digit

        // Valid passwords, but they don't match each other
        registration.setPassword("ValidPassw0rd");
        registration.setRepeatPassword("V4lidPassword");
        registrationValidator.validate(registration,errors);
        assertTrue(errors.hasFieldErrors("password"));
    }

    @Test
    public void testPasswordMismatch() {
        registration.setPassword("12345qwerty");
        registration.setRepeatPassword("qwerty12345");

        Errors errors = new BeanPropertyBindingResult(registration, "passwordMismatchRegistration");

        registrationValidator.validate(registration, errors);

        assertTrue(errors.hasFieldErrors());
    }

    private void setPasswordFields(final RegistrationForm form, final String value) {
        form.setPassword(value);
        form.setRepeatPassword(value);
    }
}
