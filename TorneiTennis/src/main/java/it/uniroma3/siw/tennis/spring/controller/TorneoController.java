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
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.tennis.spring.controller.validator.TorneoValidator;
import it.uniroma3.siw.tennis.spring.model.Torneo;
import it.uniroma3.siw.tennis.spring.service.ArbitroService;
import it.uniroma3.siw.tennis.spring.service.TorneoService;

@Controller
public class TorneoController {
	
	@Autowired
	private TorneoService torneoService;
	
	@Autowired
	private ArbitroService arbitroService;
	
    @Autowired
    private TorneoValidator torneoValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/registraTorneo", method = RequestMethod.GET)
    public String apriRegistraTorneo(Model model) {
    	model.addAttribute("torneo", new Torneo());
    	model.addAttribute("arbitri", arbitroService.tutti());
    	return "registrazione/registraTorneo.html";
    }
    
    @RequestMapping(value = "/registraTorneo", method = RequestMethod.POST)
    public String registraNuovoTorneo(@ModelAttribute("torneo") Torneo torneo, @RequestParam("arbtr") String idArbitro, Model model, BindingResult bindingResult) {
    	logger.debug("Registrazione nuovo torneo.");
    	
    	this.torneoValidator.validate(torneo, bindingResult);
    	this.torneoValidator.controllaCampoIdArbitro(idArbitro, bindingResult);
    	
    	//Dati del torneo sono validi?
    	if(!bindingResult.hasErrors()) {
    		torneo.setArbitro(arbitroService.arbitroPerId(Long.parseLong(idArbitro)));
    		this.torneoService.inserisci(torneo);
    		
    		logger.debug("Registrazione nuovo torneo effettuata.");
    		
    		return "index.html";
    	}
    	else {
    		logger.debug("Uno o piu' campi di torneo vuoti");
    		
    		model.addAttribute("arbitri", this.arbitroService.tutti());
    		return "registrazione/registraTorneo.html";
    	}
    }
    
    @RequestMapping(value="/tornei", method = RequestMethod.GET)
    public String elencoTornei(Model model) {
    	logger.debug("Lettura di tutti i tornei");
    	
    	model.addAttribute("tornei", this.torneoService.tutti());
        return "tornei.html";
    }
    
    @RequestMapping(value = "/torneo/{id}", method = RequestMethod.GET)
    public String getTorneo(@PathVariable("id") Long idTorneo, Model model) {
    	logger.debug("Lettura del torneo selezionato.");
    	
    	Torneo t = this.torneoService.getTorneoPerId(idTorneo);
    	if(t != null)
    		model.addAttribute("torneo", t);
    	
    	return "infoTorneo.html";
    }
}
