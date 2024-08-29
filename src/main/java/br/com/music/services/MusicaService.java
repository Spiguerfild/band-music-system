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

    /**
     * Retorna uma lista de todas as músicas cadastradas no sistema.
     * 
     * @return Lista de objetos MusicaDTO representando todas as músicas.
     */
    @Transactional(readOnly = true)
    public List<MusicaDTO> findAll() {
        List<Musica> lista = repository.findAll();
        return lista.stream().map(x -> new MusicaDTO(x)).collect(Collectors.toList());
    }

    /**
     * Busca uma música pelo seu ID.
     * 
     * @param id ID da música a ser encontrada.
     * @return Objeto MusicaDTO representando a música encontrada.
     * @throws ResourceNotFoundException se a música não for encontrada.
     */
    @Transactional(readOnly = true)
    public MusicaDTO findById(Long id) {
        Optional<Musica> obj = repository.findById(id);
        Musica entity = obj.orElseThrow(() -> new ResourceNotFoundException("O registro solicitado não foi localizado."));
        return new MusicaDTO(entity);
    }

    /**
     * Insere uma nova música no sistema.
     * 
     * @param dto Objeto MusicaDTO contendo os dados da nova música.
     * @return Objeto MusicaDTO representando a música inserida.
     */
    @Transactional
    public MusicaDTO insert(MusicaDTO dto) {
        Musica entity = new Musica();
        entity.setNome(dto.getNome());
        entity.setAutor(dto.getAutor());

        entity = repository.save(entity);

        return new MusicaDTO(entity);
    }

    /**
     * Atualiza os dados de uma música existente.
     * 
     * @param id  ID da música a ser atualizada.
     * @param dto Objeto MusicaDTO contendo os novos dados da música.
     * @return Objeto MusicaDTO representando a música atualizada.
     * @throws ResourceNotFoundException se a música a ser atualizada não for encontrada.
     */
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

    /**
     * Remove uma música do sistema pelo seu ID.
     * 
     * @param id ID da música a ser removida.
     * @throws ResourceNotFoundException se a música a ser removida não for encontrada.
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
