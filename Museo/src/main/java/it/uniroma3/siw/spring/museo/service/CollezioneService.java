package it.uniroma3.siw.spring.museo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import it.uniroma3.siw.spring.museo.model.Collezione;
import it.uniroma3.siw.spring.museo.model.Opera;
import it.uniroma3.siw.spring.museo.repository.CollezioneRepository;
@Service
public class CollezioneService {
	@Autowired
	private CollezioneRepository collezioneRepository;
	/**
	 * Cerca una collezione tramite il parametro idCollezione
	 * @param idCollezione
	 * @return la collezione se presente nel db, altrimenti null
	 */
	@Transactional
	public Collezione collezionePerId(Long idCollezione) {
		Optional<Collezione> result=collezioneRepository.findById(idCollezione);
		return result.orElse(null);
	}
	/**
	 * cerca una lista di collezioni ordinate per il nome
	 * @return la lista di collezioni
	 */
	@Transactional
	public List<Collezione> getCollezioniOrdinatePerNome() {
		return (List<Collezione>) collezioneRepository.findAllByOrderByNome();
	}
	/**
	 * ritorna una lista di opere appartenenti alla colleizone avente id:idCollezione
	 * @param idCollezione
	 * @return la lista di opere della collezione
	 */
	@Transactional
	public List<Opera> getOpereDellaCollezione(Long idCollezione) {
		return this.collezioneRepository.findTutteOpereDellaCollezione(idCollezione);
	}
	/**
	 * salva la collezione nel DB
	 * @param collezione
	 */
	@Transactional
	public void saveCollezione(Collezione collezione) {
		this.collezioneRepository.save(collezione);
	}
	/**
	 * cerca tutte le collezioni
	 * @return lista di collezioni con tutte le collezioni del db
	 */
	@Transactional
	public List<Collezione> tutti() {
		return (List<Collezione>) this.collezioneRepository.findAll();
	}
	/**
	 * controlla se il nome della collezione inserita è già presnete nel DB
	 * @param c
	 * @return true se ci sono collezioni con quel nome, false altrimenti
	 */
	@Transactional
	public boolean alreadyExists(Collezione c) {
		List<Collezione> res = (List<Collezione>)this.collezioneRepository.findByNome(c.getNome());

		return res.size()>0;
	}
	/**
	 * cancella una collezione dal DB
	 * @param idCollezione
	 */
	public void eliminaCollezioneById(Long idCollezione) {
		collezioneRepository.deleteById(idCollezione);
	}


}
