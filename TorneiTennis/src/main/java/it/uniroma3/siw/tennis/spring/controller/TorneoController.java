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
import it.uniroma3.siw.tennis.spring.model.Tennista;
import it.uniroma3.siw.tennis.spring.model.Torneo;
import it.uniroma3.siw.tennis.spring.service.ArbitroService;
import it.uniroma3.siw.tennis.spring.service.PartitaService;
import it.uniroma3.siw.tennis.spring.service.TorneoService;
import it.uniroma3.siw.tennis.spring.utili.Utili;

@Controller
public class TorneoController {
	@Autowired
	private Utili utili;
	@Autowired
	private TorneoService torneoService;
	
	@Autowired
	private ArbitroService arbitroService;
	
	@Autowired
	private PartitaService partitaService;
	
    @Autowired
    private TorneoValidator torneoValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/admin/registraTorneo", method = RequestMethod.GET)
    public String apriRegistraTorneo(Model model) {
    	model.addAttribute("torneo", new Torneo());
    	model.addAttribute("arbitri", arbitroService.tutti());
    	return "admin/registraTorneo.html";
    }
    
    @RequestMapping(value = "/admin/registraTorneo", method = RequestMethod.POST)
    public String registraNuovoTorneo(@ModelAttribute("torneo") Torneo torneo, @RequestParam("arbtr") String idArbitro, Model model, BindingResult bindingResult) {
    	logger.debug("Registrazione nuovo torneo.");
    	
    	this.torneoValidator.validate(torneo, bindingResult);
    	this.torneoValidator.controllaCampoIdArbitro(idArbitro, bindingResult);
    	
    	//Dati del torneo sono validi?
    	if(!bindingResult.hasErrors()) {
    		torneo.setArbitro(arbitroService.arbitroPerId(Long.parseLong(idArbitro)));
    		torneo.setNumeroPartecipanti(0);
    		this.torneoService.inserisci(torneo);
    		
    		logger.debug("Registrazione nuovo torneo effettuata.");
    		
    		model.addAttribute("torneo", torneo);
    		return "/admin/registrazioneTorneoCompletata.html";
    	}
    	else {
    		logger.debug("Uno o piu' campi di torneo vuoti");
    		
    		model.addAttribute("arbitri", this.arbitroService.tutti());
    		return "/admin/registraTorneo.html";
    	}
    }
    
    @RequestMapping(value="/tornei", method = RequestMethod.GET)
    public String elencoTornei(Model model) {
    	logger.debug("Lettura di tutti i tornei");
    	
    	model.addAttribute("tornei", this.torneoService.tutti());
        return "tornei.html";
    }
    
    @RequestMapping(value="/torneiUtente", method = RequestMethod.GET)
    public String elencoTornei2(Model model) {
    	logger.debug("Lettura di tutti i tornei");
    	
    	model.addAttribute("tornei", this.torneoService.tutti());
        return "torneiUtente";
    }
    
    @RequestMapping(value = "/torneo/{id}", method = RequestMethod.GET)
    public String getTorneo(@PathVariable("id") Long idTorneo, Model model) {
    	logger.debug("Lettura del torneo selezionato.");
    	
    	Torneo t = this.torneoService.getTorneoPerId(idTorneo);
    	if(t != null) {
    		model.addAttribute("torneo", t);
    		model.addAttribute("partite", partitaService.getPartiteByToreno(idTorneo));
    	}
    	
    	return "infoTorneo.html";
    }
    
    @RequestMapping(value = "/iscrizioneTorneo", method = RequestMethod.GET)
    public String apriIscrizioneTorneo(Model model) {
    	Long idTennista=utili.getTennistaAttuale().getId();
    	model.addAttribute("torneiDisponibili",torneoService.getTorneiDisponibili(idTennista));
    	model.addAttribute("torneoSelez",new Torneo());
    	return "iscrizioneTorneo";
    }
    
    @RequestMapping(value = "/iscrizioneTorneo", method = RequestMethod.POST)
    public String IscrizioneTorneo(@RequestParam("torneoSelezionato") Long idTorneo,Model model) {
    	Tennista tennista=utili.getTennistaAttuale();
    	System.out.println("id torneo= " + idTorneo);
    	Torneo torneoSelez = this.torneoService.getTorneoPerId(idTorneo);
    	System.out.println("nome torneo: " + torneoSelez.getNome() + " anno toreno: " + torneoSelez.getAnno() + " numeroMaxPartecipanti: " + torneoSelez.getNumeroMaxDiPartecipanti() );
    	if(tennista!=null && torneoSelez!=null) {
    		torneoSelez.setNumeroPartecipanti(torneoSelez.getNumeroPartecipanti()+1);
    		torneoSelez.getTennistiIscritti().add(tennista);
    		torneoService.iscriviTennista(torneoSelez);
    		model.addAttribute("torneo", torneoSelez);
    		return "iscrizioneTorneoCompletata";
    	}
    	else if(torneoSelez==null) {
        	model.addAttribute("torneiDisponibili",torneoService.getTorneiDisponibili(tennista.getId()));
        	return "iscrizioneTorneo";
    	}
    	return "index";
    	
    }
    
    @RequestMapping(value = "/cancellaIscrizioneTorneo", method = RequestMethod.GET)
    public String apriCancellaIscrizioneTorneo(Model model) {
    	Long idTennista=utili.getTennistaAttuale().getId();
    	System.out.println(torneoService.getTorneiIscrittiDaTennista(idTennista).size());
    	model.addAttribute("tornei",torneoService.getTorneiIscrittiDaTennista(idTennista));
    	return "cancellaIscrizioneTorneo";
    }
    
    @RequestMapping(value = "/cancellaIscrizioneTorneo", method = RequestMethod.POST)
    public String cancellaIscrizioneTorneo(@RequestParam("torneoSelezionato") Long idTorneo,Model model) {
    	Tennista tennista=utili.getTennistaAttuale();
    	Torneo torneoSelez = this.torneoService.getTorneoPerId(idTorneo);
    	tennista.getTorneiIscritti().remove(torneoSelez);
    	torneoSelez.getTennistiIscritti().remove(tennista);
    	torneoSelez.setNumeroPartecipanti(torneoSelez.getNumeroPartecipanti()-1);
    	torneoService.inserisci(torneoSelez);
    	model.addAttribute("torneo",torneoSelez);
    	return "cancellazioneIscrizioneCompletata";
    }
    
}
