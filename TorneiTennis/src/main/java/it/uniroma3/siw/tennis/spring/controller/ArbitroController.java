package it.uniroma3.siw.tennis.spring.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import it.uniroma3.siw.tennis.spring.controller.validator.ArbitroValidator;
import it.uniroma3.siw.tennis.spring.model.Arbitro;
import it.uniroma3.siw.tennis.spring.service.ArbitroService;

@Controller
public class ArbitroController {
	@Autowired
	private ArbitroService arbitroService;
	
	@Autowired
	private ArbitroValidator arbitroValidator;
	
	private static final Logger logger = LoggerFactory.getLogger(ArbitroController.class);
	
	@RequestMapping(value = "/admin/registraArbitro", method = RequestMethod.GET)
	public String apriRegistraArbitro(Model model) {
		model.addAttribute("arbitro", new Arbitro());
		return "admin/registraArbitro.html";
	}
	
	@RequestMapping(value = "/admin/registraArbitro", method = RequestMethod.POST)
	public String registraNuovoArbitro(@ModelAttribute("arbitro") Arbitro arbitro, Model model, BindingResult bindingResult) {
		logger.debug("registrazione nuovo arbitro.");
		
		this.arbitroValidator.validate(arbitro, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			logger.debug("Registrazione arbitro effettuta.");
			arbitroService.inserisci(arbitro);
			
			model.addAttribute("arbitro", arbitro);
			return "admin/registrazioneArbitroCompletata.html";
		}
		
		return "admin/registraArbitro.html";
	}
	
	@RequestMapping(value = "/admin/sceltaArbitroPerModifica", method = RequestMethod.GET)
	public String apriSceltaArbitroPerModifica(Model model) {
		model.addAttribute("arbitri", this.arbitroService.tutti());
		return "admin/sceltaArbitroPerModifica.html";
	}
	
	@RequestMapping(value = "/admin/sceltaArbitroPerModifica", method = RequestMethod.POST)
	public String sceltoArbitroPerModifica(@RequestParam("arbitroSelezionato") Long idArbitro, Model model, HttpSession sessione) {
		Arbitro arbitro = this.arbitroService.arbitroPerId(idArbitro);
		
		//Nessun arbitro?
		if(arbitro == null) {
			//model.addAttribute("arbitri", this.arbitroService.tutti());
			//return "admin/sceltaArbitroPerModifica.html";
			return "redirect:/admin/sceltaArbitroPerModifica";
		}
		
		//model.addAttribute("arbitro", arbitro);
		//return "admin/modificaArbitro.html";
		sessione.setAttribute("arbitroPerModifica", arbitro);
		return "redirect:/admin/modificaArbitro";
	}
	
	@RequestMapping(value = "/admin/modificaArbitro", method = RequestMethod.GET)
	public String apriModificaArbitro(Model model, HttpSession sessione) {
		Arbitro a = (Arbitro) sessione.getAttribute("arbitroPerModifica");
		
		model.addAttribute("arbitro", a);
		return "admin/modificaArbitro.html";
	}
	
	@RequestMapping(value = "/admin/modificaArbitro", method = RequestMethod.POST)
	public String modificaDatiArbitro(@ModelAttribute("arbitro") Arbitro arbitroModificato, Model model, BindingResult bindingResult ,HttpSession sessione) {
		this.arbitroValidator.validate(arbitroModificato, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			Arbitro a = (Arbitro) sessione.getAttribute("arbitroPerModifica");
			arbitroModificato.setId(a.getId());
			this.arbitroService.modificaDatiDi(arbitroModificato);
			
			sessione.removeAttribute("arbitroPerModifica");
			
			return "/admin/modificaArbitroCompletata.html";
		}
		
		return "/admin/modificaArbitro.html";
	}
	
	@RequestMapping(value = "/arbitro/{id}", method = RequestMethod.GET)
	public String getArbitro(@PathVariable("id") Long idArbitro, Model model) {
		logger.debug("Lettura arbitro.");
		
		model.addAttribute("arbitro", this.arbitroService.arbitroPerId(idArbitro));
		return "arbitro.html";
	}
}
