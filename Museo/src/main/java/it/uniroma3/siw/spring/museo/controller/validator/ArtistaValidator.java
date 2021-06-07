package it.uniroma3.siw.spring.museo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.spring.museo.model.Artista;
import it.uniroma3.siw.spring.museo.service.ArtistaService;

@Component
public class ArtistaValidator implements Validator {
	@Autowired
	private ArtistaService artistaService;

	@Override
	public void validate(Object obj, Errors errors) {
		Artista artista = (Artista) obj;
		
		if(this.artistaService.alreadyExists(artista)) {
			errors.reject("registra_artista_errors_duplicato");
		}
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Artista.class.equals(clazz);
	}
}
