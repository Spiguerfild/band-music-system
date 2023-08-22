package br.com.music.dto;

import java.io.Serializable;

import br.com.music.entities.Instrumento;
import br.com.music.entities.Musico;

public class MusicoDaBandaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Musico musico;
	private Instrumento instrumento;
	
	
	public MusicoDaBandaDTO() {
		
	}


	public MusicoDaBandaDTO(Long id, Musico musico, Instrumento instrumento) {
		
		this.id = id;
		this.musico = musico;
		this.instrumento = instrumento;
	}
	
	
	public MusicoDaBandaDTO(MusicoDaBandaDTO entity) {
		this.id = entity.getId();
		this.musico = entity.getMusico();
		this.instrumento = entity.getInstrumento();
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
	
	
	
}
