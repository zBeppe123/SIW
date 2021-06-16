package it.uniroma3.siw.tennis.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import it.uniroma3.siw.tennis.spring.service.PartitaService;
import it.uniroma3.siw.tennis.spring.utili.Utili;

@Controller
public class AuthenticationController {
	@Autowired
	private Utili utili;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private PartitaService partitaService;
	
	@Autowired
	private TennistaValidator tennistaValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	/** Apre la pagina per la registrazione di un nuovo tennista.
	 * @param model
	 * @return Stringa riferita a registraTennista.html
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET) 
	public String showRegisterForm (Model model) {
		model.addAttribute("tennista", new Tennista());
		model.addAttribute("credentials", new Credentials());
		return "registraTennista";
	}
	
	/** Apre la pagina di login
	 * @param model
	 * @return Stringa riferita a loginForm.html
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET) 
	public String showLoginForm (Model model) {
		return "loginForm";
	}
	
	/** Effettua il logout dell'utente che ha fatto l'accesso.
	 * @param model
	 * @return Stringa riferita a index.html
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET) 
	public String logout(Model model) {
		return "index";
	}
	
	/** Apre la pagina home dell'utente acceduto.
	 * @param model
	 * @return Stringa riferita a "/utente/home.html" se l'utente che accede e' una tennista,
	 * 			altrimenti a "/admin/home" quando accede l'admin. 
	 */
    @RequestMapping(value = "/default", method = RequestMethod.GET)
    public String defaultAfterLogin(Model model) {
    	Credentials credentials = utili.getCredentials();
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/home";
        }
    	Tennista tennista=credentials.getTennista();
    	model.addAttribute("tennista",tennista);
    	model.addAttribute("partite",partitaService.getPartiteByTennista(tennista.getId()));
        return "utente/home";
    }
	
    /** Effettua la registra di un nuovo tennista
     * @param tennista
     * @param tennistaBindingResult
     * @param credentials
     * @param credentialsBindingResult
     * @param model
     * @return Stringa riferita a registrationSuccessful se la registrazione e' andata a buon fine,
     * 			altrimenti a registraTennista.html
     */
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
    
}
