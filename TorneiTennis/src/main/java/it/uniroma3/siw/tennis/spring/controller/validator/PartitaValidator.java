package it.uniroma3.siw.tennis.spring.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.siw.tennis.spring.model.Partita;
import it.uniroma3.siw.tennis.spring.service.PartitaService;

@Component
public class PartitaValidator implements Validator {

	@Autowired
	private PartitaService partitaService;

	private static final Logger logger = LoggerFactory.getLogger(PartitaValidator.class);

	@Override
	public void validate(Object o,Errors errors) {
		logger.debug("Controllo dei dati della partita immessi.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "punteggioG1", "registra_partita_errors");
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "punteggioG2", "registra_partita_errors");
	}

	public void controllaId(String idToreno,String idTennista1, String idTennista2, Errors errors) {
		if(idTennista1.isEmpty()) {
    		errors.reject("registra_partita_errors_tennista1NonSelezionato");
    	}
		if(idTennista2.isEmpty()) {
    		errors.reject("registra_partita_errors_tennista2NonSelezionato");
    	}
		if(idTennista1.equals(idTennista2)) {
			errors.reject("registra_partita_errors_SelezionatiTennistiUguali");
		}
		if(idToreno.isEmpty()) {
    		errors.reject("registra_partita_errors_torneoNonSelezionato");
    	}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Partita.class.equals(clazz);
	}

}
