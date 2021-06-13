package it.uniroma3.siw.spring.museo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.spring.museo.model.Opera;
import it.uniroma3.siw.spring.museo.repository.OperaRepository;
@Service
public class OperaService {
	@Autowired
	private OperaRepository operaRepository;
	
	/**
	 * Cerca un'opera dal DB per id
	 * @param idOpera l'id dell'opera
	 * @return ritorna l'opera o null nel caso in cui non viene trovata
	 */
	@Transactional
	public Opera operaPerId(Long idOpera) {
		Optional<Opera> result=operaRepository.findById(idOpera);
		return result.orElse(null);
	}
	/**
	 * Crea un entita di un'opera nel DB
	 * @param Opera l'opera da salvare
	 * @return ritorna l'opera
	 */
	@Transactional
	public Opera inserisci(Opera opera) {
		return operaRepository.save(opera);
		
	}
	/**
	 * Cerca tutte le opere non inserite in nessuna collezione
	 * @return lista di opere non inserite in nessuna collezione
	 */
	@Transactional
	public Object getOpereNonInseriteAdUnaCollezione() {
		return this.operaRepository.findTutteOpereNonInseriteAdUnaCollezione();
	}
	@Transactional
	public List<Opera> tutti() {
		return (List<Opera>) operaRepository.findAll();
	}
	/**
	 * elimina un opera
	 * @param idOpera
	 */
	@Transactional
	public void eliminaOperaById(Long idOpera) {
		operaRepository.deleteById(idOpera);
	}
	/**
	 * aggiorna le opere nel db, cambiandone il riferimento alla collezione
	 * @param opere inserite nella collezione
	 */
	public void inserisciOpere(List<Opera> opere) {
		operaRepository.saveAll(opere);
		
	}
}
