package it.uniroma3.siw.spring.museo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.spring.museo.model.Artista;
import it.uniroma3.siw.spring.museo.model.Curatore;
import it.uniroma3.siw.spring.museo.repository.CuratoreRepositoy;
@Service
public class CuratoreService {
	@Autowired
	private CuratoreRepositoy curatoreRepository;
	@Transactional
	public void saveCuratore(Curatore curatore) {
		this.curatoreRepository.save(curatore);
	}
	@Transactional
	public boolean matricolaAlreadyExists(Long matricola) {
		List<Curatore> res = (List<Curatore>)this.curatoreRepository.findByMatricola(matricola);
		return res.size()>0;
	}
	@Transactional
	public Curatore curatorePerId(Long idCuratore) {
		Optional<Curatore> result=curatoreRepository.findById(idCuratore);
		return result.orElse(null);
	}
	@Transactional
	public List<Curatore> tutti() {
		return (List<Curatore>) this.curatoreRepository.findAll();
	}

}
