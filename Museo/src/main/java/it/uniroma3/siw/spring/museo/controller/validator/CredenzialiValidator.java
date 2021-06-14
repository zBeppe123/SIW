package it.uniroma3.siw.spring.museo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.museo.model.Credenziali;
import it.uniroma3.siw.spring.museo.service.CredenzialiService;

@Component
public class CredenzialiValidator implements Validator {
	private final int MIN_USERNAME_LENGTH = 4;
	private final int MAX_USERNAME_ACCETTABILI = 100;
	private final int MIN_PASSWORD_LENGTH = 6;
	
	@Autowired
	private CredenzialiService credenzialiSerive;
	/**
	 * funzione validate per Credential
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		Credenziali credenziali = (Credenziali) obj;
		
		if(credenziali.getUsername().length()<MIN_USERNAME_LENGTH ||
				credenziali.getUsername().length()>MAX_USERNAME_ACCETTABILI) {//controllo della lunghezza minima e massima per username
			errors.rejectValue("username", "size");
		}
		if(credenziali.getPassword().length()<MIN_PASSWORD_LENGTH) {//controllo della lunghezza minima e massima per password
			errors.rejectValue("password", "size");
		}
		
		if(this.credenzialiSerive.getCredenziali(credenziali.getUsername()) != null) {//controllo per vedere se non esiste un duplicato nel db
			errors.rejectValue("username", "duplicato");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Credenziali.class.equals(clazz);
	}
}
