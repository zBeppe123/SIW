package it.uniroma3.siw.spring.museo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
/**
 * entità collezione, una collezione è composta da Long id, String nome, String descrizione, Curatore curatore, 
 * List<Opera> opereEsposte;
 */
@Entity
public class Collezione {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false, length = 2000)
	private String descrizione;
	
	@ManyToOne
	private Curatore curatore;
	
	@OneToMany(mappedBy = "collezione")
	private List<Opera> opereEsposte;
	
	public Collezione() {
		this.opereEsposte = new ArrayList<>();
	}

	public Collezione(String nome, String descrizione, Curatore curatore, List<Opera> opereEsposte) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.curatore = curatore;
		this.opereEsposte = opereEsposte;
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

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Curatore getCuratore() {
		return this.curatore;
	}

	public void setCuratore(Curatore curatore) {
		this.curatore = curatore;
	}

	public List<Opera> getOpereEsposte() {
		return this.opereEsposte;
	}

	public void setOpereEsposte(List<Opera> opereEsposte) {
		this.opereEsposte = opereEsposte;
	}
}
