package it.uniroma3.siw.spring.museo.controller;

import java.util.List;

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

import it.uniroma3.siw.spring.museo.controller.validator.CollezioneValidator;
import it.uniroma3.siw.spring.museo.model.Collezione;
import it.uniroma3.siw.spring.museo.model.Curatore;
import it.uniroma3.siw.spring.museo.model.Opera;
import it.uniroma3.siw.spring.museo.service.CollezioneService;
import it.uniroma3.siw.spring.museo.service.CuratoreService;
import it.uniroma3.siw.spring.museo.service.OperaService;
import it.uniroma3.siw.spring.museo.utili.Utili;

@Controller
public class CollezioneController {
	@Autowired
	private CollezioneService collezioneService;
	@Autowired
	private CollezioneValidator collezioneValidator;
	@Autowired
	private CuratoreService curatoreService;
	@Autowired
	private OperaService operaService;

	private static final Logger logger = LoggerFactory.getLogger(CollezioneController.class);
	//registra Collezione
	/**
	 * Questa funzione apre la pagina RegistraCollezione.html
	 * @param model
	 * @return la string ariferita alla pagina registra collezione.html
	 */
	@RequestMapping(value = "/admin/registraCollezione", method =RequestMethod.GET)
	public String apriRegistraCollezione(Model model) {
		model.addAttribute("curatori", curatoreService.tutti());
		model.addAttribute("collezione", new Collezione());
		return "admin/registrazione/registraCollezione";
	}
	
     /**
	 * Questa funzione registra la Collezione inserita dall'utente  nel database
	 * @param model
	 * @param collezione
	 * @param idCuratore
	 * @param bindingResult
	 * @return la stringa riferita alla pagina registraCollezioneCompletata se la registrazione ?? andata a buon fine, altrimenti la  stringa registraCollezione.
	 */
	@RequestMapping(value = "/admin/registraCollezione", method = RequestMethod.POST)
	public String registraNuovaCollezione(Model model, @ModelAttribute("collezione") Collezione collezione,@RequestParam("curatr") Long idCuratore, BindingResult bindingResult) {
		this.collezioneValidator.validate(collezione, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			collezione.setCuratore(curatoreService.curatorePerId(idCuratore));
			this.collezioneService.saveCollezione(collezione);
			
			model.addAttribute("collezione", collezione);
			return "admin/registrazione/registraCollezioneCompletata";
		}
		
		return "admin/registrazione/registraCollezione";
	}
	//inserimento opere
	/**
	 * Questa funzione apre la pagina sceltaCollezionePerInserimentoOpere che serve per selezionare una collezione alla quale si vuole inserire delle opere.
	 * @param model
	 * @return stringa riferita alla pagina sceltaCollezionePerInserimentoOpere
	 */
	@RequestMapping(value = "/admin/sceltaCollezionePerInserireOpere", method = RequestMethod.GET)
	public String apriSceltaCollezionePerInserimentoOpere(Model model) {
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "admin/inserimento/sceltaCollezionePerInserimentoOpere.html";
	}
	
	/**
	 * Questa funzione apre la pagina inserisciOpereACollezione che serve per selezionare le opere da inserire 
	 * in una collezione scelta nella pagina precedente (sceltaCollezionePerInserimentoOpere.html).
	 * @param model
	 * @param idCollezione
	 * @return stringa riferita alla pagina inserisciOpereACollezione
	 */
	@RequestMapping(value = "/admin/sceltaCollezionePerInserireOpere", method = RequestMethod.POST)
	public String sceltaLaCollezionePerInserireLeOpere(@RequestParam("collezioneSelezionato") Long idCollezione, Model model) {
		model.addAttribute("idCollezione", idCollezione);
		model.addAttribute("opereInseribili", this.operaService.getOpereNonInseriteAdUnaCollezione());
		model.addAttribute("opereNonSelezionate", "false");
		return "admin/inserimento/inserisciOpereACollezione.html";
	}
	
