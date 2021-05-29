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
	
	public 	Tennista getTennistaAttuale(){
		UserDetails tennistaDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(tennistaDetails.getUsername());
    	return credentials.getTennista();
	}
}
