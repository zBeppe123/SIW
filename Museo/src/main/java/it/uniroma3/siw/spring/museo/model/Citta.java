package it.uniroma3.siw.spring.museo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Citta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@OneToMany(mappedBy = "luogoDiNascitaArtista")
	private List<Artista> artistiNati;
	
	@OneToMany(mappedBy = "luogoDiMorteArtista")
	private List<Artista> artistiMorti;
	
	@OneToMany(mappedBy = "luogoDiNascitaCuratore")
	private List<Curatore> curatoriNati;
	
	public Citta(String nome, List<Artista> artistiNati, List<Artista> artistiMorti, List<Curatore> curatoriNati) {
		this.nome = nome;
		this.artistiNati = artistiNati;
		this.artistiMorti = artistiMorti;
		this.curatoriNati = curatoriNati;
	}

	public Citta(String nome) {
		this(nome, new ArrayList<Artista>(), new ArrayList<Artista>(), new ArrayList<Curatore>());
	}
	
	public Citta() {
		this.artistiMorti = new ArrayList<>();
		this.artistiNati = new ArrayList<>();
		this.curatoriNati = new ArrayList<>();
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

	public List<Artista> getArtistiNati() {
		return this.artistiNati;
	}

	public void setArtistiNati(List<Artista> artistiNati) {
		this.artistiNati = artistiNati;
	}

	public List<Artista> getArtistiMorti() {
		return this.artistiMorti;
	}

	public void setArtistiMorti(List<Artista> artistiMorti) {
		this.artistiMorti = artistiMorti;
	}

	public List<Curatore> getCuratoriNati() {
		return this.curatoriNati;
	}

	public void setCuratoriNati(List<Curatore> curatoriNati) {
		this.curatoriNati = curatoriNati;
	}
}
