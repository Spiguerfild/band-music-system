package br.com.music.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.dto.MusicoDTO;
import br.com.music.entities.Musico;
import br.com.music.repositories.MusicoRepository;
import br.com.music.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MusicoService {

    @Autowired
    private MusicoRepository repository;

    /**
     * Retorna uma lista de todos os músicos cadastrados no sistema.
     * 
     * @return Lista de objetos MusicoDTO representando todos os músicos.
     */
    @Transactional(readOnly = true)
    public List<MusicoDTO> findAll() {
        List<Musico> lista = repository.findAll();
        return lista.stream().map(x -> new MusicoDTO(x)).collect(Collectors.toList());
    }

    /**
     * Busca um músico pelo seu ID.
     * 
     * @param id ID do músico a ser encontrado.
     * @return Objeto MusicoDTO representando o músico encontrado.
     * @throws ResourceNotFoundException se o músico não for encontrado.
     */
    @Transactional(readOnly = true)
    public MusicoDTO findById(Long id) {
        Optional<Musico> obj = repository.findById(id);
        Musico entity = obj.orElseThrow(() -> new ResourceNotFoundException("O registro solicitado não foi localizado."));
        return new MusicoDTO(entity);
    }

    /**
     * Insere um novo músico no sistema.
     * 
     * @param dto Objeto MusicoDTO contendo os dados do novo músico.
     * @return Objeto MusicoDTO representando o músico inserido.
     */
    @Transactional
    public MusicoDTO insert(MusicoDTO dto) {
        Musico entity = new Musico();
        entity.setNome(dto.getNome());
        entity = repository.save(entity);
        return new MusicoDTO(entity);
    }

    /**
     * Atualiza os dados de um músico existente.
     * 
     * @param id  ID do músico a ser atualizado.
     * @param dto Objeto MusicoDTO contendo os novos dados do músico.
     * @return Objeto MusicoDTO representando o músico atualizado.
     * @throws ResourceNotFoundException se o músico a ser atualizado não for encontrado.
     */
    @Transactional
    public MusicoDTO update(Long id, MusicoDTO dto) {
        try {
            Musico entity = repository.getReferenceById(id);
            entity.setNome(dto.getNome());
            entity = repository.save(entity);
            return new MusicoDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("O recurso com o ID " + id + " não foi localizado");
        }
    }

    /**
     * Remove um músico do sistema pelo seu ID.
     * 
     * @param id ID do músico a ser removido.
     * @throws ResourceNotFoundException se o músico a ser removido não for encontrado.
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
