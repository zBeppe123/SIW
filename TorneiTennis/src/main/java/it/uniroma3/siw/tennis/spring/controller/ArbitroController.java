package it.uniroma3.siw.tennis.spring.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.tennis.spring.controller.validator.ArbitroModificatoValidator;
import it.uniroma3.siw.tennis.spring.controller.validator.ArbitroValidator;
import it.uniroma3.siw.tennis.spring.model.Arbitro;
import it.uniroma3.siw.tennis.spring.model.Credentials;
import it.uniroma3.siw.tennis.spring.service.ArbitroService;
import it.uniroma3.siw.tennis.spring.utili.Utili;

@Controller
public class ArbitroController {
	@Autowired
	private ArbitroService arbitroService;
	
	@Autowired
	private ArbitroValidator arbitroValidator;
	
	@Autowired
	private ArbitroModificatoValidator arbitroModificatoValidator;
	
	@Autowired
	Utili utili;
	
	private static final Logger logger = LoggerFactory.getLogger(ArbitroController.class);
	
	@RequestMapping(value = "/admin/registraArbitro", method = RequestMethod.GET)
	public String apriRegistraArbitro(Model model) {
		model.addAttribute("arbitro", new Arbitro());
		return "admin/registra/registraArbitro";
	}
	
	@RequestMapping(value = "/admin/registraArbitro", method = RequestMethod.POST)
	public String registraNuovoArbitro(@ModelAttribute("arbitro") Arbitro arbitro, Model model, BindingResult bindingResult) {
		logger.debug("registrazione nuovo arbitro.");
		
		this.arbitroValidator.validate(arbitro, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			logger.debug("Registrazione arbitro effettuta.");
			arbitroService.inserisci(arbitro);
			
			model.addAttribute("arbitro", arbitro);
			return "admin/registra/registrazioneArbitroCompletata";
		}
		
		return "admin/registra/registraArbitro";
	}
	
	@RequestMapping(value = "/admin/sceltaArbitroPerModifica", method = RequestMethod.GET)
	public String apriSceltaArbitroPerModifica(Model model) {
		model.addAttribute("arbitri", this.arbitroService.tutti());
		return "admin/modifica/sceltaArbitroPerModifica.html";
	}
	
	@RequestMapping(value = "/admin/sceltaArbitroPerModifica", method = RequestMethod.POST)
	public String sceltoArbitroPerModifica(@RequestParam("arbitroSelezionato") Long idArbitro, Model model) {
		Arbitro arbitro = this.arbitroService.arbitroPerId(idArbitro);
		model.addAttribute("arbitro", arbitro);
		return "/admin/modifica/modificaArbitro";
	}
	
	@RequestMapping(value = "/admin/modificaArbitro", method = RequestMethod.POST)
	public String modificaDatiArbitro(@ModelAttribute("arbitro") Arbitro arbitroModificato, Model model, BindingResult bindingResult) {
		this.arbitroModificatoValidator.validate(arbitroModificato, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			this.arbitroService.modificaDatiDiArbitro(arbitroModificato);
			
			return "/admin/modifica/modificaArbitroCompletata";
		}
		
		return "/admin/modifica/modificaArbitro";
	}
	
	@RequestMapping(value = "/arbitro/{id}", method = RequestMethod.GET)
	public String getArbitro(@PathVariable("id") Long idArbitro, Model model) {
		logger.debug("Lettura arbitro.");
		
		Arbitro arbitro = this.arbitroService.arbitroPerId(idArbitro);
		String s=utili.getCredentials().getRole();
		
		model.addAttribute("arbitro", arbitro);
		if(arbitro!=null)
			model.addAttribute("tornei", arbitro.getTornei());
		if(s.equals(Credentials.ADMIN_ROLE))
			model.addAttribute("amministratre", "admin");
		else if(s.equals(Credentials.DEFAULT_ROLE))
			model.addAttribute("utente", "user");
		else
			model.addAttribute("nessuno", "anonimo");
		
		
		return "arbitro.html";
	}
	
	@RequestMapping(value="/admin/cancellaArbitro", method=RequestMethod.GET)
	public String apriCancellaArbitro(Model model) {
		model.addAttribute("arbitri",this.arbitroService.arbitriNonImpegnati());
		return "admin/cancella/cancellaArbitro";
	}
	
	@RequestMapping(value="/admin/cancellaArbitro", method=RequestMethod.POST)
	public String cancellaArbitro(@RequestParam("arbitroSelezionato") Long idArbitro,Model model,HttpSession sessione) {
		this.arbitroService.cancellaArbitro(idArbitro);
		return "admin/cancella/cancellazioneArbitroCompletata";
	}

}
