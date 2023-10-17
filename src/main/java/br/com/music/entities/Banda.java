package br.com.music.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "tb_banda")
public class Banda implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
//	@ManyToMany(fetch = FetchType.EAGER)
	@ManyToMany
	@JoinTable(name = "tb_musicoinstrumentobanda",
	joinColumns = @JoinColumn(name = "banda_id"),
	inverseJoinColumns = @JoinColumn(name = "musico_instrumento_id"))
	Set<MusicoInstrumento> musicos = new HashSet<>();
	
	public Banda() {
		
	}

	public Banda(Long id, String nome) {
		
		this.id = id;
		this.nome = nome;
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
		Banda other = (Banda) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}
