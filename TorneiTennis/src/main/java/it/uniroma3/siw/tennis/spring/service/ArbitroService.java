package it.uniroma3.siw.tennis.spring.service;

import java.util.List;
import java.util.Optional;

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
	public Arbitro arbitroPerNomeAndCognome(String nome,String cognome) {
		Optional<Arbitro> result = arbitroRepository.findByNomeAndCognome(nome,cognome);
		return result.orElse(null);
	}
	
	@Transactional
	public Arbitro arbitroPerId(Long id) {
		Optional<Arbitro> result = arbitroRepository.findById(id);
		return result.orElse(null);
		
	}
	
	@Transactional
	public boolean alreadyExists(Arbitro arbitro) {
		return this.arbitroPerNomeAndCognome(arbitro.getNome(), arbitro.getCognome())!=null;
	}

	@Transactional
	public void modificaDatiDiArbitro(Arbitro arbitroDatiModificati) {
		this.arbitroRepository.save(arbitroDatiModificati);
	}
	@Transactional
	public void cancellaArbitro(Long idArbitro) {
		this.arbitroRepository.deleteById(idArbitro);
	}

	public List<Arbitro> arbitriNonImpegnati() {
		return (List<Arbitro>) this.arbitroRepository.arbitriNonImpegnati();
	}
}
