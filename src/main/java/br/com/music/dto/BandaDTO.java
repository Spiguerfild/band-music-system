package br.com.music.dto;

import java.io.Serializable;

import br.com.music.entities.Banda;



public class BandaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Long id;
	private String nome;
	
	
	public BandaDTO() {
		
	}


	public BandaDTO(Long id, String nome) {
		
		this.id = id;
		this.nome = nome;
	}
	
	
	public BandaDTO(Banda entity) {
		this.id = entity.getId();
		this.nome = entity.getNome();
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
	
	
	
}
