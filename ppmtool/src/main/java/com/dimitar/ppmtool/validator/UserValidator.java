package com.dimitar.ppmtool.validator;

import com.dimitar.ppmtool.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        final User user = (User) object;

        if (user.getPassword() == null) {
            errors.rejectValue("password", "Length", "Password can not be null.");
        }
        else if (user.getPassword().length() < 6) {
            errors.rejectValue("password", "Length", "Password must be at least 6 characters long.");
        }

        if (user.getPassword() != null && !user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "Match", "Passwords must match.");
        }
    }
}
