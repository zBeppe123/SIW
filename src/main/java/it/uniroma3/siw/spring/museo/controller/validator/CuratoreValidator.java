package it.uniroma3.siw.spring.museo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.museo.model.Curatore;
import it.uniroma3.siw.spring.museo.service.CuratoreService;

@Component
public class CuratoreValidator implements Validator{
	
	@Autowired
	private CuratoreService curatoreService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Curatore.class.equals(clazz);
	}
	/**
	 * validate per Curatore, controllo sulla matricola per non inserire duplicati
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		Curatore curatore = (Curatore) obj;
		
		if(this.curatoreService.matricolaAlreadyExists(curatore.getMatricola())) {
			errors.reject("registra_curatore_errors_matricolaDuplicata");
		}
		
	}

}
