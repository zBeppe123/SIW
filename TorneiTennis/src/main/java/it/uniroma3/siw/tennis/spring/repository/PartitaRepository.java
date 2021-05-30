package it.uniroma3.siw.tennis.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.tennis.spring.model.Partita;

public interface PartitaRepository extends CrudRepository<Partita,Long>{
	
	@Query(value="SELECT p FROM Partita p WHERE tennista1.id=:id OR tennista2.id=:id")
	List<Partita> findByTennista1EqualsIdOrTennista2EqualsId(Long id);


}
