package it.uniroma3.siw.spring.museo.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.museo.model.Collezione;

public interface CollezioneRepository extends CrudRepository<Collezione,Long>{
	public Iterable<Collezione> findAllByOrderByNome();

}
