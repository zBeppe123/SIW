package it.uniroma3.siw.spring.museo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.museo.model.Artista;
import it.uniroma3.siw.spring.museo.repository.ArtistaRepository;

@Service
public class ArtistaServiece {
	private ArtistaRepository artistaRepository;
	
	/**
	 * Cerca un'artista dal DB per id
	 * @param idArtista l'id dell'artista
	 * @return ritorna l'artista o null nel caso in cui non viene trovato
	 */
	@Transactional
	public Artista artistaPerId(Long idArtista) {
		Optional<Artista> result=artistaRepository.findById(idArtista);
		return result.orElse(null);
	}
}
