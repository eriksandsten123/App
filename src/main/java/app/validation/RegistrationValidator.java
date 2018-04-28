package app.validation;

import app.domain.RegistrationForm;
import org.apache.commons.lang3.StringUtils;
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

        if (form.getUserConditionsApproved() == false) {
            errors.rejectValue("userConditionsApproved", "error.userconditions.notapproved");
        }

        validateUsername(form.getUsername(), errors);
        validatePassword(form.getPassword(), form.getRepeatPassword(), errors);
    }

    private void validateUsername(final String username, final Errors errors) {
        if (StringUtils.isBlank(username)) {
            errors.rejectValue("username", "error.username.exists");
        }
    }

    private void validatePassword(final String password, final String repeatPassword, final Errors errors) {
        if (StringUtils.isBlank(password)) {
            errors.rejectValue("password", "error.field.blank");
        } else if (!password.equals(repeatPassword)) {
            errors.rejectValue("password", "error.password.mismatch");
        }
    }
}