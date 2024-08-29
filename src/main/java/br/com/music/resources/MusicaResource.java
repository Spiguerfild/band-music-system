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

import br.com.music.dto.MusicaDTO;
import br.com.music.services.MusicaService;

/**
 * Controlador REST para gerenciar músicas.
 */
@RestController
@RequestMapping(value = "/musicas")
public class MusicaResource {

    @Autowired
    private MusicaService service;

    /**
     * Recupera todas as músicas.
     * 
     * @return Lista de DTOs representando todas as músicas.
     */
    @GetMapping
    public ResponseEntity<List<MusicaDTO>> findAll() {
        List<MusicaDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    /**
     * Recupera uma música pelo seu ID.
     * 
     * @param id ID da música a ser encontrada.
     * @return DTO da música encontrada.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<MusicaDTO> findById(@PathVariable Long id){
        MusicaDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    /**
     * Cria uma nova música.
     * 
     * @param dto DTO contendo os dados da nova música.
     * @return DTO da nova música criada.
     */
    @PostMapping
    public ResponseEntity<MusicaDTO> insert(@RequestBody MusicaDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(dto.getId())
                    .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    /**
     * Atualiza uma música existente.
     * 
     * @param id  ID da música a ser atualizada.
     * @param dto DTO contendo os novos dados da música.
     * @return DTO da música atualizada.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<MusicaDTO> update(
            @PathVariable Long id,
            @RequestBody MusicaDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    /**
     * Remove uma música pelo seu ID.
     * 
     * @param id ID da música a ser removida.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
