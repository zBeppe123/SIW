package it.uniroma3.siw.tennis.spring.service;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import it.uniroma3.siw.tennis.spring.model.Torneo;
import it.uniroma3.siw.tennis.spring.repository.TorneoRepository;

/** Questa classe contiene tutti i serivizi offerti per Torneo.
 */
@Service
public class TorneoService {
	@Autowired
	private TorneoRepository torneoRepository;
	
	/** Restituisce il mese corrente.
	 * @return Mese corrente-
	 */
	private Integer getMese() {
		return LocalDate.now().getMonthValue();
	}
	
	/** Restituisce l'anno corrente.
	 * @return Anno corrente
	 */
	private Integer getAnno() {
		return LocalDate.now().getYear();
	}

	/** Salva un torneo nel sistema.
	 * @param torneo Torneo che si vuole salvare.
	 * @return Il torneo salvatato.
	 */
	@Transactional
	public Torneo inserisci(Torneo torneo) {
		return torneoRepository.save(torneo);
	}

	/**
	 * Restituisce una lista di tutti i tornei di ABCTennis.
	 * 
	 * @return Una lista di tutti i tornei di ABCTennis.
	 */
	@Transactional
	public List<Torneo> tutti() {
		return (List<Torneo>) torneoRepository.findAll();
	}

	/*
	 * Trova e restituisce il torneo tramite l'id.
	 * 
	 * @param id Id del torneo.
	 * 
	 * @return Torneo cercato oppure null se non esiste tale torneo con l'id
	 * specificato.
	 */
	@Transactional
	public Torneo getTorneoPerId(Long id) {
		Optional<Torneo> result = this.torneoRepository.findById(id);
		return result.orElse(null);
	}
	
	/** Trova un torneo tramite il nome.
	 * @param nome Nome del torneo
	 * @return Il torneo cercato, altrimenti null se non esiste.
	 */
	@Transactional
	public Torneo torneoPerNome(String nome) {
		return this.torneoRepository.findByNome(nome);
	}

	/**
	 * Verifica se esiste gia' un torneo con lo stesso nome esiste nel sistema.
	 * 
	 * @param torneo Torneo per cui si vuole fare la verifica.
	 * @return true se esiste gia' un torneo con lo stesso nome, false altrimenti.
	 */
	@Transactional
	public boolean alreadyExists(Torneo torneo) {
		return this.torneoRepository.findByNome(torneo.getNome()) != null;
	}
	
	/** Salva le modifica dei dati fatti ad un torneo.
	 * @param torneoModificato Torneo per cui sono modificati i dati.
	 */
	@Transactional
	public void modificaDatiDiTorneo(Torneo torneoModificato) {
		this.torneoRepository.save(torneoModificato);
	}

	/** Restituisce la lista di tutti i tornei disponibili nel mese e anno corrente per cui un tennista non e' ancora iscritto.
	 * @param idTennista Id del tennista
	 * @return Una lista di torneo disponibili per cui un tennista non e' ancora iscritto
	 */
	@Transactional
	public List<Torneo> getTorneiDisponibili(Long idTennista) {
		return (List<Torneo>) torneoRepository.findTorneiDisponibili(idTennista,this.getMese(),this.getAnno());
	}

	/** Iscrive un tennista al torneo.
	 * @param torneo Torneo per cui un tennista si e' iscritto.
	 */
	@Transactional
	public void iscriviTennista(Torneo torneo) {
		torneoRepository.save(torneo);
	}

	/** Restituisce tutti i tornei per cui un tennista e' iscritto.
	 * @param idTennista Id del tennista.
	 * @return Una lista di tornei per cui un tennista eo iscritto.
	 */
	@Transactional
	public List<Torneo> getTorneiIscrittiDaTennista(Long idTennista) {
		return (List<Torneo>) torneoRepository.findTorneiIscritti(idTennista,this.getMese(),this.getAnno());
	}
	
	/** Restituisce tutti i tornei che sono cancellabili (ovvero che non sono ancora svolte le partite nel periodo indicato).
	 * @return Una lista di tutti i tornei cancellabili.
	 */
	@Transactional
	public List<Torneo> getTorneiCancellabili() {
		return torneoRepository.findTorneiCancellabili(this.getMese(),this.getAnno());
	}
	
	/** Elminina un torneo dal sistema.
	 * @param idTorneo Id del torneo che si vuole cancellare.
	 */
	@Transactional
	public void eliminaTorneo(Long idTorneo) {
		torneoRepository.deleteById(idTorneo);		
	}
	
	/** Restituisce tutti i tornei disponibili nel mese e anno corrente.
	 * @return Una lista di tutti i tornei disponibili.
	 */
	@Transactional
	public List<Torneo> getTorneiDisponibili() {
		return (List<Torneo>) torneoRepository.findTorneiDisponibiliAttuali(this.getMese(),this.getAnno());
	}
	
	/** Restituisce tutti i tornei che sono stati svolti e conclusi.
	 * @return Una lista di tutti i torneo svolti e conclusi.
	 */
	@Transactional
	public Object getTorneiDisponibiliEFiniti() {
		return (List<Torneo>) torneoRepository.findTorneiDisponibiliAttualiEFiniti(this.getMese(),this.getAnno());
	}
	
}
