package app.validation;

import app.domain.RegistrationForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class RegistrationValidator implements Validator {
    @Override
    public boolean supports(final Class<?> clazz) {
        return clazz.isAssignableFrom(RegistrationForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final RegistrationForm form = (RegistrationForm) target;

        validateUsername(form.getUsername(), errors);
        validatePassword(form.getPassword(), form.getRepeatPassword(), errors);
        validateEmail(form.getEmail(), errors);
        validateUserConditionsApproved(form.getUserConditionsApproved(), errors);
    }

    private void validateUsername(final String username, final Errors errors) {
        if (StringUtils.isBlank(username)) {
            errors.rejectValue("username", "error.blank", new Object[]{new DefaultMessageSourceResolvable(("username"))},null);
        }
    }

    private void validatePassword(final String password, final String repeatPassword, final Errors errors) {
        /*
        if (StringUtils.isBlank(password)) {
            errors.rejectValue("password", "error.blank");
        } else if (!password.equals(repeatPassword)) {
            errors.rejectValue("password", "error.mismatch");
        }
        */
    }

    private void validateUserConditionsApproved(final boolean userConditionsApproved, final Errors errors) {
        /*
        if (userConditionsApproved == false) {
            errors.rejectValue("userConditionsApproved", "error.userconditions.notapproved");
        }
        */
    }

    private void validateEmail(final String email, final Errors errors) {
        if (StringUtils.isBlank(email)) {
            errors.rejectValue("email", "error.blank", new Object[]{new DefaultMessageSourceResolvable(("email"))},null);
        }
    }
}