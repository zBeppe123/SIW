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
	/**
	 * Salva il curatore nel DB
	 * @param curatore
	 */
	@Transactional
	public void saveCuratore(Curatore curatore) {
		this.curatoreRepository.save(curatore);
	}
	/**
	 * controlla se la matriocola inserita nel curatore è già presente nel DB
	 * @param matricola
	 * @return true se è presete la matricola, false altrimenti
	 */
	@Transactional
	public boolean matricolaAlreadyExists(Long matricola) {
		List<Curatore> res = (List<Curatore>)this.curatoreRepository.findByMatricola(matricola);
		return res.size()>0;
	}
	/**
	 * cerca un curatore nel DB tramite l'id
	 * @param idCuratore
	 * @return curatore cercato oppure null se non esiste
	 */
	@Transactional
	public Curatore curatorePerId(Long idCuratore) {
		Optional<Curatore> result=curatoreRepository.findById(idCuratore);
		return result.orElse(null);
	}
	/**
	 * cerca tutti i curatori nel db
	 * @return lista di tutti i curatori del db
	 */
	@Transactional
	public List<Curatore> tutti() {
		return (List<Curatore>) this.curatoreRepository.findAll();
	}

}
