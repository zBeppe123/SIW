package it.uniroma3.siw.model;

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
	
	private LocalDate dataMorte;
	
	@ManyToOne
	private Citta luogoDiNascitaArtista;
	
	@ManyToOne
	private Citta luogoDiMorteArtista;
	
	@OneToMany(mappedBy = "artista")
	private List<Opera> opere;

	public Artista(String nome, String cognome, String nazionalita, LocalDate dataNascita, Citta luogoDiNascita, 
			LocalDate dataMorte, Citta luogoDiMorte, List<Opera> opere) {
		this.nome = nome;
		this.cognome = cognome;
		this.nazionalita = nazionalita;
		this.dataNascita = dataNascita;
		this.luogoDiMorteArtista = luogoDiNascita;
		this.dataMorte = dataMorte;
		this.luogoDiMorteArtista = luogoDiMorte;
		this.opere = opere;
	}
	
	public Artista(String nome, String cognome, String nazionalita, LocalDate dataNascita, Citta luogoDiNascita, List<Opera> opere) {
		this(nome, cognome, nazionalita, dataNascita, luogoDiNascita, null, null, opere);
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
	
	public Citta getLuogoDiNascitaArtista() {
		return this.luogoDiNascitaArtista;
	}
	
	public void setLuogoDiNascitaArtista(Citta luogoDiNascitaArtista) {
		this.luogoDiNascitaArtista = luogoDiNascitaArtista;
	}

	public LocalDate getDataMorte() {
		return this.dataMorte;
	}

	public void setDataMorte(LocalDate dataMorte) {
		this.dataMorte = dataMorte;
	}
	
	public Citta getLuogoDiMorteArtista() {
		return this.luogoDiMorteArtista;
	}

	public void setLuogoDiMorteArtista(Citta luogoDiMorteArtista) {
		this.luogoDiMorteArtista = luogoDiMorteArtista;
	}
	
	public List<Opera> getOpere() {
		return this.opere;
	}


	public void setOpere(List<Opera> opere) {
		this.opere = opere;
	}	
}
