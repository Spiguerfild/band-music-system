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

import br.com.music.dto.MusicoInstrumentoDTO;
import br.com.music.services.MusicoInstrumentoService;

/**
 * Controlador REST para gerenciar associações entre músicos e instrumentos.
 */
@RestController
@RequestMapping(value = "/musicosinstrumentos")
public class MusicoInstrumentoResource {
    
    @Autowired
    private MusicoInstrumentoService service;
    
    /**
     * Recupera todas as associações entre músicos e instrumentos.
     * 
     * @return Lista de DTOs representando todas as associações.
     */
    @GetMapping
    public ResponseEntity<List<MusicoInstrumentoDTO>> findAll() {
        List<MusicoInstrumentoDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
    
    /**
     * Recupera uma associação entre músico e instrumento pelo seu ID.
     * 
     * @param id ID da associação a ser encontrada.
     * @return DTO da associação encontrada.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<MusicoInstrumentoDTO> findById(@PathVariable Long id){
        MusicoInstrumentoDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }
    
    /**
     * Cria uma nova associação entre músico e instrumento.
     * 
     * @param dto DTO contendo os dados da nova associação.
     * @return DTO da nova associação criada.
     */
    @PostMapping
    public ResponseEntity<MusicoInstrumentoDTO> insert(@RequestBody MusicoInstrumentoDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(dto.getId())
                    .toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    
    /**
     * Atualiza uma associação entre músico e instrumento existente.
     * 
     * @param id  ID da associação a ser atualizada.
     * @param dto DTO contendo os novos dados da associação.
     * @return DTO da associação atualizada.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<MusicoInstrumentoDTO> update(
            @PathVariable Long id,
            @RequestBody MusicoInstrumentoDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }
    
    /**
     * Remove uma associação entre músico e instrumento pelo seu ID.
     * 
     * @param id ID da associação a ser removida.
     * @return Resposta sem conteúdo.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
