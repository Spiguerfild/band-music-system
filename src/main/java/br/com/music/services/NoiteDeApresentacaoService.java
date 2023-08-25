package br.com.music.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.dto.NoiteDeApresentacaoDTO;
import br.com.music.entities.NoiteDeApresentacao;
import br.com.music.repositories.NoiteDeApresentacaoRepository;
import br.com.music.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class NoiteDeApresentacaoService {

	@Autowired
	private NoiteDeApresentacaoRepository repository;

	@Transactional(readOnly = true)
	public List<NoiteDeApresentacaoDTO> findAll(){
		List<NoiteDeApresentacao> lista = repository.findAll();
		return lista.stream().map(x -> new NoiteDeApresentacaoDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public NoiteDeApresentacaoDTO findById(Long id) {
		Optional<NoiteDeApresentacao> obj = repository.findById(id);
		
		NoiteDeApresentacao entity = obj.orElseThrow(() -> new ResourceNotFoundException("O registro solicitado não foi localizado."));
		return new NoiteDeApresentacaoDTO(entity);		
	}

	@Transactional
	public NoiteDeApresentacaoDTO insert(NoiteDeApresentacaoDTO dto) {
		NoiteDeApresentacao entity = new NoiteDeApresentacao();
		entity.setBanda(dto.getBanda());
		entity.setData(dto.getData());
		
		entity = repository.save(entity);

		return new NoiteDeApresentacaoDTO(entity);
	}

	@Transactional
	public NoiteDeApresentacaoDTO update(Long id, NoiteDeApresentacaoDTO dto) {
		
		try {
			NoiteDeApresentacao entity = repository.getReferenceById(id);
			
			entity.setBanda(dto.getBanda());
			entity.setData(dto.getData());
	
			
			entity = repository.save(entity);
	
			return new NoiteDeApresentacaoDTO(entity);
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
