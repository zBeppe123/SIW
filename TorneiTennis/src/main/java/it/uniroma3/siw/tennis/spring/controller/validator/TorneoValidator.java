package it.uniroma3.siw.tennis.spring.controller.validator;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.tennis.spring.model.Torneo;
import it.uniroma3.siw.tennis.spring.service.TorneoService;

@Component
public class TorneoValidator implements Validator {
	@Autowired
	private TorneoService torneoService;
	
    private static final Logger logger = LoggerFactory.getLogger(TorneoValidator.class);

    @Override
	public void validate(Object o, Errors errors) {
    	logger.debug("Controllo dei dati del torneo immessi.");
    	
    	//Controlla i campi vuoti.
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nome", "registra_torneo_errors");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numeroMaxDiPartecipanti", "registra_torneo_errors");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mese", "registra_torneo_errors");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "anno", "registra_torneo_errors");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "premioInDenaro", "registra_torneo_errors");
    	
    	if(!errors.hasErrors()) {
    		logger.debug("Tutti dati del torneo immessi.");
    		Torneo torneo = (Torneo) o;
    		
    		//Esiste gia' un torneo con lo stesso nome?
    		if(this.torneoService.alreadyExits(torneo)) {
    			logger.debug("Torneo gia' esistente.");
    			errors.reject("registra_torneo_errors_duplicato");
    		}
    		
    		//Mese e anno inseriti sono corretti?
    		LocalDate dataOdierna = LocalDate.now();
    		if((torneo.getMeseValore()<=dataOdierna.getMonthValue() && torneo.getAnno().intValue()==dataOdierna.getYear()) ||
    				torneo.getAnno()<dataOdierna.getYear()) {
    			errors.reject("registra_torneo_errors_dataErrata");
    		}
    	}
    	else {
    		logger.debug("C'e' almeno un campo vuoto in registraTorneo.html");
    	}
		
	}
    
	@Override
	public boolean supports(Class<?> clazz) {
		return Torneo.class.equals(clazz);
	}
}
