package it.uniroma3.siw.tennis.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.tennis.spring.model.Arbitro;

public interface ArbitroRepository extends CrudRepository<Arbitro,Long>{

	List<Arbitro> findByNomeAndCognome(String nome, String cognome);

}
