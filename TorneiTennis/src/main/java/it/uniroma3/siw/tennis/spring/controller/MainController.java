package it.uniroma3.siw.tennis.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainController {
	/** Apre la pagina index.html
	 * @param model
	 * @return Stringa riferita index.html
	 */
	@RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
	public String index(Model model) {
			return "index";
	}
}