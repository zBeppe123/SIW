package it.uniroma3.siw.tennis.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.tennis.spring.model.Partita;
import it.uniroma3.siw.tennis.spring.repository.PartitaRepository;

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
}
