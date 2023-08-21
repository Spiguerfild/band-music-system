package br.com.music.dto;

import java.io.Serializable;

import br.com.music.entities.Musica;


public class MusicaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String autor;
	
	
	public MusicaDTO() {
		
	}


	public MusicaDTO(Long id, String nome, String autor) {
		
		this.id = id;
		this.nome = nome;
		this.autor = autor;
	}

	public MusicaDTO(Musica entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
		this.autor = entity.getAutor();
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


	public String getAutor() {
		return autor;
	}


	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	
	
}
