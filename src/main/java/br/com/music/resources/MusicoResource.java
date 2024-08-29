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

import br.com.music.dto.MusicoDTO;
import br.com.music.services.MusicoService;

/**
 * Controlador REST para gerenciar músicos.
 */
@RestController
@RequestMapping(value = "/musicos")
public class MusicoResource {

    @Autowired
    private MusicoService service;
    
    /**
     * Recupera todos os músicos.
     * 
     * @return Lista de DTOs representando todos os músicos.
     */
    @GetMapping
    public ResponseEntity<List<MusicoDTO>> findAll() {
        List<MusicoDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
    
    /**
     * Recupera um músico pelo seu ID.
     * 
     * @param id ID do músico a ser encontrado.
     * @return DTO do músico encontrado.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<MusicoDTO> findById(@PathVariable Long id){
        MusicoDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }
    
    /**
     * Cria um novo músico.
     * 
     * @param dto DTO contendo os dados do novo músico.
     * @return DTO do novo músico criado.
     */
    @PostMapping
    public ResponseEntity<MusicoDTO> insert(@RequestBody MusicoDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(dto.getId())
                    .toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    
    /**
     * Atualiza um músico existente.
     * 
     * @param id  ID do músico a ser atualizado.
     * @param dto DTO contendo os novos dados do músico.
     * @return DTO do músico atualizado.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<MusicoDTO> update(
            @PathVariable Long id,
            @RequestBody MusicoDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
    
    /**
     * Remove um músico pelo seu ID.
     * 
     * @param id ID do músico a ser removido.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
