package it.uniroma3.siw.tennis.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.tennis.spring.model.Credentials;
import it.uniroma3.siw.tennis.spring.model.Tennista;
import it.uniroma3.siw.tennis.spring.service.CredentialsService;

/**
 * Validator for Credentials
 */
@Component
public class CredentialsValidator implements Validator {

    @Autowired
    private CredentialsService credentialsService;

    final Integer MAX_USERNAME_LENGTH = 20;
    final Integer MIN_USERNAME_LENGTH = 4;
    final Integer MAX_PASSWORD_LENGTH = 20;
    final Integer MIN_PASSWORD_LENGTH = 6;

    @Override
    public void validate(Object o, Errors errors) {
        Credentials credentials = (Credentials) o;
        String email = credentials.getEmail().trim();
        String password = credentials.getPassword().trim();

        if (email.isEmpty())
            errors.rejectValue("username", "required");
        else if (email.length() < MIN_USERNAME_LENGTH || email.length() > MAX_USERNAME_LENGTH)
            errors.rejectValue("username", "size");
        else if (this.credentialsService.getCredentials(email) != null)
            errors.rejectValue("username", "duplicate");

        if (password.isEmpty())
            errors.rejectValue("password", "required");
        else if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH)
            errors.rejectValue("password", "size");

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Tennista.class.equals(clazz);
    }

}