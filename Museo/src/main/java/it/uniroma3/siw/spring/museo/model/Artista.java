package it.uniroma3.siw.spring.museo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Artista {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String cognome;
	
	@Column(nullable = false)
	private String nazionalita;
	
	@Column(nullable = false)
	private LocalDate dataNascita;
	
	@Column(nullable = false)
	private String cittaNascita;
	
	private LocalDate dataMorte;
	
	private String cittaMorte;
	
	@OneToMany(mappedBy = "artista")
	private List<Opera> opere;

	public Artista(String nome, String cognome, String nazionalita, LocalDate dataNascita, String cittaNascita, 
			LocalDate dataMorte, String cittaMorte, List<Opera> opere) {
		this.nome = nome;
		this.cognome = cognome;
		this.nazionalita = nazionalita;
		this.dataNascita = dataNascita;
		this.cittaNascita = cittaNascita;
		this.dataMorte = dataMorte;
		this.cittaMorte = cittaMorte;
		this.opere = opere;
	}
	
	public Artista(String nome, String cognome, String nazionalita, LocalDate dataNascita, String cittaNascita, List<Opera> opere) {
		this(nome, cognome, nazionalita, dataNascita, cittaNascita, null, null, opere);
	}
	
	public Artista() {
		this.opere = new ArrayList<>();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getNazionalita() {
		return this.nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public LocalDate getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getCittaNascita() {
		return this.cittaNascita;
	}

	public void setCittaNascita(String cittaNascita) {
		this.cittaNascita = cittaNascita;
	}

	public LocalDate getDataMorte() {
		return this.dataMorte;
	}

	public void setDataMorte(LocalDate dataMorte) {
		this.dataMorte = dataMorte;
	}
	
	public String getCittaMorte() {
		return this.cittaMorte;
	}

	public void setCittaMorte(String cittaMorte) {
		this.cittaMorte = cittaMorte;
	}

	public List<Opera> getOpere() {
		return this.opere;
	}


	public void setOpere(List<Opera> opere) {
		this.opere = opere;
	}	
}
