package it.uniroma3.siw.tennis.spring.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@Component
public class PartitaValidator{
	public void controllaId(Long idTennista1, Long idTennista2, Errors errors) {
		if(idTennista1.equals(idTennista2)) {
			errors.reject("registra_partita_errors_SelezionatiTennistiUguali");
		}
	}
}
