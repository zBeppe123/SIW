package it.uniroma3.siw.tennis.spring.controller.validator;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.tennis.spring.model.Torneo;
import it.uniroma3.siw.tennis.spring.service.TorneoService;

/** Validatoe per Torneo modificato. */
@Component
public class TorneoModificatoValidator implements Validator {
	@Autowired
	private TorneoService torneoService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Torneo.class.equals(clazz);
	}

	/** Valida la correttenza dei dati di Torneo modificato. 
	 * Verifica sulla base di: se non esiste gia' un torneo con lo stesso nome e se mese e anno inseriti
	 * sono validi. */
	@Override
	public void validate(Object obj, Errors errors) {
		Torneo torneo = (Torneo) obj;
		Torneo t = this.torneoService.torneoPerNome(torneo.getNome());
		
		if(t!=null) {
			if(this.torneoService.alreadyExists(torneo) && !torneo.getId().equals(t.getId())) {
				errors.reject("modifica_torneo_errors_duplicato");
			}
		}
		
		LocalDate dataOdierna = LocalDate.now();
		if((torneo.getMese()<=dataOdierna.getMonthValue() && torneo.getAnno().intValue()==dataOdierna.getYear()) ||
				torneo.getAnno()<dataOdierna.getYear()) {
			errors.reject("modifica_torneo_errors_dataErrata");
		}
	}

}
