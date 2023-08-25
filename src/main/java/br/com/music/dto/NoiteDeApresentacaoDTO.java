package br.com.music.dto;

import java.io.Serializable;
import java.time.LocalDate;

import br.com.music.entities.Banda;
import br.com.music.entities.NoiteDeApresentacao;

public class NoiteDeApresentacaoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private LocalDate data;
	private Banda banda;
	
	
	public NoiteDeApresentacaoDTO() {
		
	}


	public NoiteDeApresentacaoDTO(Long id, LocalDate data, Banda banda) {
		
		this.id = id;
		this.data = data;
		this.banda = banda;
	}
	
	public NoiteDeApresentacaoDTO(NoiteDeApresentacao entity) {
		
		this.id = entity.getId();
		this.data = entity.getData();
		this.banda = entity.getBanda();
		
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
	
	
	
}
