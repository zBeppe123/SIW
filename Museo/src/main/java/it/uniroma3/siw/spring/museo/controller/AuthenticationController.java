package it.uniroma3.siw.spring.museo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.museo.controller.validator.CredenzialiValidator;
import it.uniroma3.siw.spring.museo.controller.validator.UtenteValidator;
import it.uniroma3.siw.spring.museo.model.Credenziali;
import it.uniroma3.siw.spring.museo.model.Utente;
import it.uniroma3.siw.spring.museo.service.CredenzialiService;

@Controller
public class AuthenticationController {
	@Autowired
	private UtenteValidator utenteValidator;
	
	@Autowired
	private CredenzialiValidator credenzialiValidator;

	@Autowired
	private CredenzialiService credenzialiService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String apriPaginaLogin(Model model) {
		return "loginForm.html";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(Model model) {
		return "index.html";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET) 
	public String apriPaginaRegistrazione(Model model) {
		model.addAttribute("user", new Utente());
		model.addAttribute("credentials", new Credenziali());
		return "registrazione.html";
	}
	
	@RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String registraUtente(@ModelAttribute("utente") Utente utente, BindingResult utenteBindingResult,
                 				 @ModelAttribute("credenziali") Credenziali credenziali,
                 				 BindingResult credentialiBindingResult, Model model) {
		
        this.utenteValidator.validate(utente, utenteBindingResult);
        this.credenzialiValidator.validate(credenziali, credentialiBindingResult);

        //Dati inseriti di utente e credenziali sono corretti?
        if(!utenteBindingResult.hasErrors() && ! credentialiBindingResult.hasErrors()) {
            credenziali.setUtente(utente);
            this.credenzialiService.saveCredenziali(credenziali);
            
            return "registrazioneCompletata.html";
        }
        
        return "registrazione.html";
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
