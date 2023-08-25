package br.com.music.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.dto.InstrumentoDTO;
import br.com.music.entities.Instrumento;
import br.com.music.repositories.InstrumentoRepository;
import br.com.music.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;


@Service
public class InstrumentoService {

	@Autowired
	private InstrumentoRepository repository;

	@Transactional(readOnly = true)
	public List<InstrumentoDTO> findAll(){
		List<Instrumento> lista = repository.findAll();
		return lista.stream().map(x -> new InstrumentoDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public InstrumentoDTO findById(Long id) {
		Optional<Instrumento> obj = repository.findById(id);
		
		Instrumento entity = obj.orElseThrow(() -> new ResourceNotFoundException("O registro solicitado não foi localizado."));
		return new InstrumentoDTO(entity);		
	}

	@Transactional
	public InstrumentoDTO insert(InstrumentoDTO dto) {
		Instrumento entity = new Instrumento();
		entity.setNome(dto.getNome());
	
		
		entity = repository.save(entity);

		return new InstrumentoDTO(entity);
	}

	@Transactional
	public InstrumentoDTO update(Long id, InstrumentoDTO dto) {
		
		try {
			Instrumento entity = repository.getReferenceById(id);
			
			entity.setNome(dto.getNome());
	
			
			entity = repository.save(entity);
	
			return new InstrumentoDTO(entity);
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
