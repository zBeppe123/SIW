package it.uniroma3.siw.tennis.spring.controller;

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
	
	@RequestMapping(value = "/registraArbitro", method = RequestMethod.GET)
	public String apriRegistraArbitro(Model model) {
		model.addAttribute("arbitro", new Arbitro());
		return "registraArbitro.html";
	}
	
	@RequestMapping(value = "/registraArbitro", method = RequestMethod.POST)
	public String registraNuovoArbitro(@ModelAttribute("arbitro") Arbitro arbitro, Model model, BindingResult bindingResult) {
		logger.debug("registrazione nuovo arbitro.");
		
		this.arbitroValidator.validate(arbitro, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			logger.debug("Registrazione arbitro effettuta.");
			
			arbitroService.inserisci(arbitro);
			return "index.html";
		}
		return "registraArbitro.html";
	}
	
	@RequestMapping(value = "/arbitro/{id}", method = RequestMethod.GET)
	public String getArbitro(@PathVariable("id") Long idArbitro, Model model) {
		logger.debug("Lettura arbitro.");
		
		model.addAttribute("arbitro", this.arbitroService.arbitroPerId(idArbitro));
		return "arbitro.html";
	}
}
