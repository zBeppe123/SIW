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
public class PartitaValidator{
	public void controllaId(Long idTennista1, Long idTennista2, Errors errors) {
		if(idTennista1.equals(idTennista2)) {
			errors.reject("registra_partita_errors_SelezionatiTennistiUguali");
		}
	}
}
