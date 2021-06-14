package it.uniroma3.siw.tennis.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.tennis.spring.model.Tennista;


public interface TennistaRepository extends CrudRepository<Tennista, Long> {

	List<Tennista> findByEmail(String email);
	
}
