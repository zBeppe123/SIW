package it.uniroma3.siw.tennis.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.tennis.spring.model.Torneo;
import it.uniroma3.siw.tennis.spring.service.TorneoService;

@Controller
public class TorneoController {
	
	@Autowired
	private TorneoService torneoService;
	
    @Autowired
    private TorneoValidator torneoValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    

    @RequestMapping(value="/goTornei", method = RequestMethod.GET)
    public String addToreno(Model model) {
    	logger.debug("goTornei");
    	model.addAttribute("torneo", new Torneo());
        return "tornei.html";
    }

}
