package br.com.music.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.dto.MusicaDTO;

import br.com.music.entities.Musica;

import br.com.music.repositories.MusicaRepository;


@Service
public class MusicaService {

	@Autowired
	private MusicaRepository repository;
	
	

	@Transactional(readOnly = true)
	public List<MusicaDTO> findAll() {
		
		List<Musica> list = repository.findAll();
		return list.stream().map(x -> new MusicaDTO(x)).collect(Collectors.toList());
		
	}

	@Transactional(readOnly = true)
	public MusicaDTO findById(Long id) {
		
		Optional<Musica> obj = repository.findById(id);
		Musica entity = obj.get();
		return new MusicaDTO(entity);
		
	}
}
