package it.uniroma3.siw.spring.museo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.museo.model.Collezione;
import it.uniroma3.siw.spring.museo.repository.CollezioneRepository;
@Service
public class CollezioneService {
	@Autowired
	private CollezioneRepository collezioneRepository;
	
	public List<Collezione> getCollezioni() {
		return (List<Collezione>) collezioneRepository.findAll();
	}

}
