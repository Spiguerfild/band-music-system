package br.com.music.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.dto.MusicaDTO;
import br.com.music.entities.Musica;
import br.com.music.repositories.MusicaRepository;
import br.com.music.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MusicaService {
	@Autowired
	private MusicaRepository repository;

	@Transactional(readOnly = true)
	public List<MusicaDTO> findAll() {
		List<Musica> lista = repository.findAll();
		return lista.stream().map(x -> new MusicaDTO(x)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public MusicaDTO findById(Long id) {
		Optional<Musica> obj = repository.findById(id);

		Musica entity = obj
				.orElseThrow(() -> new ResourceNotFoundException("O registro solicitado não foi localizado."));
		return new MusicaDTO(entity);
	}

	@Transactional
	public MusicaDTO insert(MusicaDTO dto) {
		Musica entity = new Musica();
		entity.setNome(dto.getNome());
		entity.setAutor(dto.getAutor());

		entity = repository.save(entity);

		return new MusicaDTO(entity);
	}

	@Transactional
	public MusicaDTO update(Long id, MusicaDTO dto) {

		try {
			Musica entity = repository.getReferenceById(id);

			entity.setNome(dto.getNome());
			entity.setAutor(dto.getAutor());

			entity = repository.save(entity);

			return new MusicaDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("O recurso com o ID " + id + " não foi localizado");
		}
	}

	public void delete(Long id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
			}
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("O registro solicitado não foi localizado.");
		}
	}
}
