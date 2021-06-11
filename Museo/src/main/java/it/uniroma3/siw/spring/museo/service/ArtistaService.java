package it.uniroma3.siw.spring.museo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.museo.model.Artista;
import it.uniroma3.siw.spring.museo.repository.ArtistaRepository;

@Service
public class ArtistaService {
	@Autowired
	private ArtistaRepository artistaRepository;
	
	@Transactional
	public void saveArtista(Artista artista) {
		this.artistaRepository.save(artista);
	}
	
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
	@Transactional
	public List<Artista> tutti() {
		return (List<Artista>) this.artistaRepository.findAll();
	}
 	
	@Transactional
	public boolean alreadyExists(Artista artista) {
		List<Artista> res = (List<Artista>)this.artistaRepository.findByCognomeAndNomeAndNazionalitaAndDataNascitaAndCittaNascita(artista.getCognome(),
				 														artista.getNome(),
				 														artista.getNazionalita(),
				 														artista.getDataNascita(),
				 														artista.getCittaNascita());
		
		return res.size()>0;
	}
	@Transactional
	public List<Artista> getArtistiOrdinatiPerCognome() {
		return (List<Artista>) artistaRepository.findAllByOrderByCognome();
	}
}
