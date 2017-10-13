package hello.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

import hello.domain.RegistrationForm;

public class RegistrationValidator implements Validator {
    @Override
    public boolean supports(final Class<?> clazz) {
        return clazz.isAssignableFrom(RegistrationForm.class);
    }

	@Override
	public void validate(Object target, Errors errors) {
		final RegistrationForm form = (RegistrationForm)target;

		if (!form.getPassword().equals(form.getRepeatPassword())) {
			errors.rejectValue("password", "Lösenorden matchar inte");
		}
	}
}