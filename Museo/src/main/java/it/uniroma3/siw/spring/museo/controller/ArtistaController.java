package it.uniroma3.siw.spring.museo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.museo.controller.validator.ArtistaValidator;
import it.uniroma3.siw.spring.museo.model.Artista;
import it.uniroma3.siw.spring.museo.service.ArtistaServiece;

@Controller
public class ArtistaController {
	private ArtistaServiece artistaService;
	
	private ArtistaValidator artistaValidator;
	
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
		return "artista";
	}
}
