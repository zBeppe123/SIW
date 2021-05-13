package it.uniroma3.siw.torneiTennis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Partita {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private Integer punteggioG1;
	
	@Column(nullable = false)
	private Integer punteggioG2;
	
	@ManyToOne
	private Torneo torneo;
	
	@ManyToOne
	private Tennista tennista1;
	
	@ManyToOne
	private Tennista tennista2;
	
	public Partita() {}
	
	public Partita(Integer punteggioG1, Integer punteggioG2, Torneo torneo, Tennista tennista1, Tennista tennista2) {
		this.punteggioG1 = punteggioG1;
		this.punteggioG2 = punteggioG2;
		this.torneo = torneo;
		this.tennista1 = tennista1;
		this.tennista2 = tennista2;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPunteggioG1() {
		return this.punteggioG1;
	}

	public void setPunteggioG1(Integer punteggioG1) {
		this.punteggioG1 = punteggioG1;
	}

	public Integer getPunteggioG2() {
		return this.punteggioG2;
	}

	public void setPunteggioG2(Integer punteggioG2) {
		this.punteggioG2 = punteggioG2;
	}

	public Torneo getTorneo() {
		return this.torneo;
	}

	public void setTorneo(Torneo torneo) {
		this.torneo = torneo;
	}

	public Tennista getTennista1() {
		return this.tennista1;
	}

	public void setTennista1(Tennista tennista1) {
		this.tennista1 = tennista1;
	}

	public Tennista getTennista2() {
		return this.tennista2;
	}

	public void setTennista2(Tennista tennista2) {
		this.tennista2 = tennista2;
	}
}
