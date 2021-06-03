package it.uniroma3.siw.tennis.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.tennis.spring.model.Arbitro;

public interface ArbitroRepository extends CrudRepository<Arbitro,Long>{

	List<Arbitro> findByNomeAndCognome(String nome, String cognome);

	@Query(value="SELECT ar FROM Arbitro as ar WHERE ar NOT IN (SELECT t.arbitro FROM Torneo as t GROUP BY(t.arbitro))")
	List<Arbitro> arbitriNonImpegnati();
}
