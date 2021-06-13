package it.uniroma3.siw.spring.museo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.museo.model.Collezione;
import it.uniroma3.siw.spring.museo.service.CollezioneService;
@Component
public class CollezioneValidator implements Validator{
	@Autowired
	private CollezioneService collezioneService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Collezione.class.equals(clazz);
	}
	/**
	 * funzione validate per Collezione, il controllo principale è vedere se non esiste già una collezione con lo stesso nome
	 */
	@Override
	public void validate(Object target, Errors errors) {
		Collezione c= (Collezione) target;
		if(this.collezioneService.alreadyExists(c)) {
			errors.reject("registra_collezione_errors_duplicato");
		}
		
	}

}