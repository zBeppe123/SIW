package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Curatore {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private String cognome;
	
	@Column(nullable = false)
	private LocalDate dataNascita;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String numeroTelefono;
	
	@Column(unique = true, nullable = false)
	private Long matricola;
	
	@ManyToOne
	private Citta luogoDiNascitaCuratore;
	
	@OneToMany(mappedBy = "curatore")
	private List<Collezione> collezioniGestite;
	
	public Curatore() {
		this.collezioniGestite = new ArrayList<>();
	}

	public Curatore(String nome, String cognome, LocalDate dataNascita, Citta luogoDiNascita, String email, String numeroTelefono, 
			Long matricola, List<Collezione> collezioniGestite) {
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.luogoDiNascitaCuratore = luogoDiNascita;
		this.email = email;
		this.numeroTelefono = numeroTelefono;
		this.matricola = matricola;
		this.collezioniGestite = collezioniGestite;
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

	public LocalDate getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}
	
	public Citta getLuogoDiNascitaCuratore() {
		return this.luogoDiNascitaCuratore;
	}

	public void setLuogoDiNascitaCuratore(Citta luogoDiNascitaCuratore) {
		this.luogoDiNascitaCuratore = luogoDiNascitaCuratore;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNumeroTelefono() {
		return this.numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public Long getMatricola() {
		return this.matricola;
	}

	public void setMatricola(Long matricola) {
		this.matricola = matricola;
	}

	public List<Collezione> getCollezioniGestite() {
		return this.collezioniGestite;
	}

	public void setCollezioniGestite(List<Collezione> collezioniGestite) {
		this.collezioniGestite = collezioniGestite;
	}
}
