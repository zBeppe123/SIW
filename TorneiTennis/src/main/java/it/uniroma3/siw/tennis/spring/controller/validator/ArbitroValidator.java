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

/** Validator per Arbitro. */
@Component
public class ArbitroValidator implements Validator {
	private static final Logger logger = LoggerFactory.getLogger(ArbitroValidator.class);
	private static final int MIN_CIFRE_TELEFONO = 10;
	
	@Autowired
	private ArbitroService arbitroService;
	
	/** Valida la correttezza dell'arbitro.
	 * Verifica sulla base se campi sono vuoti o meni, se esiste gia' un arbitro con lo stesso nome e se sia inserito
	 * numero telefonico che abbia il minino di cifre prestabilite.
	 */
	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognome", "registra_arbitro_errors");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "registra_arbitro_errors");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "telefono", "registra_arbitro_errors");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nazionalita", "registra_arbitro_errors");
		
		if(!errors.hasErrors()) {
			logger.debug("Tutti dati dell'arbitro inseriti non nulli.");
			
			Arbitro arbitro = (Arbitro) o;
			if(this.arbitroService.alreadyExists(arbitro)) {
				logger.debug("Arbitro gia' esistente.");
				errors.reject("registra_arbitro_errors_duplicato");
			}
			else if(arbitro.getTelefono().length()<MIN_CIFRE_TELEFONO) {
				logger.debug("Numero di cifre di telefono di arbitro non ha com minio 10 cifre.");
				errors.reject("registra_arbitro_errors_telefono");
			}
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Arbitro.class.equals(clazz);
	}
}
