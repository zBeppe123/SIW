package it.uniroma3.siw.tennis.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.tennis.spring.model.Arbitro;
import it.uniroma3.siw.tennis.spring.repository.ArbitroRepository;

@Service
public class ArbitroService {
	@Autowired
	private ArbitroRepository arbitroRepository;
	
	@Transactional
	public Arbitro inserisci(Arbitro arbitro) {
		return arbitroRepository.save(arbitro);
	}
	
	@Transactional
	public List<Arbitro> tutti(){
		return (List<Arbitro>) arbitroRepository.findAll();
	}
	
	@Transactional
	public List<Arbitro> arbitriPerNomeAndCognome(String nome,String cognome) {
		return arbitroRepository.findByNomeAndCognome(nome,cognome);
	}
	
	@Transactional
	public boolean alreadyExists(Arbitro arbitro) {
		List<Arbitro> arbitri = this.arbitroRepository.findByNomeAndCognome(arbitro.getNome(),arbitro.getCognome());
		if (arbitri.size() > 0)
			return true;
		else 
			return false;
	}
}
