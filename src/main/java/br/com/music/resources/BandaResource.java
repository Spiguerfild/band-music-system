package br.com.music.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.music.dto.BandaDTO;
import br.com.music.entities.MusicoInstrumento;
import br.com.music.services.BandaService;

/**
 * Controlador REST para gerenciar bandas e suas associações com músicos e instrumentos.
 */
@RestController
@RequestMapping(value = "/bandas")
public class BandaResource {

    @Autowired
    private BandaService service;

    /**
     * Recupera todos os músicos e instrumentos associados a uma banda.
     * 
     * @param bandaId ID da banda.
     * @return Lista de músicos e instrumentos associados à banda.
     */
    @GetMapping("/{bandaId}/musicosinstrumentos")
    public ResponseEntity<List<MusicoInstrumento>> getMusicosDaBanda(@PathVariable Long bandaId) {
        List<MusicoInstrumento> list = service.findAllMusicosInstrumentos(bandaId);
        return ResponseEntity.ok().body(list);
    }

    /**
     * Associa um músico e instrumento a uma banda.
     * 
     * @param musicoInstrumentoId ID do músico e instrumento.
     * @param bandaId ID da banda.
     * @return Mensagem de sucesso.
     */
    @PostMapping("/{musicoInstrumentoId}/nabanda/{bandaId}")
    public ResponseEntity<?> associarMusicoABanda(@PathVariable Long musicoInstrumentoId, @PathVariable Long bandaId) {
        service.associarMusicoABanda(musicoInstrumentoId, bandaId);
        return ResponseEntity.ok("Associação realizada com sucesso");
    }

    /**
     * Remove a associação de um músico e instrumento de uma banda.
     * 
     * @param musicoInstrumentoId ID do músico e instrumento.
     * @param bandaId ID da banda.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping(value = "/musicoinstrumentoNaBanda/{musicoInstrumentoId}/{bandaId}")
    public ResponseEntity<Void> deleteMusicoInstrumentoDaBanda(@PathVariable Long musicoInstrumentoId, @PathVariable Long bandaId) {
        service.deleteMusicoInstrumentoDaBanda(musicoInstrumentoId, bandaId);
        return ResponseEntity.noContent().build();
    }

    // CRUD Operations ----------------------------------------------------

    /**
     * Recupera todas as bandas.
     * 
     * @return Lista de DTOs representando todas as bandas.
     */
    @GetMapping
    public ResponseEntity<List<BandaDTO>> findAll() {
        List<BandaDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    /**
     * Recupera uma banda pelo seu ID.
     * 
     * @param id ID da banda a ser encontrada.
     * @return DTO da banda encontrada.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<BandaDTO> findById(@PathVariable Long id) {
        BandaDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    /**
     * Cria uma nova banda.
     * 
     * @param dto DTO contendo os dados da nova banda.
     * @return DTO da nova banda criada.
     */
    @PostMapping
    public ResponseEntity<BandaDTO> insert(@RequestBody BandaDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(null);
    }

    /**
     * Atualiza uma banda existente.
     * 
     * @param id  ID da banda a ser atualizada.
     * @param dto DTO contendo os novos dados da banda.
     * @return DTO da banda atualizada.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<BandaDTO> update(@PathVariable Long id, @RequestBody BandaDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    /**
     * Remove uma banda pelo seu ID.
     * 
     * @param id ID da banda a ser removida.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Custom Query ----------------------------------------------------

    /**
     * Recupera uma banda pelo nome.
     * 
     * @param nome Nome da banda.
     * @return DTO da banda encontrada.
     */
    @GetMapping(value = "/nomebanda/{nome}")
    public ResponseEntity<BandaDTO> findByBanda(@PathVariable String nome) {
        BandaDTO dto = service.findByBanda(nome);
        return ResponseEntity.ok().body(dto);
    }
}
