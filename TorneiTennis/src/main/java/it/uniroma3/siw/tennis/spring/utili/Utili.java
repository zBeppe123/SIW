package it.uniroma3.siw.tennis.spring.utili;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import it.uniroma3.siw.tennis.spring.model.Credentials;
import it.uniroma3.siw.tennis.spring.model.Tennista;
import it.uniroma3.siw.tennis.spring.service.CredentialsService;

@Controller
public class Utili {
	
	@Autowired
	private CredentialsService credentialsService;
	/*
	 * Ritorna il tennista attualmente loggato
	 */
	public Tennista getTennista(){
    	return credentialsService.getCredentials(this.getUserDetails().getUsername()).getTennista();
	}
	/*
	 * Ritorna Le credenziali dell'utente attualmente loggato
	 */
	public Credentials getCredentials() {
		return credentialsService.getCredentials(this.getUserDetails().getUsername());
	}
	/*
	 * Ritorna i UserDetails attualmente loggato
	 */
	private UserDetails getUserDetails() {
		return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
