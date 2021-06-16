package it.uniroma3.siw.spring.museo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.museo.model.Curatore;

public interface CuratoreRepositoy extends CrudRepository <Curatore,Long>{

	public List<Curatore> findByMatricola(Long matricola);

}
