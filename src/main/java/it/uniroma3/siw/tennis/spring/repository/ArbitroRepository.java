package it.uniroma3.siw.tennis.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.tennis.spring.model.Arbitro;

public interface ArbitroRepository extends CrudRepository<Arbitro,Long>{

	public Optional<Arbitro> findByNomeAndCognome(String nome, String cognome);

	@Query(value="SELECT ar FROM Arbitro as ar WHERE ar NOT IN (SELECT t.arbitro FROM Torneo as t GROUP BY(t.arbitro))")
	public List<Arbitro> arbitriNonImpegnati();
}
