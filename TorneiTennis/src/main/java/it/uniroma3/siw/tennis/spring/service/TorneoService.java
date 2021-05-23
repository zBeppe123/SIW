package it.uniroma3.siw.tennis.spring.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.tennis.spring.model.Torneo;
import it.uniroma3.siw.tennis.spring.repository.TorneoRepository;

public class TorneoService {
	@Autowired
	private TorneoRepository torenoRepository;
	
	@Transactional
	public Torneo inserisci(Torneo toreno) {
		return torenoRepository.save(toreno);
	}
}
