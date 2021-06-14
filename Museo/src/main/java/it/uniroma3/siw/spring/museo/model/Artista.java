package it.uniroma3.siw.spring.museo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
/**
 * entità artista, un artista è composto da Long id, String nome, String cognome, String nazionalita, LocalDate dataNascita,
	String cittaNascita, LocalDate dataMorte, String cittaMorte, String biografia, List<Opera> opere;
 */
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
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataNascita;
	
	@Column(nullable = false)
	private String cittaNascita;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataMorte;
	
	private String cittaMorte;
	
	@Lob
	private String biografia;
	
	@OneToMany(mappedBy = "artista")
	private List<Opera> opere;

	public Artista(String nome, String cognome, String nazionalita, LocalDate dataNascita, String cittaNascita, 
			LocalDate dataMorte, String cittaMorte, String biografia, List<Opera> opere) {
		this.nome = nome;
		this.cognome = cognome;
		this.nazionalita = nazionalita;
		this.dataNascita = dataNascita;
		this.cittaNascita = cittaNascita;
		this.dataMorte = dataMorte;
		this.cittaMorte = cittaMorte;
		this.biografia = biografia;
		this.opere = opere;
	}
	
	public Artista(String nome, String cognome, String nazionalita, LocalDate dataNascita, String cittaNascita, List<Opera> opere, String biografia) {
		this(nome, cognome, nazionalita, dataNascita, cittaNascita, null, null, biografia, opere);
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

	public String getBiografia() {
		return this.biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public List<Opera> getOpere() {
		return this.opere;
	}
	
	public void setOpere(List<Opera> opere) {
		this.opere = opere;
	}
}
