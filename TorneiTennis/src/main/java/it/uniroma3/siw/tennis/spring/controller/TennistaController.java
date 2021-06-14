package it.uniroma3.siw.tennis.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.tennis.spring.model.Credentials;
import it.uniroma3.siw.tennis.spring.model.Tennista;
import it.uniroma3.siw.tennis.spring.service.PartitaService;
import it.uniroma3.siw.tennis.spring.service.TennistaService;
import it.uniroma3.siw.tennis.spring.utili.Utili;

@Controller
public class TennistaController {
	@Autowired
	private TennistaService tennistaService;
	@Autowired
	private PartitaService partitaService;
	
	@Autowired
	Utili utili;
	
	/** Apre la pagina dove mostra i dati di un tennista.
	 * @param idTennista
	 * @param model
	 * @return Stringa riferita a tennista.html
	 */
	@RequestMapping(value = "/tennista/{id}", method = RequestMethod.GET)
	public String getTennista(@PathVariable("id") Long idTennista, Model model) {
		Tennista t = this.tennistaService.tennistaPerId(idTennista);
		if (t != null) {
			model.addAttribute("tennista", this.tennistaService.tennistaPerId(idTennista));
			model.addAttribute("partite", this.partitaService.getPartiteByTennista(t.getId()));
		}
		
		try {
			String s=utili.getCredentials().getRole();
			
			if(s.equals(Credentials.ADMIN_ROLE))
				model.addAttribute("utente", "admin");
			else
				model.addAttribute("utente", "tennista");
		}
		catch(ClassCastException e) {
			model.addAttribute("utente", "anonimo");
		}
		
		return "tennista";
	}

}
