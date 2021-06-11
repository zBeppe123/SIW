package it.uniroma3.siw.spring.museo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.museo.controller.validator.CollezioneValidator;

import it.uniroma3.siw.spring.museo.model.Collezione;
import it.uniroma3.siw.spring.museo.service.CollezioneService;

@Controller
public class CollezioneController {
	@Autowired
	private CollezioneService collezioneService;
	@Autowired
	private CollezioneValidator collezioneValidator;
	
	@RequestMapping(value = "/collezione/{id}", method = RequestMethod.GET)
	public String getArtista(@PathVariable("id") Long idCollezione, Model model) {
		Collezione c=this.collezioneService.collezionePerId(idCollezione);
		if(c!=null) {
			model.addAttribute("collezione",c);
		}
		return "collezione";
	}
}
