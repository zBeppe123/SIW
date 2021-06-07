package it.uniroma3.siw.spring.museo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.spring.museo.model.Artista;

public interface ArtistaRepository extends CrudRepository<Artista,Long> {
	public Iterable<Artista> findByCognomeAndNomeAndNazionalitaAndDataNascitaAndCittaNascita(String cognome, String nome, String nazionalita, LocalDate dataNascita, String cittaNascita);
}
