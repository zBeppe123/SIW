package it.uniroma3.siw.tennis.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.tennis.spring.model.Arbitro;
import it.uniroma3.siw.tennis.spring.service.ArbitroService;

@Component
public class ArbitroValidator implements Validator {
	private static final Logger logger = LoggerFactory.getLogger(ArbitroValidator.class);
	
	@Autowired
	private ArbitroService arbitroService;
	
	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "registra_arbitro_errors");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "registra_arbitro_errors");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telefono", "registra_arbitro_errors");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nazionalita", "registra_arbitro_errors");
		
		if(!errors.hasErrors()) {
			logger.debug("Tutti dati dell'arbitro inseriti non nulli.");
			
			if(this.arbitroService.alreadyExists((Arbitro) o)) {
				logger.debug("Arbitro gia' esistente.");
				errors.reject("registra_arbitro_errors_duplicato");
			}
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Arbitro.class.equals(clazz);
	}
}
