package it.uniroma3.siw.tennis.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.tennis.spring.model.Tennista;
import it.uniroma3.siw.tennis.spring.model.Torneo;
import it.uniroma3.siw.tennis.spring.model.TorneoDisponibile;
import it.uniroma3.siw.tennis.spring.repository.TorneoRepository;

@Service
public class TorneoService {
	@Autowired
	private TorneoRepository torneoRepository;
	
	@Transactional
	public Torneo inserisci(Torneo toreno) {
		return torneoRepository.save(toreno);
	}
	
	/** Restituisce una lista di tutti i tornei di ABCTennis.
	 * @return Una lista di tutti i tornei di ABCTennis. */
	@Transactional
	public List<Torneo> tutti() {
		return (List<Torneo>) torneoRepository.findAll();
	}
	
	/* Trova e restituisce il torneo tramite l'id.
	 * @param id Id del torneo.
	 * @return Torneo cercato oppure null se non esiste tale torneo con l'id specificato.*/
	@Transactional
	public Torneo getTorneoPerId(Long id) {
		Optional<Torneo> result = this.torneoRepository.findById(id);
		return result.orElse(null);
	}
	
	/** Verifica se esiste gia' un torneo con lo stesso nel sistema.
	 * @param torneo Torneo per cui si vuole fare la verifica.
	 * @return true se esiste gia' un torneo con lo stesso nome, false altrimenti. */
	@Transactional
	public boolean alreadyExits(Torneo torneo) {
		return this.torneoRepository.findByNome(torneo.getNome()) != null;
	}

	public Torneo torneoPerId(long id) {
		Optional<Torneo> result = torneoRepository.findById(id);
		return result.orElse(null);
	}
	
	public void iscriviTennista(Tennista tennista,Long idToreno) {
		
	}
//	@Transactional
//	public List<Integer> getPostiDisponibili(Long id) {
//		return (List<Integer>) torneoRepository.findNumeroPartecipantiPerOgniTorneo(id);
//	}
	@Transactional
	public List<Torneo> getTorneiDisponibili(Long idTennista) {
		return (List<Torneo>) torneoRepository.findTorneiDisponibili(idTennista);
	}

	public void iscriviTennista(Torneo torneo) {
		torneoRepository.save(torneo);
		
	}

//	public List<TorneoDisponibile> tuttiTorneiPiuIntero() {
//		return (List<TorneoDisponibile>) torneoRepository.findTuttiTorneoPiùIntero();
//	}
}
