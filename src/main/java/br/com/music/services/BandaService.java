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

import br.com.music.dto.BandaDTO;
import br.com.music.entities.Banda;
import br.com.music.entities.MusicoInstrumento;
import br.com.music.repositories.BandaRepository;
import br.com.music.repositories.MusicoInstrumentoRepository;
import br.com.music.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BandaService {

    @Autowired
    private BandaRepository repository;

    @Autowired
    private MusicoInstrumentoRepository musicoInstrumentoRepository;
    
    /**
     * Recupera todos os músicos e instrumentos associados a uma banda específica.
     * 
     * @param bandaId ID da banda para a qual os músicos e instrumentos devem ser recuperados.
     * @return Lista de objetos MusicoInstrumento associados à banda.
     * @throws ResourceNotFoundException se a banda não for encontrada.
     */
    @Transactional(readOnly = true)
    public List<MusicoInstrumento> findAllMusicosInstrumentos(@PathVariable Long bandaId) {
        Banda banda = repository.findById(bandaId)
                .orElseThrow(() -> new ResourceNotFoundException("Banda não encontrada"));

        Set<MusicoInstrumento> musicosDaBanda = banda.getMusicosInstrumentos();

        return new ArrayList<>(musicosDaBanda);
    }

    /**
     * Associa um músico e instrumento a uma banda existente.
     * 
     * @param musicoInstrumentoId ID do músico e instrumento a ser associado.
     * @param bandaId ID da banda à qual o músico e instrumento devem ser associados.
     * @throws ResourceNotFoundException se a banda ou o músico e instrumento não forem encontrados.
     */
    @Transactional
    public void associarMusicoABanda(Long musicoInstrumentoId, Long bandaId) {
        Banda banda = repository.findById(bandaId)
                .orElseThrow(() -> new ResourceNotFoundException("Banda não encontrada"));
        MusicoInstrumento musicoInstrumento = musicoInstrumentoRepository.findById(musicoInstrumentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Musico-Instrumento não encontrado"));

        banda.add(musicoInstrumento);
        repository.save(banda);
    }

    /**
     * Remove a associação de um músico e instrumento de uma banda.
     * 
     * @param musicoInstrumentoId ID do músico e instrumento a ser removido.
     * @param bandaId ID da banda da qual o músico e instrumento devem ser removidos.
     * @throws ResourceNotFoundException se a banda ou o músico e instrumento não forem encontrados.
     */
    @Transactional
    public void deleteMusicoInstrumentoDaBanda(Long musicoInstrumentoId, Long bandaId) {
        Banda banda = repository.findById(bandaId)
                .orElseThrow(() -> new ResourceNotFoundException("Banda não encontrada"));
        MusicoInstrumento musicoInstrumento = musicoInstrumentoRepository.findById(musicoInstrumentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Musico-Instrumento não encontrado"));

        banda.del(musicoInstrumento);
        repository.save(banda);
    }

    // CREATE | READ | UPDATE | DELETE
    // ---------------------------------------------------------
    
    /**
     * Recupera todas as bandas cadastradas no sistema.
     * 
     * @return Lista de objetos BandaDTO representando todas as bandas.
     */
    @Transactional(readOnly = true)
    public List<BandaDTO> findAll() {
        List<Banda> lista = repository.findAll();
        return lista.stream().map(x -> new BandaDTO(x)).collect(Collectors.toList());
    }

    /**
     * Busca uma banda pelo seu ID.
     * 
     * @param id ID da banda a ser encontrada.
     * @return Objeto BandaDTO representando a banda encontrada.
     * @throws ResourceNotFoundException se a banda não for encontrada.
     */
    @Transactional(readOnly = true)
    public BandaDTO findById(Long id) {
        Optional<Banda> obj = repository.findById(id);

        Banda entity = obj
                .orElseThrow(() -> new ResourceNotFoundException("O registro solicitado não foi localizado."));
        return new BandaDTO(entity);
    }

    /**
     * Insere uma nova banda no sistema.
     * 
     * @param dto Objeto BandaDTO contendo os dados da nova banda.
     * @return Objeto BandaDTO representando a banda inserida.
     */
    @Transactional
    public BandaDTO insert(BandaDTO dto) {
        Banda entity = new Banda();
        entity.setNome(dto.getNome());

        entity = repository.save(entity);

        return new BandaDTO(entity);
    }

    /**
     * Atualiza os dados de uma banda existente.
     * 
     * @param id  ID da banda a ser atualizada.
     * @param dto Objeto BandaDTO contendo os novos dados da banda.
     * @return Objeto BandaDTO representando a banda atualizada.
     * @throws ResourceNotFoundException se a banda a ser atualizada não for encontrada.
     */
    @Transactional
    public BandaDTO update(Long id, BandaDTO dto) {
        try {
            Banda entity = repository.getReferenceById(id);

            entity.setNome(dto.getNome());

            entity = repository.save(entity);

            return new BandaDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("O recurso com o ID " + id + " não foi localizado");
        }
    }

    /**
     * Remove uma banda do sistema pelo seu ID.
     * 
     * @param id ID da banda a ser removida.
     * @throws ResourceNotFoundException se a banda a ser removida não for encontrada.
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

    // Consultas personalizadas
    // ---------------------------------------------------------

    /**
     * Busca uma banda pelo seu nome.
     * 
     * @param nome Nome da banda a ser buscada.
     * @return Objeto BandaDTO representando a banda encontrada.
     */
    @Transactional(readOnly = true)
    public BandaDTO findByBanda(String nome) {
        Banda obj = repository.findByNome(nome);
        return new BandaDTO(obj);
    }
}
