package it.uniroma3.siw.tennis.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.tennis.spring.controller.validator.TennistaValidator;
import it.uniroma3.siw.tennis.spring.model.Tennista;
import it.uniroma3.siw.tennis.spring.service.PartitaService;
import it.uniroma3.siw.tennis.spring.service.TennistaService;

@Controller
public class TennistaController {
	@Autowired
	private TennistaService tennistaService;
	@Autowired
	private PartitaService partitaService;
	@Autowired
	private TennistaValidator tennistaValidator;

	@RequestMapping(value = "/tennista/{id}", method = RequestMethod.GET)
	public String getTennista(@PathVariable("id") Long idTennista, Model model) {
		Tennista t = this.tennistaService.tennistaPerId(idTennista);
		if (t != null) {
			model.addAttribute("tennista", this.tennistaService.tennistaPerId(idTennista));
			model.addAttribute("partite", this.partitaService.getPartiteByTennista(t.getId()));
		}
		return "tennista";
	}

}
