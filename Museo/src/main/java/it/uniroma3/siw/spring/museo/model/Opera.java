package it.uniroma3.siw.spring.museo.model;

import javax.persistence.*;

@Entity
public class Opera {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String titolo;
	
	private Integer anno;
	
	@Column(length = 5000)
	private String descrizione;
	
	@Column(nullable=false)
	private String img;
	
	@ManyToOne
	private Artista artista;
	
	@ManyToOne
	private Collezione collezione;
	
	public Opera() {}
	
	public Opera(String titolo, Integer anno, String descrizione, String img, Artista artista, Collezione collezione) {
		this.titolo = titolo;
		this.anno = anno;
		this.descrizione = descrizione;
		this.img=img;
		this.artista = artista;
		this.collezione = collezione;
	}
	
	public Opera(String titolo, String img, Artista artista, Collezione collezione) {
		this(titolo, null, null, img, artista, collezione);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return this.titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Integer getAnno() {
		return this.anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public Artista getArtista() {
		return this.artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	public Collezione getCollezione() {
		return this.collezione;
	}

	public void setCollezione(Collezione collezione) {
		this.collezione = collezione;
	}
	
	
}
