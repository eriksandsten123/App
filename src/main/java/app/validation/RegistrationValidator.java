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
		final RegistrationForm form = (RegistrationForm)target;

		if (StringUtils.isBlank(form.getUsername())) {
			errors.rejectValue("username", "error.username.exists");
		}

		if (!form.getPassword().equals(form.getRepeatPassword())) {
			errors.rejectValue("password", "error.password.mismatch");
		}
	}
}