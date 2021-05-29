package it.uniroma3.siw.tennis.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.tennis.spring.model.Torneo;

public interface TorneoRepository extends CrudRepository<Torneo,Long>{
	public Torneo findByNome(String nomeTorneo);
}
