package it.uniroma3.siw.tennis.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.tennis.spring.model.Partita;

public interface PartitaRepository extends CrudRepository<Partita,Long>{
	/** Restituisce tutte le partite svolte fatte da un tennista. */
	@Query(value="SELECT p FROM Partita p WHERE tennista1.id=:id OR tennista2.id=:id")
	List<Partita> findByTennista1EqualsIdOrTennista2EqualsId(Long id);

	/** Restituisce la lista di tutte le partite di un torneo. */
	@Query(value="SELECT p FROM Partita p WHERE torneo.id=:id")
	List<Partita> findByTorneoId(Long id);
}
