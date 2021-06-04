package it.uniroma3.siw.tennis.spring.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.tennis.spring.model.Arbitro;
import it.uniroma3.siw.tennis.spring.service.ArbitroService;

@Component
public class ArbitroModificatoValidator implements Validator {
	private final int MIN_CIFRE_TELEFONO = 10;
	
	@Autowired
	private ArbitroService arbitroService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Arbitro.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		Arbitro arbitro = (Arbitro) obj;
		Arbitro a = this.arbitroService.arbitroPerNomeAndCognome(arbitro.getNome(), arbitro.getCognome());
		
		if(a!=null) {
			if(this.arbitroService.alreadyExists(arbitro) && !arbitro.getId().equals(a.getId())) {
				errors.reject("modifica_arbitro_errors_duplicato");
			}
		}
		
		if(arbitro.getTelefono().length()<MIN_CIFRE_TELEFONO) {
			errors.reject("modifica_arbitro_errors_telefono");
		}
	}

}
