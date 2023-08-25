package br.com.music.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.dto.BandaDTO;
import br.com.music.entities.Banda;
import br.com.music.repositories.BandaRepository;
import br.com.music.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BandaService {

	@Autowired
	private BandaRepository repository;

	@Transactional(readOnly = true)
	public List<BandaDTO> findAll(){
		List<Banda> lista = repository.findAll();
		return lista.stream().map(x -> new BandaDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public BandaDTO findById(Long id) {
		Optional<Banda> obj = repository.findById(id);
		
		Banda entity = obj.orElseThrow(() -> new ResourceNotFoundException("O registro solicitado não foi localizado."));
		return new BandaDTO(entity);		
	}

	@Transactional
	public BandaDTO insert(BandaDTO dto) {
		Banda entity = new Banda();
		entity.setNome(dto.getNome());
	
		
		entity = repository.save(entity);

		return new BandaDTO(entity);
	}

	@Transactional
	public BandaDTO update(Long id, BandaDTO dto) {
		
		try {
			Banda entity = repository.getReferenceById(id);
			
			entity.setNome(dto.getNome());
	
			
			entity = repository.save(entity);
	
			return new BandaDTO(entity);
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
