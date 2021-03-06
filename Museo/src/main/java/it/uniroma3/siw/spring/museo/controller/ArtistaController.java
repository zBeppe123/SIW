package it.uniroma3.siw.spring.museo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.museo.controller.validator.ArtistaValidator;
import it.uniroma3.siw.spring.museo.model.Artista;
import it.uniroma3.siw.spring.museo.service.ArtistaService;
import it.uniroma3.siw.spring.museo.utili.Utili;

@Controller
public class ArtistaController {
	@Autowired
	private ArtistaService artistaService;
	
	@Autowired
	private ArtistaValidator artistaValidator;
	/**
	 * Questa funzione apre la pagina html registra artista
	 * @param model
	 * @param artista
	 * @param bindingResult
	 * @return stringa con riferimento alla pagina
	 */
	@RequestMapping(value = "/admin/registraArtista", method = RequestMethod.GET)
	public String apriRegistraArtista(Model model) {
		model.addAttribute("artista", new Artista());
		return "admin/registrazione/registraArtista";
	}
	/**
	 * Questa funzione registra un nuovo artista nel database
	 * @param model
	 * @param artista
	 * @param bindingResult
	 * @return strigna riferita a registraArtistaCompletata.html se la registrazione dell'artista e' andata a buon fine,
	 * 			altrimenti a registraArtista se ci degli errori.
	 */
	@RequestMapping(value = "/admin/registraArtista", method = RequestMethod.POST)
	public String registraNuovoArtista(Model model, @ModelAttribute("artista") Artista artista, BindingResult bindingResult) {
		this.artistaValidator.validate(artista, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			this.artistaService.saveArtista(artista);
			
			model.addAttribute("artista", artista);
			return "admin/registrazione/registraArtistaCompletata.html";
		}
		
		return "admin/registrazione/registraArtista.html";
	}
	
	/**
	 * Questo metodo serve ad aprire la pagina di un artista cercandolo tramite il suo id
	 * @param idArtista 
	 * @param model
	 * @return pagina dell'artista
	 */
	@RequestMapping(value = "/artista/{id}", method = RequestMethod.GET)
	public String getArtista(@PathVariable("id") Long idArtista, Model model) {
		Artista a=this.artistaService.artistaPerId(idArtista);
		if(a!=null) {
			model.addAttribute("artista",a);
		}
		model.addAttribute("utente", Utili.getTipologiaUtente());
		
		return "artista";
	}
	/**
	 * Questa pagina serve ad aprire la pagina con tutti gli artisti ordinati per cognome
	 * @param model
	 * @param artista
	 * @param bindingResult
	 * @return stringa per pagina artisti.html
	 */

	@RequestMapping(value = "/artisti", method = RequestMethod.GET)
	public String apriArtisti(Model model){
		model.addAttribute("artisti",artistaService.getArtistiOrdinatiPerCognome());
		model.addAttribute("utente", Utili.getTipologiaUtente());
		return "artisti";
	}
}
