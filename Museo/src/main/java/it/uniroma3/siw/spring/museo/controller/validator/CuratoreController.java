package it.uniroma3.siw.spring.museo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.museo.controller.validator.CuratoreValidator;
import it.uniroma3.siw.spring.museo.model.Curatore;
import it.uniroma3.siw.spring.museo.service.CuratoreService;

@Controller
public class CuratoreController {
	@Autowired
	private CuratoreService curatoreService;
	
	@Autowired
	private CuratoreValidator curatoreValidator;
	
	@RequestMapping(value = "/admin/registraCuratore", method = RequestMethod.GET)
	public String apriRegistraCuratore(Model model) {
		model.addAttribute("curatore", new Curatore());
		return "admin/registrazione/registraCuratore";
	}
	
	@RequestMapping(value = "/admin/registraCuratore", method = RequestMethod.POST)
	public String registraNuovoCuratore(Model model, @ModelAttribute("curatore") Curatore curatore, BindingResult bindingResult) {
		this.curatoreValidator.validate(curatore, bindingResult);
		
		if(!bindingResult.hasErrors()) {
			this.curatoreService.saveCuratore(curatore);
			
			model.addAttribute("curatore", curatore);
			return "admin/registrazione/registraCuratoreCompletata";
		}
		
		return "admin/registrazione/registraCuratore";
	}
}
