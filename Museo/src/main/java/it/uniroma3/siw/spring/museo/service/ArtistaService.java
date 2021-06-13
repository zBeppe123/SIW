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
	/**
	 * Salva l'artista nel DB
	 * @param artista
	 */
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
	/**
	 * Cerca una lista di artisti nel db
	 * @return lista di artisti
	 */
	@Transactional
	public List<Artista> tutti() {
		return (List<Artista>) this.artistaRepository.findAll();
	}
 	/**
 	 * Controlla se l'artista è già presente nel DB
 	 * @param artista
 	 * @return true se la grandezza della lista di artisti trovata è > 0, altrimenti false
 	 */
	@Transactional
	public boolean alreadyExists(Artista artista) {
		List<Artista> res = (List<Artista>)this.artistaRepository.findByCognomeAndNomeAndNazionalitaAndDataNascitaAndCittaNascita(artista.getCognome(),
				 														artista.getNome(),
				 														artista.getNazionalita(),
				 														artista.getDataNascita(),
				 														artista.getCittaNascita());
		
		return res.size()>0;
	}
	/**
	 * Prende tutti gli artisti ordinati per Cognome
	 * @return la lista di Artisti ordinata per cognome
	 */
	@Transactional
	public List<Artista> getArtistiOrdinatiPerCognome() {
		return (List<Artista>) artistaRepository.findAllByOrderByCognome();
	}
}
