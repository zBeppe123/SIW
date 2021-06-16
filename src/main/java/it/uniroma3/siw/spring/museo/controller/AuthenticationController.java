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
	/**
	 * Questa funzione apre la pagina del login
	 * @param model
	 * @param artista
	 * @param bindingResult
	 * @return stringa riferita alla pagina login.html
	 */

	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String apriPaginaLogin(Model model) {
		return "login.html";
	}
	
	/**
	 * Questa pagina fa tornare l'utente all'index dopo aver premuto il tasto logout
	 * @param model
	 * @param artista
	 * @param bindingResult
	 * @return stringa riferita a index.html
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(Model model) {
		return "index.html";
	}
	/**
	 * questa funzione porta alla home dell'utente entrato
	 * @param model
	 * @param artista
	 * @param bindingResult
	 * @return stringa admin/home se il ruolo dell'utente è admin altrimenti la stringa home per utenti normali
	 */

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
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registraAdmin(Model model) {
		Credenziali c = new Credenziali();
		c.setUsername("admin");
		c.setUsername("123456");
		credenzialiService.saveCredenziali(c);
		return "index";
	}
}
