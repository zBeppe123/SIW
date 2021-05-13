package it.uniroma3.siw.torneiTennis.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Arbitro {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private String cognome;
	@Column(nullable = false)
	private String telefono;
	@Column(nullable = false)
	private String nazionalita;
	@OneToMany(mappedBy="arbitro")
	private List<Torneo> tornei;
	
	public Arbitro() {
		this.tornei= new ArrayList<Torneo>();
	}
	
	public Arbitro(String nome, String cognome, String telefono, String nazionalita) {
		this.nome = nome;
		this.cognome = cognome;
		this.telefono = telefono;
		this.nazionalita = nazionalita;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNumeroTelefono() {
		return telefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.telefono = numeroTelefono;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public List<Torneo> getTornei() {
		return tornei;
	}

	public void setTornei(List<Torneo> tornei) {
		this.tornei = tornei;
	}

}
