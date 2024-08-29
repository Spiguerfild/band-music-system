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

import br.com.music.dto.NoiteDeApresentacaoDTO;
import br.com.music.entities.Musica;
import br.com.music.services.NoiteDeApresentacaoService;

/**
 * Controlador REST para gerenciar as noites de apresentação.
 */
@RestController
@RequestMapping(value = "/noitesdeapresentacao")
public class NoiteDeApresentacaoResource {

    @Autowired
    private NoiteDeApresentacaoService service;
    
    /**
     * Recupera todas as músicas associadas a uma noite de apresentação específica.
     * 
     * @param noiteId ID da noite de apresentação.
     * @return Lista de músicas associadas à noite de apresentação.
     */
    @GetMapping("/{noiteId}/musicas")
    public ResponseEntity<List<Musica>> getMusicasDaNoite(@PathVariable Long noiteId) {
        List<Musica> list = service.findAllMusicasDaNoite(noiteId);
        return ResponseEntity.ok().body(list);
    }

    /**
     * Associa uma música a uma noite de apresentação.
     * 
     * @param musicaId ID da música a ser associada.
     * @param noiteId ID da noite de apresentação à qual a música será associada.
     * @return Resposta com uma mensagem de sucesso.
     */
    @PostMapping("/{musicaId}/nanoite/{noiteId}")
    public ResponseEntity<?> associarMusicaANoite(@PathVariable Long musicaId, @PathVariable Long noiteId) {
        service.associarMusicaANoite(musicaId, noiteId);
        return ResponseEntity.ok("Associação realizada com sucesso");
    }
    
    /**
     * Remove uma música de uma noite de apresentação.
     * 
     * @param musicaId ID da música a ser removida.
     * @param noiteId ID da noite de apresentação da qual a música será removida.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping(value = "/musicaNaNoite/{musicaId}/{noiteId}")
    public ResponseEntity<Void> deleteMusicaDaNoite(@PathVariable Long musicaId, @PathVariable Long noiteId) {
        service.deleteMusicaDaNoite(musicaId, noiteId);
        return ResponseEntity.noContent().build();
    }
    
    // CRUD Referente a Noite de apresentação(escala)
    
    /**
     * Recupera todas as noites de apresentação.
     * 
     * @return Lista de DTOs representando todas as noites de apresentação.
     */
    @GetMapping
    public ResponseEntity<List<NoiteDeApresentacaoDTO>> findAll() {
        List<NoiteDeApresentacaoDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
    
    /**
     * Busca uma noite de apresentação pelo seu ID.
     * 
     * @param id ID da noite de apresentação a ser encontrada.
     * @return DTO da noite de apresentação encontrada.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<NoiteDeApresentacaoDTO> findById(@PathVariable Long id){
        NoiteDeApresentacaoDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }
    
    /**
     * Cria uma nova noite de apresentação.
     * 
     * @param dto DTO contendo os dados da nova noite de apresentação.
     * @return DTO da nova noite de apresentação criada.
     */
    @PostMapping
    public ResponseEntity<NoiteDeApresentacaoDTO> insert(@RequestBody NoiteDeApresentacaoDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(dto.getId())
                    .toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    
    /**
     * Atualiza uma noite de apresentação existente.
     * 
     * @param id  ID da noite de apresentação a ser atualizada.
     * @param dto DTO contendo os novos dados da noite de apresentação.
     * @return DTO da noite de apresentação atualizada.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<NoiteDeApresentacaoDTO> update(
            @PathVariable Long id,
            @RequestBody NoiteDeApresentacaoDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
    
    /**
     * Remove uma noite de apresentação pelo seu ID.
     * 
     * @param id ID da noite de apresentação a ser removida.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
