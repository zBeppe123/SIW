package it.uniroma3.siw.tennis.spring.controller;

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
	
	
	
	@RequestMapping(value = "/admin/selezionaTorneoPerRPartita", method = RequestMethod.GET)
    public String apriSelezionaTorenoPerRPartita(Model model) {
    	model.addAttribute("tornei", torneoService.getTorneiDisponibili());
    	return "/admin/registra/selezionaTorneoPerRPartita";
    }
	@RequestMapping(value = "/admin/selezionaTorneoPerRPartita", method = RequestMethod.POST)
    public String apriSelezionaTorenoPerRPartita(@RequestParam("torneoSelezionato") Long idTorneo, Model model) {
		model.addAttribute("idTorneo",idTorneo);
		model.addAttribute("tennisti",torneoService.getTorneoPerId(idTorneo).getTennistiIscritti());
		model.addAttribute("partita",new Partita());
    	return "/admin/registra/registraPartita";
    }
    
    @RequestMapping(value = "/admin/registraPartita", method = RequestMethod.POST)
    public String registraNuovaPartita(@ModelAttribute("partita") Partita partita,@RequestParam("torn") Long idTorneo, @RequestParam("gioc1") Long idTennista1,
    		@RequestParam("gioc2") Long idTennista2, Model model, BindingResult bindingResult) {
    	this.partitaValidator.validate(partita, bindingResult);
    	this.partitaValidator.controllaId(idTennista1, idTennista2, bindingResult);
    	
    	//I dati sono validi?
    	if(!bindingResult.hasErrors()) {
    		
    		partita.setTorneo(torneoService.getTorneoPerId(idTorneo));
    		partita.setTennista1(tennistaService.tennistaPerId(idTennista1));
    		partita.setTennista2(tennistaService.tennistaPerId(idTennista2));
    		this.partitaService.inserisci(partita);
    		
    		return "admin/registra/registrazionePartitaCompletata";
    	}
    	
    	model.addAttribute("idTorneo",idTorneo);
    	model.addAttribute("tennisti",torneoService.getTorneoPerId(idTorneo).getTennistiIscritti());
    	model.addAttribute("partita",new Partita());			
    	return "admin/registra/registraPartita";
    }
    
    
    @RequestMapping(value = "/admin/selezionaTorneoPerCPartita", method = RequestMethod.GET)
    public String apriSelezionaTorenoPerCPartita(Model model) {
    	model.addAttribute("tornei", torneoService.getTorneiDisponibiliEFiniti());
    	return "admin/cancella/selezionaTorneoPerCPartita";
    }
	@RequestMapping(value = "/admin/selezionaTorneoPerCPartita", method = RequestMethod.POST)
    public String apriSelezionaTorenoPerCPartita(@RequestParam("torneoSelezionato") Long idTorneo, Model model) {
		model.addAttribute("partite",partitaService.getPartiteByToreno(idTorneo));
    	return "admin/cancella/cancellaPartita";
    }
    
    @RequestMapping(value = "/admin/cancellaPartita", method = RequestMethod.POST)
    public String cancellaPartita(@ModelAttribute("partitaSel") Long idPartita) {
    	this.partitaService.eliminaPartita(idPartita);
    	return "admin/cancella/cancellazionePartitaCompletata";
    }
}
