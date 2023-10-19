package br.com.music.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.dto.MusicoInstrumentoDTO;
import br.com.music.entities.MusicoInstrumento;
import br.com.music.repositories.MusicoInstrumentoRepository;
import br.com.music.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;


@Service
public class MusicoInstrumentoService {

	@Autowired
	private MusicoInstrumentoRepository repository;

	@Transactional(readOnly = true)
	public List<MusicoInstrumentoDTO> findAll(){
		List<MusicoInstrumento> lista = repository.findAll();
		return lista.stream().map(x -> new MusicoInstrumentoDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public MusicoInstrumentoDTO findById(Long id) {
		Optional<MusicoInstrumento> obj = repository.findById(id);
		
		MusicoInstrumento entity = obj.orElseThrow(() -> new ResourceNotFoundException("O registro solicitado não foi localizado."));
		return new MusicoInstrumentoDTO(entity);		
	}

//	@Transactional
//	public MusicoInstrumentoDTO insert(MusicoInstrumentoDTO dto) {
//		MusicoInstrumento entity = new MusicoInstrumento();
//		
//		entity.setInstrumento(dto.getInstrumento());
//		entity.setMusico(dto.getMusico());
//		
//	
//		
//		entity = repository.save(entity);
//
//		return new MusicoInstrumentoDTO(entity);
//	}
	@Transactional
	public MusicoInstrumentoDTO insert(MusicoInstrumentoDTO dto) {
	    // Verifique se já existe um registro com a mesma combinação de músico e instrumento
	    if (repository.existsByMusicoAndInstrumento(dto.getMusico(), dto.getInstrumento())) {
	        throw new ResourceNotFoundException("Registro já existe, este músico já toca este instrumento.");
	    }

	    MusicoInstrumento entity = new MusicoInstrumento();
	    entity.setInstrumento(dto.getInstrumento());
	    entity.setMusico(dto.getMusico());

	    entity = repository.save(entity);

	    return new MusicoInstrumentoDTO(entity);
	}


	@Transactional
	public MusicoInstrumentoDTO update(Long id, MusicoInstrumentoDTO dto) {
		 // Verifique se já existe um registro com a mesma combinação de músico e instrumento
	    if (repository.existsByMusicoAndInstrumento(dto.getMusico(), dto.getInstrumento())) {
	        throw new ResourceNotFoundException("Registro já existe, este músico já toca este instrumento.");
	    }
	    
		try {
			MusicoInstrumento entity = repository.getReferenceById(id);
			
			entity.setInstrumento(dto.getInstrumento());
			entity.setMusico(dto.getMusico());
			
			entity = repository.save(entity);
	
			return new MusicoInstrumentoDTO(entity);
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
