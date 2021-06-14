package it.uniroma3.siw.tennis.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.tennis.spring.model.Partita;
import it.uniroma3.siw.tennis.spring.repository.PartitaRepository;

/** Questa classe offre tutti i servizi per Partita. */
@Service
public class PartitaService {
	@Autowired
	private PartitaRepository partitaRepository;
	
	/** Salva la partita nel DB.
	 * @param partita Partita che si vuole salvare.
	 * @return La partita salvata.
	 */
	@Transactional
	public Partita inserisci(Partita partita) {
		return partitaRepository.save(partita);
	}
	
	/** Restituisce tutte le partite svolte in ABCTennis.
	 * @return Una lista di tutte le partite svolte.
	 */
	@Transactional
	public List<Partita> tutti() {
		return (List<Partita>) partitaRepository.findAll();
	}
	
	/** Restituisce tutte le partite svolte da un tennista.
	 * @param id Id del tennista
	 * @return Una lista di tutte le partite svolte dal tennista.
	 */
	@Transactional
	public List<Partita> getPartiteByTennista(Long id) {
		return (List<Partita>) partitaRepository.findByTennista1EqualsIdOrTennista2EqualsId(id);
	}
	 
	/** Restituisce tutte le partite svolte di un torneo.
	 * @param idTorneo Id del torneo.
	 * @return Una lista di tutte le partite svolte del torneo.
	 */
	@Transactional
	public List<Partita> getPartiteByTorneo(Long idTorneo) {
		return (List<Partita>) partitaRepository.findByTorneoId(idTorneo);
	}
	
	/** Elimina una partita dal DB.
	 * @param idPartita Id della partita da eliminare.
	 */
	@Transactional
	public void eliminaPartita(Long idPartita) {
		partitaRepository.deleteById(idPartita);
	}
}
