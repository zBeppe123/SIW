package it.uniroma3.siw.spring.museo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.museo.model.Opera;

public interface OperaRepository extends CrudRepository<Opera,Long>{
	@Query(value = "SELECT o FROM Opera o WHERE o.collezione=null")
	public Iterable<Opera> findTutteOpereNonInseriteAdUnaCollezione();
}
