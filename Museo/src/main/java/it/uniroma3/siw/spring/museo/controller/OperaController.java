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
	public String getOpera(@PathVariable("id") Long idOpera, Model model) {
		Opera o = this.operaService.operaPerId(idOpera);
		if(o!=null) {
			model.addAttribute("opera", o);
		}
		
		//logger.debug("=============================================================================== OPERA: " + o);
		return "opera";
	}

	/** Apre la pagina registraOpera.html
	 * @param model
	 * @return stringa riferita a registraOpera.html
	 */
	@RequestMapping(value = "/admin/registraOpera", method = RequestMethod.GET)
	public String apriRegistraOpera(Model model) {
		model.addAttribute("opera", new Opera());
		model.addAttribute("artisti", artistaService.tutti());
		return "admin/registrazione/registraOpera";
	}

	/** Registra una nuova opera inserita dall'utente
	 * @param opera
	 * @param idArtista
	 * @param img
	 * @param model
	 * @param bindingResult
	 * @return stringa riferita a registraOperaComplentata.html se la registrazione dell'opera e' andata a buon fine,
	 * 		   atrimenti registraOpera.html per degli errori.
	 * @throws IOException Fallimento di salvataggio dell'immagine dell'opera nel server.
	 */
	@RequestMapping(value = "/admin/registraOpera", method = RequestMethod.POST)
	public String RegistraOpera(@ModelAttribute("opera") Opera opera, @RequestParam("autor") Long idArtista,
			@RequestParam("immagine") MultipartFile img, Model model, BindingResult bindingResult) throws IOException {
		this.operaValidator.validate(opera, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			String fileName = StringUtils.cleanPath(img.getOriginalFilename());
			String uploadDir = PATH_SAVE_IMAGES;
	        FileUploadUtil.saveFile(uploadDir, fileName, img);
	        
			opera.setArtista(artistaService.artistaPerId(idArtista));
			opera.setImg(fileName);
			this.operaService.inserisci(opera);
			
			model.addAttribute("opera", opera);
			return "/admin/registrazione/registraOperaCompletata";
		}
		model.addAttribute("artisti", artistaService.tutti());
		return "admin/registrazione/registraOpera";
	}
	
	/** Apre la pagina cancellaOpera.html per seleziona un'opera da cancellare
	 * @param model
	 * @return stringa riferita a cancellaOpera.html
	 */
	@RequestMapping(value = "/admin/cancellaOpera", method=RequestMethod.GET)
	public String apriCancellaOpera(Model model) {
		model.addAttribute("opere", operaService.tutti());
		return "admin/cancella/cancellaOpera";
	}
	
	/** Cancella l'opera selezionata dall'utente.
	 * @param idOpera
	 * @param model
	 * @return stringa riferita a cencellaOperaCompletata per visualizzare che l'opera e' cancellata correttamente.
	 */
	@RequestMapping(value = "/admin/cancellaOpera", method=RequestMethod.POST)
	public String CancellaOpera(@RequestParam("operaSelezionata") Long idOpera, Model model) {
		this.operaService.eliminaOperaById(idOpera);
		return "admin/cancella/cancellaOperaCompletata";
	}
	
	/** Apre la pagina per la scelta dell'opera da modificare.
	 * @param model
	 * @return Stringa riferita a sceltaOperaPerModifica.html
	 */
	@RequestMapping(value = "/admin/sceltaOperaPerModifica", method = RequestMethod.GET)
	public String sceltaOperaPerModifica(Model model) {
		model.addAttribute("opere", this.operaService.tutti());
		return "admin/modifica/sceltaOperaPerModifica";
	}
	
	/** Viene scelto l'opera da modificare dall'admin e apre la pagina per la modifica dell'opera.
	 * @param idOpera
	 * @param model
	 * @return Stringa riferita a modificaOpera.html
	 */
	@RequestMapping(value = "/admin/sceltaOperaPerModifica", method = RequestMethod.POST)
	public String scegliOperaDaModificare(@RequestParam("operaSelezionata") Long idOpera, Model model) {
		model.addAttribute("opera", this.operaService.operaPerId(idOpera));
		return "admin/modifica/modificaOpera";
	}
	
	public String modificaOpera(@RequestParam(name = "immagine", required = false) MultipartFile img,
								@RequestParam("opera") Opera opera,Model model) {
this.operaValidator.validate(opera, bindingResult);
		
		if (!bindingResult.hasErrors()) {
			String fileName = StringUtils.cleanPath(img.getOriginalFilename());
			String uploadDir = PATH_SAVE_IMAGES;
	        FileUploadUtil.saveFile(uploadDir, fileName, img);
	        
			opera.setArtista(artistaService.artistaPerId(idArtista));
			opera.setImg(fileName);
			this.operaService.inserisci(opera);
			
			model.addAttribute("opera", opera);
			return "/admin/registrazione/registraOperaCompletata";
		}
		model.addAttribute("artisti", artistaService.tutti());
		return "admin/registrazione/registraOpera";
	}
}
