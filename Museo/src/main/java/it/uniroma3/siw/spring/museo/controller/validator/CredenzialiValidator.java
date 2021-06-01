package it.uniroma3.siw.spring.museo.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.museo.model.Credenziali;

@Component
public class CredenzialiValidator implements Validator {
	private final int MIN_USERNAME_LENGTH = 4;
	private final int MAX_USERNAME_ACCETTABILI = 100;
	private final int MIN_PASSWORD_LENGTH = 4;
	
	@Override
	public void validate(Object obj, Errors errors) {
		Credenziali credenziali = (Credenziali) obj;
		
		if(credenziali.getUsername().length()<MIN_USERNAME_LENGTH ||
				credenziali.getUsername().length()>MAX_USERNAME_ACCETTABILI) {
			errors.rejectValue("username", "size");
		}
		if(credenziali.getPassword().length()<MIN_PASSWORD_LENGTH) {
			errors.rejectValue("password", "size");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Credenziali.class.equals(clazz);
	}
}
