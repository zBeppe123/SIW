package it.uniroma3.siw.spring.museo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.museo.model.Artista;
import it.uniroma3.siw.spring.museo.model.Collezione;
import it.uniroma3.siw.spring.museo.repository.CollezioneRepository;
@Service
public class CollezioneService {
	@Autowired
	private CollezioneRepository collezioneRepository;
	
	public List<Collezione> getCollezioni() {
		return (List<Collezione>) collezioneRepository.findAll();
	}

	public Collezione collezionePerId(Long idCollezione) {
		Optional<Collezione> result=collezioneRepository.findById(idCollezione);
		return result.orElse(null);
	}

	public List<Collezione> getCollezioniOrdinatePerNome() {
		return (List<Collezione>) collezioneRepository.findAllByOrderByNome();
	}

	public void saveCollezione(Collezione collezione) {
		this.collezioneRepository.save(collezione);
	}

	public List<Collezione> tutti() {
		return (List<Collezione>) this.collezioneRepository.findAll();
	}

	public boolean alreadyExists(Collezione c) {
		List<Collezione> res = (List<Collezione>)this.collezioneRepository.findByNome(c.getNome());

		return res.size()>0;
	}
}
