package it.uniroma3.siw.spring.museo.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.spring.museo.controller.validator.OperaValidator;
import it.uniroma3.siw.spring.museo.model.Opera;
import it.uniroma3.siw.spring.museo.service.ArtistaService;
import it.uniroma3.siw.spring.museo.service.CollezioneService;
import it.uniroma3.siw.spring.museo.service.OperaService;

@Controller
public class OperaController {
	private static final Logger logger = LoggerFactory.getLogger(OperaController.class);
	private final String PATH_SAVE_IMAGES = "src/main/resources/static/images/opere/";
	
	@Autowired
	private OperaService operaService;
	@Autowired
	private ArtistaService artistaService;
	@Autowired
	private CollezioneService collezioneService;
	@Autowired
	private OperaValidator operaValidator;


	/**
	 * Questo metodo serve ad aprire la pagina di un opera cercandola tramite il suo
	 * id
	 * 
	 * @param idOpera
	 * @param model
	 * @return pagina dell'opera
	 */
	@RequestMapping(value = "/opera/{id}", method = RequestMethod.GET)
	public String getArtista(@PathVariable("id") Long idOpera, Model model) {
		Opera o = this.operaService.operaPerId(idOpera);
		if(o!=null) {
			model.addAttribute("opera", o);
		}
		
		logger.debug("=============================================================================== OPERA: " + o);
		return "opera";
	}

	@RequestMapping(value = "/admin/registraOpera", method = RequestMethod.GET)
	public String apriRegistraOpera(Model model) {
		model.addAttribute("opera", new Opera());
		model.addAttribute("artisti", artistaService.tutti());
		return "admin/registrazione/registraOpera";
	}

	@RequestMapping(value = "/admin/registraOpera", method = RequestMethod.POST)
	public String RegistraOpera(@ModelAttribute("opera") Opera opera, @RequestParam("autor") Long idArtista,
			@RequestParam("immagine") MultipartFile img, Model model, BindingResult bindingResult) throws IOException {
		this.operaValidator.validate(opera, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			String fileName = StringUtils.cleanPath(img.getOriginalFilename());
			String uploadDir = PATH_SAVE_IMAGES;// + opera.getId();
	        FileUploadUtil.saveFile(uploadDir, fileName, img);
			opera.setArtista(artistaService.artistaPerId(idArtista));
			this.operaService.inserisci(opera);
			model.addAttribute("opera", opera);
			return "/admin/registrazione/registraOperaCompletata";
		}
		model.addAttribute("artisti", artistaService.tutti());
		return "admin/registrazione/registraOpera";
	}
}
