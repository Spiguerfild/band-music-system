package br.com.music.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.dto.MusicoDaBandaDTO;

import br.com.music.entities.MusicoDaBanda;

import br.com.music.repositories.MusicoDaBandaRepository;


@Service
public class MusicoDaBandaService {

	@Autowired
	private MusicoDaBandaRepository repository;
	
	

	@Transactional(readOnly = true)
	public List<MusicoDaBandaDTO> findAll() {
		
		List<MusicoDaBanda> list = repository.findAll();
		return list.stream().map(x -> new MusicoDaBandaDTO(x)).collect(Collectors.toList());
		
	}

	@Transactional(readOnly = true)
	public MusicoDaBandaDTO findById(Long id) {
		
		Optional<MusicoDaBanda> obj = repository.findById(id);
		MusicoDaBanda entity = obj.get();
		return new MusicoDaBandaDTO(entity);
		
	}
}
