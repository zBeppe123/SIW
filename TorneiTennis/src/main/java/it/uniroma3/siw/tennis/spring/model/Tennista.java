package it.uniroma3.siw.tennis.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
/**
 * Classe per l'entità tennista
 * un tennista può iscriversi a più toreni e può aver partecipato a più partite di tornei diversi
 */
@Entity
public class Tennista {
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
	
	@Column(nullable = false)
	private String email;
	
	@OneToMany(mappedBy = "tennista1")
	private List<Partita> partiteGiocateT1;
	
	@OneToMany(mappedBy = "tennista2")
	private List<Partita> partiteGiocateT2;
	
	@ManyToMany(mappedBy = "tennistiIscritti")
	private List<Torneo> torneiIscritti;
	
	public Tennista() {
		this.partiteGiocateT1 = new ArrayList<>();
		this.partiteGiocateT2 = new ArrayList<>();
		this.torneiIscritti = new ArrayList<>();
	}

	public Tennista(String nome, String cognome, String telefono, String nazionalita, String email,
			List<Partita> partiteGiocateT1, List<Partita> partiteGiocateT2, List<Torneo> torneiIscritti) {
		this.nome = nome;
		this.cognome = cognome;
		this.telefono = telefono;
		this.nazionalita = nazionalita;
		this.email = email;
		this.partiteGiocateT1 = partiteGiocateT1;
		this.partiteGiocateT2 = partiteGiocateT2;
		this.torneiIscritti = torneiIscritti;
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

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNazionalita() {
		return this.nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Partita> getPartiteGiocateT1() {
		return this.partiteGiocateT1;
	}

	public void setPartiteGiocateT1(List<Partita> partiteGiocateT1) {
		this.partiteGiocateT1 = partiteGiocateT1;
	}
	
	public List<Partita> getPartiteGiocateT2() {
		return this.partiteGiocateT2;
	}

	public void setPartiteGiocate(List<Partita> partiteGiocateT2) {
		this.partiteGiocateT2 = partiteGiocateT2;
	}

	public List<Torneo> getTorneiIscritti() {
		return this.torneiIscritti;
	}

	public void setTorneiIscritti(List<Torneo> torneiIscritti) {
		this.torneiIscritti = torneiIscritti;
	}
}
