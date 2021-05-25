package it.uniroma3.siw.tennis.spring.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.tennis.spring.model.Tennista;


/**
 * Validator for Tennista
 */
@Component
public class TennistaValidator implements Validator {

    final Integer MAX_NAME_LENGTH = 100;
    final Integer MIN_NAME_LENGTH = 2;

    @Override
    public void validate(Object o, Errors errors) {
        Tennista tennista = (Tennista) o;
        String nome = tennista.getNome().trim();
        String cognome = tennista.getCognome().trim();

        if (nome.isEmpty())
            errors.rejectValue("nome", "required");
        else if (nome.length() < MIN_NAME_LENGTH || nome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("nome", "size");

        if (cognome.isEmpty())
            errors.rejectValue("cognome", "required");
        else if (cognome.length() < MIN_NAME_LENGTH || cognome.length() > MAX_NAME_LENGTH)
            errors.rejectValue("cognome", "size");
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Tennista.class.equals(clazz);
    }

}

