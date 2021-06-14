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

import it.uniroma3.siw.tennis.spring.controller.validator.TorneoModificatoValidator;
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
    
    @Autowired
    private TorneoModificatoValidator torneoModificatoValidator;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /** Apre la pagina per registra un torneo.
     * @param model
     * @return Stringa con riferimento a registraTorneo.html
     */
    @RequestMapping(value = "/admin/registraTorneo", method = RequestMethod.GET)
    public String apriRegistraTorneo(Model model) {
    	model.addAttribute("torneo", new Torneo());
    	model.addAttribute("arbitri", arbitroService.tutti());
    	return "admin/registra/registraTorneo";
    }
    
    /** Registra un nuovo torneo inserito dall'utente.
     * @param torneo
     * @param idArbitro
     * @param model
     * @param bindingResult
     * @return Stringa con riferimento a registraTorneo.html
     */
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
    		return "/admin/registra/registrazioneTorneoCompletata";
    	}
    	
    	logger.debug("Uno o piu' campi di torneo vuoti");
    	model.addAttribute("arbitri", this.arbitroService.tutti());
    	return "/admin/registra/registraTorneo";
    }
    
    /** Apre la pagina dove mostra l'elenco di tutti tornei per la modifica dati.
     * @param model
     * @return Stringa riferita a sceltaTorneoPerModifica.html
     */
    @RequestMapping(value = "/admin/sceltaTorneoPerModifica", method = RequestMethod.GET)
	public String apriSceltaTorneoPerModifica(Model model) {
		model.addAttribute("tornei", this.torneoService.tutti());
		return "admin/modifica/sceltaTorneoPerModifica";
	}
	
    /** L'admin sceglie il torneo per la modifica e viene aperto la pagina per la modifica dati del torneo.
     * @param idTorneo
     * @param model
     * @return Stringa riferita a modificaTorneo.html
     */
	@RequestMapping(value = "/admin/sceltaTorneoPerModifica", method = RequestMethod.POST)
	public String sceltoArbitroPerModifica(@RequestParam("torneoSelezionato") Long idTorneo, Model model) {
		Torneo torneo = this.torneoService.getTorneoPerId(idTorneo);
		
		model.addAttribute("torneo", torneo);
		model.addAttribute("arbitri", this.arbitroService.tutti());
		return "/admin/modifica/modificaTorneo";
	}
	
	/** Modifica i dati del torneo selezionato.
	 * @param idArbitro
	 * @param torneoModificato
	 * @param model
	 * @param bindingResult
	 * @return Stringa riferita a modificaTorneoCompletata.html se la modifica e' avvenuta con successo,
	 * 			altrimenti a modificaTorneo.html
	 */
	@RequestMapping(value = "/admin/modificaTorneo", method = RequestMethod.POST)
	public String modificaDatiTorneo(@RequestParam("arbtr") Long idArbitro, @ModelAttribute("torneo") Torneo torneoModificato, 
									  Model model, BindingResult bindingResult) {
		this.torneoModificatoValidator.validate(torneoModificato, bindingResult);
		
		torneoModificato.setArbitro(this.arbitroService.arbitroPerId(idArbitro));
		
		if(!bindingResult.hasErrors()) {
			torneoModificato.setNumeroPartecipanti(this.torneoService.getTorneoPerId(torneoModificato.getId()).getNumeroPartecipanti());
			
			this.torneoService.modificaDatiDiTorneo(torneoModificato);
			
			return "/admin/modifica/modificaTorneoCompletata";
		}
		
		model.addAttribute("arbitri", this.arbitroService.tutti());
		return "/admin/modifica/modificaTorneo";
	}
    
	/** Apre la pagina dove mostra l'elenco di tutti i tornei.
	 * @param model
	 * @return Stringa riferita a tornei.html
	 */
    @RequestMapping(value="/tornei", method = RequestMethod.GET)
    public String elencoTornei(Model model) {
    	logger.debug("Lettura di tutti i tornei");
    	
    	model.addAttribute("tornei", this.torneoService.tutti());
        return "tornei";
    }
    
    /** Apre la pagina dove mostra l'elenco di tutti i tornei.
	 * @param model
	 * @return Stringa riferita a torneiUtente.html
	 */
    @RequestMapping(value="/torneiUtente", method = RequestMethod.GET)
    public String elencoTornei2(Model model) {
    	logger.debug("Lettura di tutti i tornei");
    	
    	model.addAttribute("tornei", this.torneoService.tutti());
        return "/utente/torneiUtente";
    }
    
    /** Apre la pagina dove mostra tutti i dati di un torneo selezionato.
     * @param idTorneo
     * @param model
     * @return Stringa con riferimento infoTorneo.html
     */
    @RequestMapping(value = "/torneo/{id}", method = RequestMethod.GET)
    public String getTorneo(@PathVariable("id") Long idTorneo, Model model) {
    	logger.debug("Lettura del torneo selezionato.");
    	
    	Torneo t = this.torneoService.getTorneoPerId(idTorneo);
    	if(t != null) {
    		model.addAttribute("torneo", t);
    		model.addAttribute("partite", partitaService.getPartiteByTorneo(idTorneo));
    	}
    	
    	return "infoTorneo";
    }
    
    /** Apre la pagina dove mostra l'elenco di tornei a cui un tennista puo' iscriversi.
     * @param model
     * @return Stringa riferita a iscrizioneTorneo.html
     */
    @RequestMapping(value = "/iscrizioneTorneo", method = RequestMethod.GET)
    public String apriIscrizioneTorneo(Model model) {
    	Long idTennista=utili.getTennista().getId();
    	model.addAttribute("torneiDisponibili",torneoService.getTorneiDisponibili(idTennista));
    	model.addAttribute("torneoSelez",new Torneo());
    	return "/utente/gestioneIscrizione/iscrizioneTorneo";
    }
    
    /** Il tennista si iscrive ad un torneo che ha selezionato.
     * @param idTorneo
     * @param model
     * @return Striga riferita a iscrizioneTorneoCompletata.html se l'iscrizione e' andata a buon fine,
     * 			altrimenti a iscrizioneTorneo.html
     */
    @RequestMapping(value = "/iscrizioneTorneo", method = RequestMethod.POST)
    public String IscrizioneTorneo(@RequestParam("torneoSelezionato") Long idTorneo,Model model) {
    	Tennista tennista=utili.getTennista();
    	Torneo torneoSelez = this.torneoService.getTorneoPerId(idTorneo);
    	if(tennista!=null && torneoSelez!=null) {
    		torneoSelez.setNumeroPartecipanti(torneoSelez.getNumeroPartecipanti()+1);
    		torneoSelez.getTennistiIscritti().add(tennista);
    		torneoService.iscriviTennista(torneoSelez);
    		model.addAttribute("torneo", torneoSelez);
    		return "/utente/gestioneIscrizione/iscrizioneTorneoCompletata";
    	}
    	else if(torneoSelez==null) {
        	model.addAttribute("torneiDisponibili",torneoService.getTorneiDisponibili(tennista.getId()));
        	return "/utente/gestioneIscrizione/iscrizioneTorneo";
    	}
    	return "/utente/home";
    	
    }
    
    /** Apre la pagina dove mostra tutti i tornei iscritt del tennista.
     * @param model
     * @return Stringa riferita a cancellaIscrizioneTorneo
     */
    @RequestMapping(value = "/cancellaIscrizioneTorneo", method = RequestMethod.GET)
    public String apriCancellaIscrizioneTorneo(Model model) {
    	Long idTennista=utili.getTennista().getId();
    	model.addAttribute("tornei",torneoService.getTorneiIscrittiDaTennista(idTennista));
    	return "/utente/gestioneIscrizione/cancellaIscrizioneTorneo";
    }
    
    /** Cancella l'iscrizione a cui un tennista e' iscritto ad un torneo.
     * @param idTorneo
     * @param model
     * @return Stringa riferito a cancellazioneIscrizioneCompletata.html
     */
    @RequestMapping(value = "/cancellaIscrizioneTorneo", method = RequestMethod.POST)
    public String cancellaIscrizioneTorneo(@RequestParam("torneoSelezionato") Long idTorneo,Model model) {
    	Tennista tennista=utili.getTennista();
    	Torneo torneoSelez = this.torneoService.getTorneoPerId(idTorneo);
    	tennista.getTorneiIscritti().remove(torneoSelez);
    	torneoSelez.getTennistiIscritti().remove(tennista);
    	torneoSelez.setNumeroPartecipanti(torneoSelez.getNumeroPartecipanti()-1);
    	torneoService.inserisci(torneoSelez);
    	model.addAttribute("torneo",torneoSelez);
    	return "/utente/gestioneIscrizione/cancellazioneIscrizioneCompletata";
    }
    
    /** Apre la pagina dove mostra tutti i torneo cancellabili.
     * @param model
     * @return Stringa con riferimento a cancellaTorneo.html
     */
    @RequestMapping(value="/admin/cancellaTorneo", method=RequestMethod.GET)
    public String apriCancellaToreno(Model model) {
    	model.addAttribute("tornei",torneoService.getTorneiCancellabili());
    	return "/admin/cancella/cancellaTorneo";
    }
    
    /** Cancella un torneo selezionato dall'admin.
     * @param idTorneo
     * @return Stringa riferita a cancellazioneTorneo.html
     */
    @RequestMapping(value="/admin/cancellaTorneo", method=RequestMethod.POST)
    public String CancellaToreno(@RequestParam("torenoSelezionato") Long idTorneo) {
    	this.torneoService.eliminaTorneo(idTorneo);
    	return "/admin/cancella/cancellazioneTorneoCompletata";
    }
}
