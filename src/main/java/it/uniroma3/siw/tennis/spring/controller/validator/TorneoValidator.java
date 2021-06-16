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

/** Validator per Torneo. */
@Component
public class TorneoValidator implements Validator {
	@Autowired
	private TorneoService torneoService;
	
    private static final Logger logger = LoggerFactory.getLogger(TorneoValidator.class);

    /** Valida la corretteza del torneo.
     * Verifica sulla base di se icampi sono vuoti o meno, se siste gia' un torneo con lo stesso nome e che non sia o no inserita
     * il mese e o l'anno non validi.
     */
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
    		if(this.torneoService.alreadyExists(torneo)) {
    			logger.debug("Torneo gia' esistente.");
    			errors.reject("registra_torneo_errors_duplicato");
    		}
    		
    		//Mese e anno sono corretti?
    		LocalDate dataOdierna = LocalDate.now();
    		if((torneo.getMese()<=dataOdierna.getMonthValue() && torneo.getAnno().intValue()==dataOdierna.getYear()) ||
    				torneo.getAnno()<dataOdierna.getYear()) {
    			errors.reject("registra_torneo_errors_dataErrata");
    		}
    		
    		//Numero partecipanti > 8?
    		if(torneo.getNumeroMaxDiPartecipanti()<4) {
    			errors.reject("registra_torneo_errors_numeroMinimoDiPartecipanti");
    		}
    		
    	}
    	else {
    		logger.debug("C'e' almeno un campo vuoto in registraTorneo.html");
    	}
		
	}
    
    /** Verifica se l'arbitro e' stato selezionto o meno. */
    public void controllaCampoIdArbitro(String idArbitro, Errors errors) {
    	if(idArbitro.isEmpty()) {
    		errors.reject("registra_torneo_errors_arbitroNonSelezionato");
    	}
    }
    
	@Override
	public boolean supports(Class<?> clazz) {
		return Torneo.class.equals(clazz);
	}
}
