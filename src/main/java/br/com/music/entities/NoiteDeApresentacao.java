package br.com.music.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_noitedeapresentacao")
public class NoiteDeApresentacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate data;
	@ManyToOne
	@JoinColumn(name = "id_banda_fk")
	private Banda banda;
	
	@ManyToMany
	@JoinTable(name = "tb_musicasdanoitedeapresentacao",
	joinColumns = @JoinColumn(name = "noite_apresentacao_id"),
	inverseJoinColumns = @JoinColumn(name = "musica_id"))
	Set<Musica> musicas = new HashSet<>();
	
	
	public NoiteDeApresentacao() {
		
		
	}


	public NoiteDeApresentacao(Long id, LocalDate data, Banda banda) {
		
		this.id = id;
		this.data = data;
		this.banda = banda;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public LocalDate getData() {
		return data;
	}


	public void setData(LocalDate data) {
		this.data = data;
	}


	public Banda getBanda() {
		return banda;
	}


	public void setBanda(Banda banda) {
		this.banda = banda;
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
		NoiteDeApresentacao other = (NoiteDeApresentacao) obj;
		return Objects.equals(id, other.id);
	}

	
	
}
