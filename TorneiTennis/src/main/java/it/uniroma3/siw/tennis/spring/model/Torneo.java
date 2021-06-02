package it.uniroma3.siw.tennis.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
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
	private Integer numeroPartecipanti;
	@Column(nullable = false)
	private Integer mese;
	@Column(nullable = false)
	private Integer anno;
	@Column(nullable = false)
	private Float premioInDenaro;
	@ManyToOne
	private Arbitro arbitro;
	@ManyToMany
	@JoinTable(name = "tornei_tennisti")
	private List<Tennista> tennistiIscritti;
	@OneToMany(mappedBy="torneo")
	private List<Partita> partite;
	
	public Torneo() {
		tennistiIscritti = new ArrayList<Tennista>();
		partite = new ArrayList<Partita>();
		this.numeroPartecipanti=0;
	}

	public Torneo(String nome, Integer numeroMaxDiParteciapnti, Integer mese, Integer anno, Float premioInDenaro,Arbitro arbitro) {
		this.nome = nome;
		this.numeroMaxDiPartecipanti = numeroMaxDiParteciapnti;
		this.mese = mese;
		this.anno = anno;
		this.premioInDenaro = premioInDenaro;
		this.arbitro=arbitro;
		this.numeroPartecipanti=0;
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

	//vecchia implementazione per mese
//	public int getMeseValore() {
//		int meseValue = -1;
//		
//		if(this.mese.equals("Gennaio")) {
//			meseValue = 1;
//		}
//		else if(this.mese.equals("Febbraio")) {
//			meseValue = 2;
//		}
//		else if(this.mese.equals("Marzo")) {
//			meseValue = 3;
//		}
//		else if(this.mese.equals("Aprile")) {
//			meseValue = 4;
//		}
//		else if(this.mese.equals("Maggio")) {
//			meseValue = 1;
//		}
//		else if(this.mese.equals("Giugno")) {
//			meseValue = 6;
//		}
//		else if(this.mese.equals("Luglio")) {
//			meseValue = 7;
//		}
//		else if(this.mese.equals("Agosto")) {
//			meseValue = 8;
//		}
//		else if(this.mese.equals("Settembre")) {
//			meseValue = 9;
//		}
//		else if(this.mese.equals("Ottobre")) {
//			meseValue = 10;
//		}
//		else if(this.mese.equals("Novembre")) {
//			meseValue = 11;
//		}
//		else if(this.mese.equals("Dicembre")) {
//			meseValue = 12;
//		}
//		
//		return meseValue;
//	}

	public Integer getMese() {
		return mese;
	}

	public void setMese(Integer mese) {
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

	public Integer getNumeroPartecipanti() {
		return numeroPartecipanti;
	}

	public void setNumeroPartecipanti(Integer numeroPartecipanti) {
		this.numeroPartecipanti = numeroPartecipanti;
	}
	
}