	/**
	 * Questa funzione inserisce le opere nella collezione
	 * @param idCollezione
	 * @param idOpere
	 * @param model
	 * @return stringa riferita alla pagina inserisciOpereACollezioneCompletata.html se sono state scelte opere, altrimenti inserisciOpereACollezione.html
	 */
	@RequestMapping(value = "/admin/inserisciOpereACollezione", method = RequestMethod.POST)
	public String inserisciOpereAllaCollezione(@RequestParam("id_collezione") Long idCollezione, 
											   @RequestParam(name="opereSelezionate", required=false) List<Long> idOpere,
											   Model model) {
		//E' stato seleionato nella pagina almeno un'opera?
		if(idOpere != null) {
			//Inserisco ogni opera alla collezione.
			Collezione collezione = this.collezioneService.collezionePerId(idCollezione);
			for(Long idOpera : idOpere) {
				Opera o = this.operaService.operaPerId(idOpera);
				o.setCollezione(collezione);
				this.operaService.inserisci(o);
			}
			
			model.addAttribute("collezione", collezione);
			return "admin/inserimento/inserisciOpereACollezioneCompletata.html";
		}
		
		model.addAttribute("idCollezione", idCollezione);
		model.addAttribute("opereInseribili", this.operaService.getOpereNonInseriteAdUnaCollezione());
		model.addAttribute("opereNonSelezionate", "true");
		return "admin/inserimento/inserisciOpereACollezione.html";
	}
	//rimozione opere
	/**
	 * questa funzione apre la pagina SeceltaCollezionePerRimozioneOpere che serve per selezionare la collezione alla quale bisogna rimuovere delle opere all'interno
	 * @param model
	 * @return stringa riferita alla pagina sceltaCollezionePerRimozioneOpere
	 */
	@RequestMapping(value = "/admin/sceltaCollezionePerRimuovereOpere", method = RequestMethod.GET)
	public String apriSceltaCollezionePerRimozioneOpere(Model model) {
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "admin/inserimento/sceltaCollezionePerRimozioneOpere.html";
	}
	
	/**
	 * Questa funzione apre la pagina rimuoviOpereACollezione che serve pre la selzione delle opere
	 * @param idCollezione
	 * @param model
	 * @return stringa riferita alla pagina rimuoviOpereACollezione.html
	 */
	@RequestMapping(value = "/admin/sceltaCollezionePerRimuovereOpere", method = RequestMethod.POST)
	public String sceltaLaCollezionePerRimuovereLeOpere(@RequestParam("collezioneSelezionato") Long idCollezione, Model model) {
		model.addAttribute("idCollezione", idCollezione);
		model.addAttribute("opere", this.collezioneService.getOpereDellaCollezione(idCollezione));
		model.addAttribute("opereNonSelezionate", "false");
		return "admin/inserimento/rimuoviOpereACollezione.html";
	}
	
	/**
	 * Questa funzione inserisce le opere nella collezione
	 * @param idCollezione
	 * @param idOpere
	 * @param model
	 * @return stringa riferita alla pagina inserisciOpereACollezioneCompletata.html se sono state scelte opere altrimenti inserisciOpereACollezione.html
	 */
	@RequestMapping(value = "/admin/rimuoviOpereACollezione", method = RequestMethod.POST)
	public String rimuoviOpereAllaCollezione(@RequestParam("id_collezione") Long idCollezione, 
											   @RequestParam(name="opereSelezionate", required=false) List<Long> idOpere,
											   Model model) {
		if(idOpere != null) {
			Collezione collezione = this.collezioneService.collezionePerId(idCollezione);
			for(Long idOpera : idOpere) {
				Opera o = this.operaService.operaPerId(idOpera);
				o.setCollezione(null);
				this.operaService.inserisci(o);
			}
			
			model.addAttribute("collezione", collezione);
			return "admin/inserimento/rimuoviOpereACollezioneCompletata.html";
		}
		
		model.addAttribute("idCollezione", idCollezione);
		model.addAttribute("opere", this.operaService.getOpereNonInseriteAdUnaCollezione());
		model.addAttribute("opereNonSelezionate", "true");
		return "admin/inserimento/rimuoviOpereACollezione.html";
	}
	//controller pagine controller
	/**
	 * Questa funzione apre la pagina collezione/id ovvero apre la pagina della collezione selezionata nella pagina precedente.
	 * @param idCollezione
	 * @param model
	 * @return stringa riferita alla pagina collezione
	 */
	@RequestMapping(value = "/collezione/{id}", method = RequestMethod.GET)
	public String getCollezione(@PathVariable("id") Long idCollezione, Model model) {
		Collezione c=this.collezioneService.collezionePerId(idCollezione);
		
		if(c!=null) {
			model.addAttribute("collezione", c);
			model.addAttribute("opere", this.collezioneService.getOpereDellaCollezione(idCollezione));
		}
		model.addAttribute("utente",Utili.getTipologiaUtente());
		return "collezione";
	}
	
