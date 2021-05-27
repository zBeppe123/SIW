package it.uniroma3.siw.tennis.spring.controller;

import java.util.List;

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
    	this.torneoValidator.validate(torneo, bindingResult);
    	this.torneoValidator.controllaCampoIdArbitro(idArbitro, bindingResult);
    	
    	//Dati del torneo sono validi?
    	if(!bindingResult.hasErrors()) {
    		
    		torneo.setArbitro(arbitroService.arbitroPerId(Long.parseLong(idArbitro)));
    		this.torneoService.inserisci(torneo);
    		return "index.html";
    	}
    	else {
    		model.addAttribute("arbitri", this.arbitroService.tutti());
    		return "registrazione/registraTorneo.html";
    	}
    }
    
    @RequestMapping(value="/tornei", method = RequestMethod.GET)
    public String addToreno(Model model) {
    	logger.debug("Lettura di tutti i tornei");
    	
    	List<Torneo> tornei = torneoService.tutti();
    	model.addAttribute("tornei", tornei);
        return "tornei.html";
    }

    
}
