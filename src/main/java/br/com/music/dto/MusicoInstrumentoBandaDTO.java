package br.com.music.dto;

import java.io.Serializable;

import br.com.music.entities.Banda;
import br.com.music.entities.MusicoInstrumento;

public class MusicoInstrumentoBandaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Long id;
	private Banda banda;
	private MusicoInstrumento musicoInstrumento;
	
	
	public MusicoInstrumentoBandaDTO() {
		
	}


	public MusicoInstrumentoBandaDTO(Long id, Banda banda, MusicoInstrumento musicoInstrumento) {
		
		this.id = id;
		this.banda = banda;
		this.musicoInstrumento = musicoInstrumento;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Banda getBanda() {
		return banda;
	}


	public void setBanda(Banda banda) {
		this.banda = banda;
	}


	public MusicoInstrumento getMusicoInstrumento() {
		return musicoInstrumento;
	}


	public void setMusicoInstrumento(MusicoInstrumento musicoInstrumento) {
		this.musicoInstrumento = musicoInstrumento;
	}
	
	
	
	

}
