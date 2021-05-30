package it.uniroma3.siw.tennis.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.tennis.spring.model.Partita;
import it.uniroma3.siw.tennis.spring.model.Tennista;
import it.uniroma3.siw.tennis.spring.repository.PartitaRepository;

@Service
public class PartitaService {
	@Autowired
	private PartitaRepository partitaRepository;
	
	@Transactional
	public Partita inserisci(Partita partita) {
		return partitaRepository.save(partita);
	}
	
	@Transactional
	public List<Partita> tutti() {
		return (List<Partita>) partitaRepository.findAll();
	}
	
	@Transactional
	public List<Partita> getPartiteByTennista(Long id) {
		return (List<Partita>) partitaRepository.findByTennista1EqualsIdOrTennista2EqualsId(id);
	}

	public Object getPartiteByToreno(Long idTorneo) {
		return (List<Partita>) partitaRepository.findByTorneoId(idTorneo);
	}
}
