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

	/**
	 * Retorna a lista de músicas associadas a uma noite de apresentação.
	 * 
	 * @param noiteId ID da noite de apresentação.
	 * @return Lista de músicas associadas à noite.
	 */
	@Transactional(readOnly = true)
	public List<Musica> findAllMusicasDaNoite(@PathVariable Long noiteId) {
		NoiteDeApresentacao noite = repository.findById(noiteId)
				.orElseThrow(() -> new ResourceNotFoundException("Noite de apresentação nao encontrada"));

		Set<Musica> musicasDaNoite = noite.getMusicas();

		return new ArrayList<>(musicasDaNoite);
	}

	/**
	 * Associa uma musica a uma noite de apresentação.
	 * 
	 * @param musicaId ID da música.
	 * @param noiteId  ID da noite de apresentação.
	 */
	@Transactional
	public void associarMusicaANoite(Long musicaId, Long noiteId) {
		NoiteDeApresentacao noite = repository.findById(noiteId)
				.orElseThrow(() -> new ResourceNotFoundException("Noite de apresentação não encontrada"));
		Musica musica = musicaRepository.findById(musicaId)
				.orElseThrow(() -> new ResourceNotFoundException("Música não encontrada"));

		noite.add(musica);
		repository.save(noite);
	}

	/**
	 * Remove uma música de uma noite de apresentação.
	 * 
	 * @param musicaId ID da musica.
	 * @param noiteId  ID da noite de apresentação.
	 */
	@Transactional
	public void deleteMusicaDaNoite(Long musicaId, Long noiteId) {
		NoiteDeApresentacao noite = repository.findById(noiteId)
				.orElseThrow(() -> new ResourceNotFoundException("Noite de apresentação não encontrada"));
		Musica musica = musicaRepository.findById(musicaId)
				.orElseThrow(() -> new ResourceNotFoundException("Música não encontrada"));

		noite.del(musica);
		repository.save(noite);
	}

	/**
	 * Retorna todas as noites de apresentação.
	 * 
	 * @return Lista de DTOs de noites de apresentação.
	 */
	@Transactional(readOnly = true)
	public List<NoiteDeApresentacaoDTO> findAll() {
		List<NoiteDeApresentacao> lista = repository.findAll();
		return lista.stream().map(x -> new NoiteDeApresentacaoDTO(x)).collect(Collectors.toList());
	}

	/**
	 * Retorna uma noite de apresentação pelo ID.
	 * 
	 * @param id ID da noite de apresentação.
	 * @return DTO da noite de apresentação.
	 */
	@Transactional(readOnly = true)
	public NoiteDeApresentacaoDTO findById(Long id) {
		Optional<NoiteDeApresentacao> obj = repository.findById(id);

		NoiteDeApresentacao entity = obj
				.orElseThrow(() -> new ResourceNotFoundException("O registro solicitado não foi localizado."));
		return new NoiteDeApresentacaoDTO(entity);
	}

	/**
	 * Insere uma nova noite de apresentação.
	 * 
	 * @param dto DTO contendo os dados da nova noite de apresentação.
	 * @return DTO da noite de apresentação inserida.
	 */
	@Transactional
	public NoiteDeApresentacaoDTO insert(NoiteDeApresentacaoDTO dto) {
		NoiteDeApresentacao entity = new NoiteDeApresentacao();
		entity.setBanda(dto.getBanda());
		entity.setData(dto.getData());

		entity = repository.save(entity);

		return new NoiteDeApresentacaoDTO(entity);
	}

	/**
	 * Atualiza uma noite de apresentação existente.
	 * 
	 * @param id  ID da noite de apresentação a ser atualizada.
	 * @param dto DTO contendo os novos dados.
	 * @return DTO da noite de apresentação atualizada.
	 */
	@Transactional
	public NoiteDeApresentacaoDTO update(Long id, NoiteDeApresentacaoDTO dto) {
		try {
			NoiteDeApresentacao entity = repository.getReferenceById(id);

			entity.setBanda(dto.getBanda());
			entity.setData(dto.getData());

			entity = repository.save(entity);

			return new NoiteDeApresentacaoDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("O recurso com o ID " + id + " não foi localizado");
		}
	}

	/**
	 * Remove uma noite de apresentação pelo ID.
	 * 
	 * @param id ID da noite de apresentação a ser removida.
	 */
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
