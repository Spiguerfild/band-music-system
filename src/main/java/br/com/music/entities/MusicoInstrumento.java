package br.com.music.entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_musicoinstrumento")
public class MusicoInstrumento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "id_musico_fk")
	private Musico musico;
	@ManyToOne
	@JoinColumn(name = "id_instrumento_fk")
	private Instrumento instrumento;
	
	@ManyToMany(mappedBy = "musicosInstrumentos")
	private Set<Banda> bandas;

	public MusicoInstrumento() {
		
	}

	public MusicoInstrumento(Long id, Musico musico, Instrumento instrumento) {

		this.id = id;
		this.musico = musico;
		this.instrumento = instrumento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Musico getMusico() {
		return musico;
	}

	public void setMusico(Musico musico) {
		this.musico = musico;
	}

	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MusicoInstrumento other = (MusicoInstrumento) obj;
		return Objects.equals(id, other.id);
	}

	
	
}
