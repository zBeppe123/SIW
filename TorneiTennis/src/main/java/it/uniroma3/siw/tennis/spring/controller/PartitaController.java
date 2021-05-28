package it.uniroma3.siw.tennis.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.tennis.spring.controller.validator.PartitaValidator;
import it.uniroma3.siw.tennis.spring.model.Partita;
import it.uniroma3.siw.tennis.spring.service.PartitaService;
import it.uniroma3.siw.tennis.spring.service.TennistaService;
import it.uniroma3.siw.tennis.spring.service.TorneoService;

@Controller
public class PartitaController {
	@Autowired
	private PartitaService partitaService;
	
	@Autowired
	private TorneoService torneoService;
	
	@Autowired
	private TennistaService tennistaService;
	
	@Autowired
	private PartitaValidator partitaValidator;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @RequestMapping(value = "/registraPartita", method = RequestMethod.GET)
    public String apriRegistraPartita(Model model) {
    	System.out.println("provo");
    	model.addAttribute("partita", new Partita());
    	model.addAttribute("toreni", torneoService.tutti());
    	model.addAttribute("tennisti", tennistaService.tutti());
    	System.out.println("provo2");
    	return "registraPartita.html";
    }
    
    @RequestMapping(value = "/registraPartita", method = RequestMethod.POST)
    public String registraNuovaPartita(@ModelAttribute("partita") Partita partita,@ModelAttribute("torn") String idTorneo, @RequestParam("gioc1") String idTennista1, @RequestParam("gioc2") String idTennista2, Model model, BindingResult bindingResult) {
    	this.partitaValidator.validate(partita, bindingResult);
    	this.partitaValidator.controllaId(idTorneo, idTennista1, idTennista2, bindingResult);
    	
    	//I dati sono validi?
    	if(!bindingResult.hasErrors()) {
    		
    		partita.setTorneo(torneoService.torneoPerId(Long.parseLong(idTorneo)));
    		partita.setTennista1(tennistaService.tennistaPerId(Long.parseLong(idTennista1)));
    		partita.setTennista2(tennistaService.tennistaPerId(Long.parseLong(idTennista1)));
    		this.partitaService.inserisci(partita);
    		return "index.html";
    	}
    	else {
    		model.addAttribute("arbitri", this.partitaService.tutti());
    		return "registraPartita";
    	}
    }
    
	
	
	
}
