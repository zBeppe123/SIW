package it.uniroma3.siw.tennis.spring.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.tennis.spring.model.Tennista;
import it.uniroma3.siw.tennis.spring.repository.TennistaRepository;

/** Questa class offre tutti i servizi per Tennista. */
@Service
public class TennistaService {

    @Autowired
    protected TennistaRepository tennistaRepository;
    
    /**
     * Salva un tennista nel db.
     * @param tennista Tennista che si vuole salvare.
     * @return Il tennista salvato
     */
    @Transactional
    public Tennista saveTennista(Tennista tennista) {
        return this.tennistaRepository.save(tennista);
    }
    
    /** Restituisce tutti i tennisti iscritti ad ABCTennist.
     * @return Una lista di tutti i tennisti iscritti.
     */
	@Transactional
	public List<Tennista> tutti(){
		return (List<Tennista>) tennistaRepository.findAll();
	}

	/** Cerca un tennista tramite l'id.
	 * @param id Id del tennista
	 * @return Il tennista cercato, altrimenti null se non esiste.
	 */
	public Tennista tennistaPerId(long id) {
		Optional<Tennista> result = tennistaRepository.findById(id);
		return result.orElse(null);
	}
}