	/**
	 * Questa funzione apre la pagina collezioni.html
	 * @param model
	 * @return stringa riferita alla pagina collezioni.html
	 */
	@RequestMapping(value = "/collezioni", method = RequestMethod.GET)
	public String apriCollezioni(Model model){
		model.addAttribute("collezioni", collezioneService.getCollezioniOrdinatePerNome());
		model.addAttribute("utente",Utili.getTipologiaUtente());
		return "collezioni";
	}
	//cancella collezione
	/**
	 * Questa funzione apre la pagina cancellaCollezione.html
	 * @param model
	 * @return stringa riferita alla pagina cancellaCollezione
	 */
	@RequestMapping(value = "/admin/cancellaCollezione", method=RequestMethod.GET)
	public String apriCancellaCollezione(Model model) {
		model.addAttribute("collezioni", collezioneService.tutti());
		return "admin/cancella/cancellaCollezione";
	}
	
	/**
	 * Questa funzione cancella la collezione selezionata 
	 * @param idCollezione
	 * @param model
	 * @return stringa riferita alla pagina cancellaCollezioneCompletata.html
	 */
	@RequestMapping(value = "/admin/cancellaCollezione", method=RequestMethod.POST)
	public String CancellaCollezione(@RequestParam("collezioneSelezionata") Long idCollezione, Model model) {
		List<Opera> opere=collezioneService.getOpereDellaCollezione(idCollezione);
		for(Opera o:opere) {
			o.setCollezione(null);
		}
		this.operaService.inserisciOpere(opere);
 		this.collezioneService.eliminaCollezioneById(idCollezione);
		return "admin/cancella/cancellaCollezioneCompletata";
	}
	//modifica collezione

	/**
	 * apre la pagina sceltaCollezionePerModifica
	 * @param model
	 * @return stringa riferita alla pagina sceltaCollezionePerModifica
	 */
	@RequestMapping(value = "/admin/sceltaCollezionePerModifica", method = RequestMethod.GET)
	public String apriSceltaCollezionePerModifica(Model model) {
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "admin/modifica/sceltaCollezionePerModifica";
	}
	
	/**
	 * apre la pagina modifcaCollezione
	 * @param idCollezione
	 * @param model
	 * @return stringa riferita alla pagina modifcaCollezione
	 */
	@RequestMapping(value = "/admin/sceltaCollezionePerModifica", method = RequestMethod.POST)
	public String sceltaCollezionePerModifica(@RequestParam("collezioneSelezionata") Long idCollezione, Model model) {
		Collezione collezione = this.collezioneService.collezionePerId(idCollezione);
		model.addAttribute("collezione", collezione);
		model.addAttribute("curatori", curatoreService.tutti());
		return "/admin/modifica/modificaCollezione";
	}
	
	/**
	 * questa funzione modifca una collezione
	 * @param collezioneModificata
	 * @param model
	 * @param bindingResult
	 * @return stringa riferita alla pagina modificaCollezioneCompletata se tutto ?? andato a buon fine, modificaCollezione altrimenti
	 */
	@RequestMapping(value = "/admin/modificaCollezione", method = RequestMethod.POST)
	public String modificaDatiCollezione(@ModelAttribute("collezione") Collezione collezioneModificata,@RequestParam("curator") Long idCuratore, Model model, BindingResult bindingResult) {
		this.collezioneValidator.validateModifica(collezioneModificata, bindingResult);
		collezioneModificata.setCuratore(curatoreService.curatorePerId(idCuratore));
		if(!bindingResult.hasErrors()) {

			collezioneService.saveCollezione(collezioneModificata);
			return "/admin/modifica/modificaCollezioneCompletata";
		}
		model.addAttribute("curatori", curatoreService.tutti());
		return "/admin/modifica/modificaCollezione";
	}
}
