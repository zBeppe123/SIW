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

    final Integer MIN_USERNAME_LENGTH = 4;
    final Integer MAX_PASSWORD_LENGTH = 20;
    final Integer MIN_PASSWORD_LENGTH = 6;

    /** Valida la corretteza di Credentials.
     * Verifica sulla base di: se username e password rispettano le lunghezze dei caratteri prestabiliti
     * e se esiste gia' un username uguale 
     */
    @Override
    public void validate(Object o, Errors errors) {
        Credentials credentials = (Credentials) o;
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        if (username.isEmpty())
            errors.rejectValue("username", "required");
        else if (username.length() < MIN_USERNAME_LENGTH)
            errors.rejectValue("username", "size");
        else if (this.credentialsService.getCredentials(username) != null)
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