package br.com.music.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.dto.MusicoDaBandaDTO;
import br.com.music.entities.MusicoDaBanda;
import br.com.music.repositories.MusicoDaBandaRepository;
import br.com.music.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;


@Service
public class MusicoDaBandaService {

	@Autowired
	private MusicoDaBandaRepository repository;

	@Transactional(readOnly = true)
	public List<MusicoDaBandaDTO> findAll(){
		List<MusicoDaBanda> lista = repository.findAll();
		return lista.stream().map(x -> new MusicoDaBandaDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public MusicoDaBandaDTO findById(Long id) {
		Optional<MusicoDaBanda> obj = repository.findById(id);
		
		MusicoDaBanda entity = obj.orElseThrow(() -> new ResourceNotFoundException("O registro solicitado não foi localizado."));
		return new MusicoDaBandaDTO(entity);		
	}

	@Transactional
	public MusicoDaBandaDTO insert(MusicoDaBandaDTO dto) {
		MusicoDaBanda entity = new MusicoDaBanda();
		
		entity.setInstrumento(dto.getInstrumento());
		entity.setMusico(dto.getMusico());
		
	
		
		entity = repository.save(entity);

		return new MusicoDaBandaDTO(entity);
	}

	@Transactional
	public MusicoDaBandaDTO update(Long id, MusicoDaBandaDTO dto) {
		
		try {
			MusicoDaBanda entity = repository.getReferenceById(id);
			
			entity.setInstrumento(dto.getInstrumento());
			entity.setMusico(dto.getMusico());
			
			entity = repository.save(entity);
	
			return new MusicoDaBandaDTO(entity);
		} catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException(
					"O recurso com o ID "+id+" não foi localizado");
		}
	}

	public void delete(Long id) {
		try {
		if(repository.existsById(id)) {
			repository.deleteById(id);
		}
		}catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("O registro solicitado não foi localizado.");
		}
	}
}
