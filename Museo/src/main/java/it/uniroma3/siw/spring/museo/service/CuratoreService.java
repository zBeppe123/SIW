package it.uniroma3.siw.spring.museo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.museo.model.Curatore;
import it.uniroma3.siw.spring.museo.repository.CuratoreRepositoy;
@Service
public class CuratoreService {
	@Autowired
	private CuratoreRepositoy curatoreRepository;
	public void saveCuratore(Curatore curatore) {
		this.curatoreRepository.save(curatore);
	}

	public boolean matricolaAlreadyExists(Long matricola) {
		List<Curatore> res = (List<Curatore>)this.curatoreRepository.findByMatricola(matricola);
		return res.size()>0;
	}

}
