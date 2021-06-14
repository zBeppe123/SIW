package it.uniroma3.siw.tennis.spring.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/** Validator per Partita. */
@Component
public class PartitaValidator{
	/** Verifica se la partita finita da registrare viene selezionato due tennisti che sono uguali. */
	public void controllaId(Long idTennista1, Long idTennista2, Errors errors) {
		if(idTennista1.equals(idTennista2)) {
			errors.reject("registra_partita_errors_SelezionatiTennistiUguali");
		}
	}
}
