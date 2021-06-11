package it.uniroma3.siw.spring.museo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.museo.model.Credenziali;
import it.uniroma3.siw.spring.museo.service.CredenzialiService;

@Controller
public class AuthenticationController {
	@Autowired
	private CredenzialiService credenzialiService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String apriPaginaLogin(Model model) {
		return "login.html";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(Model model) {
		return "index.html";
	}
	
	@RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultAfterLogin(Model model) {
    	UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credenziali credenziali = this.credenzialiService.getCredenziali(userDetails.getUsername());
    	
    	//L'utente autenticato e' un amministratore?
    	if (credenziali.getRole().equals(Credenziali.ADMIN_ROLE)) {
            return "admin/home.html";
        }
    	
        return "home.html";
    }
}
