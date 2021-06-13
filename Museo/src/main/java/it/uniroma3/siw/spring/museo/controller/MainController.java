package it.uniroma3.siw.spring.museo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	/**
	 * questa funzione serve per aprire l'index
	 * @param model
	 * @return stringa index.html
	 */
	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public String vaiPaginaIniziale(Model model) {
		return "index.html";
	}
	
	/**
	 * questa pagina apre la pagina informazioni.html
	 * @param model
	 * @return stringa riferita alla pagina informazioni.html
	 */
	@RequestMapping(value = "/informazioni", method = RequestMethod.GET)
	public String informazioniSulMuseo(Model model) {
		return "informazioni";
	}
}
