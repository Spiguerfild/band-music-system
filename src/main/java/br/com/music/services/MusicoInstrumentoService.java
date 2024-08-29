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

    /**
     * Retorna uma lista de todos os músicos e seus instrumentos cadastrados no sistema.
     * 
     * @return Lista de objetos MusicoInstrumentoDTO representando todos os músicos e instrumentos.
     */
    @Transactional(readOnly = true)
    public List<MusicoInstrumentoDTO> findAll() {
        List<MusicoInstrumento> lista = repository.findAll();
        return lista.stream().map(x -> new MusicoInstrumentoDTO(x)).collect(Collectors.toList());
    }

    /**
     * Busca um registro de músico e instrumento pelo seu ID.
     * 
     * @param id ID do registro de músico e instrumento a ser encontrado.
     * @return Objeto MusicoInstrumentoDTO representando o registro encontrado.
     * @throws ResourceNotFoundException se o registro não for encontrado.
     */
    @Transactional(readOnly = true)
    public MusicoInstrumentoDTO findById(Long id) {
        Optional<MusicoInstrumento> obj = repository.findById(id);
        MusicoInstrumento entity = obj.orElseThrow(() -> new ResourceNotFoundException("O registro solicitado não foi localizado."));
        return new MusicoInstrumentoDTO(entity);
    }

    /**
     * Insere um novo registro de músico e instrumento no sistema.
     * 
     * @param dto Objeto MusicoInstrumentoDTO contendo os dados do novo registro.
     * @return Objeto MusicoInstrumentoDTO representando o registro inserido.
     * @throws ResourceNotFoundException se já existir um registro com a mesma combinação de músico e instrumento.
     */
    @Transactional
    public MusicoInstrumentoDTO insert(MusicoInstrumentoDTO dto) {
        // Verifica se já existe um registro com a mesma combinação de músico e instrumento
        if (repository.existsByMusicoAndInstrumento(dto.getMusico(), dto.getInstrumento())) {
            throw new ResourceNotFoundException("Registro já existe, este músico já toca este instrumento.");
        }

        MusicoInstrumento entity = new MusicoInstrumento();
        entity.setInstrumento(dto.getInstrumento());
        entity.setMusico(dto.getMusico());

        entity = repository.save(entity);

        return new MusicoInstrumentoDTO(entity);
    }

    /**
     * Atualiza os dados de um registro de músico e instrumento existente.
     * 
     * @param id  ID do registro a ser atualizado.
     * @param dto Objeto MusicoInstrumentoDTO contendo os novos dados do registro.
     * @return Objeto MusicoInstrumentoDTO representando o registro atualizado.
     * @throws ResourceNotFoundException se o registro a ser atualizado não for encontrado ou se já existir um registro com a mesma combinação de músico e instrumento.
     */
    @Transactional
    public MusicoInstrumentoDTO update(Long id, MusicoInstrumentoDTO dto) {
        // Verifica se já existe um registro com a mesma combinação de músico e instrumento
        if (repository.existsByMusicoAndInstrumento(dto.getMusico(), dto.getInstrumento())) {
            throw new ResourceNotFoundException("Registro já existe, este músico já toca este instrumento.");
        }

        try {
            MusicoInstrumento entity = repository.getReferenceById(id);

            entity.setInstrumento(dto.getInstrumento());
            entity.setMusico(dto.getMusico());

            entity = repository.save(entity);

            return new MusicoInstrumentoDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("O recurso com o ID " + id + " não foi localizado");
        }
    }

    /**
     * Remove um registro de músico e instrumento do sistema pelo seu ID.
     * 
     * @param id ID do registro a ser removido.
     * @throws ResourceNotFoundException se o registro a ser removido não for encontrado.
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
