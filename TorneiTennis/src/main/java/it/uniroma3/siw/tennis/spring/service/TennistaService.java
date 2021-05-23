package it.uniroma3.siw.tennis.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.tennis.spring.model.Tennista;
import it.uniroma3.siw.tennis.spring.repository.TennistaRepository;

@Service
public class TennistaService {
	@Autowired
	private TennistaRepository tennistaRepository;
	
	@Transactional
	public Tennista inserisci(Tennista tennista) {
		return tennistaRepository.save(tennista);
	}
	
	@Transactional
	public List<Tennista> tutti(){
		return (List<Tennista>) tennistaRepository.findAll();
	}
	
	@Transactional
	public List<Tennista> tennistiPerEmail(String email) {
		return tennistaRepository.findByEmail(email);
	}
	
	@Transactional
	public boolean alreadyExists(Tennista tennista) {
		List<Tennista> tennisti = this.tennistaRepository.findByEmail(tennista.getEmail());
		if (tennisti.size() > 0)
			return true;
		else 
			return false;
	}
}
