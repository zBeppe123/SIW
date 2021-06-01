package it.uniroma3.siw.spring.museo.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.museo.model.Utente;

@Component
public class UtenteValidator implements Validator {
	private final int MIN_CARATTERI_ACCETTABILI = 4;
	private final int MAX_CARATTERI_ACCETTABILI = 100;
	
	@Override
	public void validate(Object obj, Errors errors) {
		Utente utente = (Utente) obj;
		
		if((utente.getCognome().length() < MIN_CARATTERI_ACCETTABILI) ||
				(utente.getCognome().length() > MAX_CARATTERI_ACCETTABILI)) {
			errors.rejectValue("cognome", "size");
		}
		if((utente.getNome().length() < MIN_CARATTERI_ACCETTABILI) ||
				(utente.getNome().length() > MAX_CARATTERI_ACCETTABILI)) {
			errors.rejectValue("nome", "size");
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Utente.class.equals(clazz);
	}
}
