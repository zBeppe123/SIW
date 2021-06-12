package it.uniroma3.siw.spring.museo.controller;

import java.util.List;

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
	
	@RequestMapping(value = "/admin/registraCollezione", method =RequestMethod.GET)
	public String apriRegistraCollezione(Model model) {
		model.addAttribute("curatori", curatoreService.tutti());
		model.addAttribute("collezione", new Collezione());
		return "admin/registrazione/registraCollezione";
	}
	
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
	
	@RequestMapping(value = "/admin/sceltaCollezionePerInserireOpere", method = RequestMethod.GET)
	public String apriSceltaCollezionePerInserimentoOpere(Model model) {
		model.addAttribute("collezioni", this.collezioneService.tutti());
		return "admin/inserimento/sceltaCollezionePerInserimentoOpere.html";
	}
	
	@RequestMapping(value = "/admin/sceltaCollezionePerInserireOpere", method = RequestMethod.POST)
	public String sceltoLaCollezionePerInserireLeOpere(@RequestParam("collezioneSelezionato") Long idCollezione, Model model) {
		model.addAttribute("idCollezione", idCollezione);
		model.addAttribute("opereInseribili", this.operaService.getOpereNonInseriteAdUnaCollezione());
		model.addAttribute("opereNonSelezionate", "false");
		return "admin/inserimento/inserisciOpereACollezione.html";
	}
	
	@RequestMapping(value = "/admin/inserisciOpereACollezione", method = RequestMethod.POST)
	public String inserisciOpereAllaCollezione(@RequestParam("id_collezione") Long idCollezione, 
											   @RequestParam(name="opereSelezionate", required=false) List<Long> idOpere,
											   Model model) {
		if(idOpere != null) {
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
	
	@RequestMapping(value = "/collezione/{id}", method = RequestMethod.GET)
	public String getCollezione(@PathVariable("id") Long idCollezione, Model model) {
		Collezione c=this.collezioneService.collezionePerId(idCollezione);
		
		model.addAttribute("collezione", c);
		if(c!=null) {
			model.addAttribute("opere",c.getOpereEsposte());
		}
		return "collezione";
	}
	
	@RequestMapping(value = "/collezioni", method = RequestMethod.GET)
	public String apriCollezioni(Model model){
		model.addAttribute("collezioni", collezioneService.getCollezioniOrdinatePerNome());
		return "collezioni";
	}
}
