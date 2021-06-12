package it.uniroma3.siw.spring.museo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.museo.model.Collezione;
import it.uniroma3.siw.spring.museo.model.Opera;

public interface CollezioneRepository extends CrudRepository<Collezione,Long>{
	public Iterable<Collezione> findAllByOrderByNome();

	public List<Collezione> findByNome(String nome);
	
	@Query(value = "SELECT o FROM Opera o WHERE o.collezione.id=:idCollezione")
	public List<Opera> findTutteOpereDellaCollezione(Long idCollezione);

}
