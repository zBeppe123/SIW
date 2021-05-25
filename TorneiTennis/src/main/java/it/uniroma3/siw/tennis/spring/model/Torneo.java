package it.uniroma3.siw.tennis.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
/**
 * Classe per l'entità torneo
 * un toreno è arbitrato da un solo arbitro
 * un toreno è composto da più partite
 * ad un torneo possono partecipare un numero massimo di partecipanti
 */
@Entity
public class Torneo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false, unique = true)
	private String nome;
	@Column(nullable = false)
	private Integer numeroMaxDiPartecipanti;
	@Column(nullable = false)
	private String mese;
	@Column(nullable = false)
	private Integer anno;
	@Column(nullable = false)
	private Float premioInDenaro;
	@ManyToOne
	private Arbitro arbitro;
	@ManyToMany
	private List<Tennista> tennistiIscritti;
	@OneToMany(mappedBy="torneo")
	private List<Partita> partite;
	
	public Torneo() {
		tennistiIscritti = new ArrayList<Tennista>();
		partite = new ArrayList<Partita>();
	}

	public Torneo(String nome, Integer numeroMaxDiParteciapnti, String mese, Integer anno, Float premioInDenaro,Arbitro arbitro) {
		this.nome = nome;
		this.numeroMaxDiPartecipanti = numeroMaxDiParteciapnti;
		this.mese = mese;
		this.anno = anno;
		this.premioInDenaro = premioInDenaro;
		this.arbitro=arbitro;
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

	public Integer getNumeroMaxDiPartecipanti() {
		return numeroMaxDiPartecipanti;
	}

	public void setNumeroMaxDiPartecipanti(Integer numeroMaxDiPartecipanti) {
		this.numeroMaxDiPartecipanti = numeroMaxDiPartecipanti;
	}

	public String getMese() {
		return mese;
	}

	public void setMese(String mese) {
		this.mese = mese;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public Float getPremioInDenaro() {
		return premioInDenaro;
	}

	public void setPremioInDenaro(Float premioInDenaro) {
		this.premioInDenaro = premioInDenaro;
	}

	public Arbitro getArbitro() {
		return arbitro;
	}

	public void setArbitro(Arbitro arbitro) {
		this.arbitro = arbitro;
	}

	public List<Tennista> getTennistiIscritti() {
		return tennistiIscritti;
	}

	public void setTennistiIscritti(List<Tennista> tennistiIscritti) {
		this.tennistiIscritti = tennistiIscritti;
	}

	public List<Partita> getPartite() {
		return partite;
	}

	public void setPartite(List<Partita> partite) {
		this.partite = partite;
	}
	public String getDescrizione() {
		return "		premio: "+ this.premioInDenaro + "\n arbitro: " + this.arbitro.getNome() + " " + this.arbitro.getCognome();
				}
}
