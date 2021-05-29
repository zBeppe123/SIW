package it.uniroma3.siw.tennis.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.tennis.spring.controller.validator.CredentialsValidator;
import it.uniroma3.siw.tennis.spring.controller.validator.TennistaValidator;
import it.uniroma3.siw.tennis.spring.model.Credentials;
import it.uniroma3.siw.tennis.spring.model.Tennista;
import it.uniroma3.siw.tennis.spring.service.CredentialsService;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private TennistaValidator tennistaValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET) 
	public String showRegisterForm (Model model) {
		model.addAttribute("tennista", new Tennista());
		model.addAttribute("credentials", new Credentials());
		return "registraTennista";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String showLoginForm (Model model) {
        System.out.println("entro in login");
		return "loginForm";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(Model model) {
		return "index";
	}
	
    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultAfterLogin(Model model) {
    	UserDetails tennistaDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(tennistaDetails.getUsername());    	
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
    		System.out.println("sei un admin");
            return "admin/home";
        }
    	model.addAttribute("tennista",credentials.getTennista());
    	System.out.println("prego");
        return "home";
    }
	
    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("tennista") Tennista tennista,
                 BindingResult tennistaBindingResult,
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {
        // validate user and credentials fields
        this.tennistaValidator.validate(tennista, tennistaBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);

        // if neither of them had invalid contents, store the User and the Credentials into the DB
        if(!tennistaBindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
            // set the user and store the credentials;
            // this also stores the User, thanks to Cascade.ALL policy
        	credentials.setTennista(tennista);
            credentialsService.saveCredentials(credentials);
            return "registrationSuccessful";
        }
        return "registraTennista";
    }
    
    @RequestMapping(value = "/index", method = RequestMethod.GET) 
	public String showIndex (Model model) {
		return "index";
	}
    
}
