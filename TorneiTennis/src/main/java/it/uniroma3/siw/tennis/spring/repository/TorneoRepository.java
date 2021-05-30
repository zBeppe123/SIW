package it.uniroma3.siw.tennis.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.tennis.spring.model.Torneo;

public interface TorneoRepository extends CrudRepository<Torneo,Long>{
	public Torneo findByNome(String nomeTorneo);
	
	//SELECT COUNT(tt.tennisti_iscritti_id) FROM Torneo tor LEFT JOIN Torneo_tennisti_iscritti tt 	on tor.id=tt.tornei_iscritti_id GROUP BY(tt.tornei_iscritti_id) ORDER BY(tt.tornei_iscritti_id)
//	@Query(value="SELECT COUNT(tor.id) FROM Torneo tor GROUP BY(tor.id) ORDER BY(tor.id)")
//	public List<Integer> findNumeroPartecipantiPerOgniTorneo();
}
