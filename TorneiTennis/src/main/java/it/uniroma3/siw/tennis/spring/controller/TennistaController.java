package it.uniroma3.siw.tennis.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.tennis.spring.controller.validator.TennistaValidator;
import it.uniroma3.siw.tennis.spring.service.TennistaService;

@Component
public class TennistaController {
	@Autowired
	private TennistaService tennistaService;
	@Autowired
	private TennistaValidator tennistaValidator;
	
	@RequestMapping(value = "/arbitro/{id}", method = RequestMethod.GET)
	public String getArbitro(@PathVariable("id") Long idTennista, Model model) {
		
		model.addAttribute("tennista", this.tennistaService.tennistaPerId(idTennista));
		return "tennista.html";
	}
	
}
