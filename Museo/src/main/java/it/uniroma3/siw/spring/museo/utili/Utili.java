package it.uniroma3.siw.spring.museo.utili;

import java.io.IOException;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class Utili {
	private final static String PATH_SAVE_IMAGES = "src/main/resources/static/images/opere/";
			
	/** Salva l'immagine nel sistema.
	 * @param file File che si vuole salvare
	 * @return Il nome del file salvato.
	 * @throws IOException Se si ha problemi di salvataggio del file.
	 */
	public static String salvaImmagine(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String uploadDir = PATH_SAVE_IMAGES;
		
        FileManagerUtils.saveFile(uploadDir, fileName, file);
        
		return fileName;
	}
	
	/** Cancella un file da sistema.
	 * @param nomeFile Nome del file da cacellare.
	 * @throws IOException
	 */
	public static void cancellaImmagine(String nomeFile) throws IOException {
		FileManagerUtils.deleteFile(PATH_SAVE_IMAGES, nomeFile);
	}
}
