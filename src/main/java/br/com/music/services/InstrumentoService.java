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

    /**
     * Retorna uma lista de todos os instrumentos cadastrados no sistema.
     * 
     * @return Lista de objetos InstrumentoDTO representando todos os instrumentos.
     */
    @Transactional(readOnly = true)
    public List<InstrumentoDTO> findAll() {
        List<Instrumento> lista = repository.findAll();
        return lista.stream().map(x -> new InstrumentoDTO(x)).collect(Collectors.toList());
    }

    /**
     * Busca um instrumento pelo seu ID.
     * 
     * @param id ID do instrumento a ser encontrado.
     * @return Objeto InstrumentoDTO representando o instrumento encontrado.
     * @throws ResourceNotFoundException se o instrumento não for encontrado.
     */
    @Transactional(readOnly = true)
    public InstrumentoDTO findById(Long id) {
        Optional<Instrumento> obj = repository.findById(id);
        Instrumento entity = obj.orElseThrow(() -> new ResourceNotFoundException("O registro solicitado não foi localizado."));
        return new InstrumentoDTO(entity);
    }

    /**
     * Insere um novo instrumento no sistema.
     * 
     * @param dto Objeto InstrumentoDTO contendo os dados do novo instrumento.
     * @return Objeto InstrumentoDTO representando o instrumento inserido.
     */
    @Transactional
    public InstrumentoDTO insert(InstrumentoDTO dto) {
        Instrumento entity = new Instrumento();
        entity.setNome(dto.getNome());
        entity = repository.save(entity);
        return new InstrumentoDTO(entity);
    }

    /**
     * Atualiza os dados de um instrumento existente.
     * 
     * @param id  ID do instrumento a ser atualizado.
     * @param dto Objeto InstrumentoDTO contendo os novos dados do instrumento.
     * @return Objeto InstrumentoDTO representando o instrumento atualizado.
     * @throws ResourceNotFoundException se o instrumento a ser atualizado não for encontrado.
     */
    @Transactional
    public InstrumentoDTO update(Long id, InstrumentoDTO dto) {
        try {
            Instrumento entity = repository.getReferenceById(id);
            entity.setNome(dto.getNome());
            entity = repository.save(entity);
            return new InstrumentoDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("O recurso com o ID " + id + " não foi localizado");
        }
    }

    /**
     * Remove um instrumento do sistema pelo seu ID.
     * 
     * @param id ID do instrumento a ser removido.
     * @throws ResourceNotFoundException se o instrumento a ser removido não for encontrado.
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
