package it.uniroma3.siw.tennis.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.tennis.spring.model.Torneo;

public interface TorneoRepository extends CrudRepository<Torneo,Long>{
	public Torneo findByNome(String nomeTorneo);
	
	//SELECT COUNT(tt.tennisti_iscritti_id) FROM Torneo tor LEFT JOIN Torneo_tennisti_iscritti tt on tor.id=tt.tornei_iscritti_id WHERE tt.tornei_iscritti_id=:id GROUP BY(tt.tornei_iscritti_id) ORDER BY(tt.tornei_iscritti_id)
	//@Query(value="SELECT (tor.numeroMaxDiPartecipanti-COUNT(ten)) AS numPostiDisponibili FROM Torneo as tor LEFT JOIN tor.tennistiIscritti as ten WHERE ten.getId()!=:id AND numPostiDisponibili>0 GROUP BY(tor.id) ORDER BY(tor.id)")
	//@Query (value="SELECT numPostiDisponibili FROM (SELECT tor, (tor.numero_max_di_partecipanti-COUNT(tt.tennisti_iscritti_id)) as numPostiDisponibili FROM Torneo tor LEFT JOIN Torneo_tennisti_iscritti tt ON tor.id=tt.tornei_iscritti_id WHERE tt.tennisti_iscritti_id != :idTennista GROUP BY(tor) ORDER BY(tor)) AS ta WHERE numPostiDisponibili>0", nativeQuery=true)
	//public List<Integer> findNumeroPartecipantiPerOgniTorneo(Long idTennista);
	//@Query (value="SELECT tor FROM (SELECT tor, COUNT(tt.tennisti_iscritti_id) as numPostiDisponibili FROM Torneo tor LEFT JOIN Torneo_tennisti_iscritti tt ON tor.id=tt.tornei_iscritti_id WHERE tt.tennisti_iscritti_id!=:idTennista GROUP BY(tor) ORDER BY(tor)) AS ta WHERE numPostiDisponibili>0", nativeQuery=true)
//	@Query (value="(SELECT tor \r\n"
//			+ "FROM Torneo tor \r\n"
//			+ "LEFT JOIN tornei_tennisti tt \r\n"
//			+ "ON tor.id=tt.tornei_iscritti_id  \r\n"
//			+ "GROUP BY(tor)) EXCEPT (SELECT tor \r\n"
//			+ "					  FROM Torneo tor \r\n"
//			+ "					  LEFT JOIN tornei_tennisti tt \r\n"
//			+ "					  ON tor.id=tt.tornei_iscritti_id \r\n"
//			+ "					  WHERE tt.tennisti_iscritti_id=2 \r\n"
//			+ "					  GROUP BY(tor))",nativeQuery=true)
	@Query (value="SELECT tor FROM Torneo as tor LEFT JOIN tor.tennistiIscritti WHERE tor NOT IN (SELECT t FROM Torneo as t LEFT JOIN t.tennistiIscritti as te WHERE te.id=:idTennista GROUP BY(t)) AND tor.numeroPartecipanti<tor.numeroMaxDiPartecipanti AND NOT( tor.mese<=:mese AND tor.anno=:anno  OR  tor.anno<:anno) GROUP BY(tor)")
	public List<Torneo> findTorneiDisponibili(Long idTennista, Integer mese, Integer anno);

	@Query (value="SELECT tor FROM Torneo as tor LEFT JOIN tor.tennistiIscritti as te WHERE te.id=:idTennista AND NOT(tor.mese<=:mese AND tor.anno=:anno  OR  tor.anno<:anno)")
	public List<Torneo> findTorneiIscritti(Long idTennista,Integer mese,Integer anno);
	
	@Query (value="SELECT tor FROM Torneo as tor WHERE NOT( tor.mese<=:mese AND tor.anno=:anno  OR  tor.anno<:anno)")
	public List<Torneo> findTorneiCancellabili(Integer mese,Integer anno);
	
	@Query	(value="SELECT tor FROM Torneo as tor WHERE ( tor.mese=:mese AND tor.anno=:anno)")
	public List<Torneo> findTorneiDisponibiliAttuali(Integer mese, Integer anno);
	
	@Query	(value="SELECT tor FROM Torneo as tor WHERE ( tor.mese<=:mese AND tor.anno=:anno OR tor.anno<:anno)")
	public List<Torneo> findTorneiDisponibiliAttualiEFiniti(Integer mese, Integer anno);
	
	

}
