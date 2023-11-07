package br.com.music.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.music.dto.NoiteDeApresentacaoDTO;
import br.com.music.entities.Banda;
import br.com.music.entities.Musica;
import br.com.music.entities.MusicoInstrumento;
import br.com.music.entities.NoiteDeApresentacao;
import br.com.music.repositories.MusicaRepository;
import br.com.music.repositories.NoiteDeApresentacaoRepository;
import br.com.music.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class NoiteDeApresentacaoService {

	@Autowired
	private NoiteDeApresentacaoRepository repository;

	@Autowired
	private MusicaRepository musicaRepository;

	
	@Transactional(readOnly = true) 
	public List<Musica> findAllMusicasDaNoite(@PathVariable Long noiteId) {
	    NoiteDeApresentacao noite = repository.findById(noiteId)
	            .orElseThrow(() -> new ResourceNotFoundException("Noite de apresentação não encontrada"));

	    Set<Musica> musicasDaNoite = noite.getMusicas();

	    return new ArrayList<>(musicasDaNoite);
	}


	@Transactional
	public void associarMusicaANoite(Long musicaId, Long noiteId) {

		  NoiteDeApresentacao noite = repository.findById(noiteId)
				.orElseThrow(() -> new ResourceNotFoundException("Noite de apresentação não encontrada"));
		Musica musica = musicaRepository.findById(musicaId)
				.orElseThrow(() -> new ResourceNotFoundException("Musica não encontrado"));

		noite.add(musica);
		repository.save(noite);
	}

	@Transactional
	public void deleteMusicaDaNoite(Long musicaId, Long noiteId) {
		
		 NoiteDeApresentacao noite  = repository.findById(noiteId)
				.orElseThrow(() -> new ResourceNotFoundException("Noite de apresentação não encontrada"));
		 Musica musica = musicaRepository.findById(musicaId)
				.orElseThrow(() -> new ResourceNotFoundException("Musica não encontrado"));
		
		noite.del(musica);
		repository.save(noite);
		
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	
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
