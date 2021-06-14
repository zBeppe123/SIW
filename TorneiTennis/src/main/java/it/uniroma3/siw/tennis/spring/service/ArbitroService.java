package it.uniroma3.siw.tennis.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.tennis.spring.model.Arbitro;
import it.uniroma3.siw.tennis.spring.repository.ArbitroRepository;

/** Questa classe offre tutti i servizi per Arbitro. */
@Service
public class ArbitroService {
	@Autowired
	private ArbitroRepository arbitroRepository;
	
	/** Salva un arbitro nel DB.
	 * @param arbitro Arbitro da salvare.
	 * @return L'arbitro salvato.
	 */
	@Transactional
	public Arbitro inserisci(Arbitro arbitro) {
		return arbitroRepository.save(arbitro);
	}
	
	/** Restituisce tutti gli arbitri iscritti ad ABCTennis.
	 * @return Una lista di tutti gli arbitri iscritti.
	 */
	@Transactional
	public List<Arbitro> tutti(){
		return (List<Arbitro>) arbitroRepository.findAll();
	}
	
	/** Trova un arbitro tramite il suo nome e cognome.
	 * @param nome Nome dell'arbitro.
	 * @param cognome Cognome dell'arbitro.
	 * @return L'arbitro cercato, altrimenti null se non esiste.
	 */
	@Transactional
	public Arbitro arbitroPerNomeAndCognome(String nome,String cognome) {
		Optional<Arbitro> result = arbitroRepository.findByNomeAndCognome(nome,cognome);
		return result.orElse(null);
	}
	
	/** Trova un arbitro tramite l'id.
	 * @param id Id dell'arbitro.
	 * @return L'arbitro cercato, altrimenti null se non esiste.
	 */
	@Transactional
	public Arbitro arbitroPerId(Long id) {
		Optional<Arbitro> result = arbitroRepository.findById(id);
		return result.orElse(null);
		
	}
	
	/** Verifica se esiste gia' lo stesso arbitro
	 * @param arbitro Arbitro da verificare se non esiste uno identico.
	 * @return true se esiste gia' lo stesso arbitro, false altrimenti.
	 */
	@Transactional
	public boolean alreadyExists(Arbitro arbitro) {
		return this.arbitroPerNomeAndCognome(arbitro.getNome(), arbitro.getCognome())!=null;
	}

	/** Salva le modifiche dei dati fatti ad un arbitro.
	 * @param arbitroDatiModificati Arbitro modificato.
	 */
	@Transactional
	public void modificaDatiDiArbitro(Arbitro arbitroDatiModificati) {
		this.arbitroRepository.save(arbitroDatiModificati);
	}
	
	/** Elimina un arbitro dal DB.
	 * @param idArbitro Id dell'arbitro da cancellare.
	 */
	@Transactional
	public void cancellaArbitro(Long idArbitro) {
		this.arbitroRepository.deleteById(idArbitro);
	}

	/** Restituisce tutti gli arbitri che non hanno arbitrato almeno un torneo.
	 * @return Una lista di tutti gli arbitri che non hanno arbitrato.
	 */
	public List<Arbitro> arbitriNonImpegnati() {
		return (List<Arbitro>) this.arbitroRepository.arbitriNonImpegnati();
	}
}
