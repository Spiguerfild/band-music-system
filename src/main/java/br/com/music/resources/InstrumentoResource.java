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

import br.com.music.dto.InstrumentoDTO;
import br.com.music.services.InstrumentoService;

/**
 * Controlador REST para gerenciar instrumentos.
 */
@RestController
@RequestMapping(value = "/instrumentos")
public class InstrumentoResource {
	
	@Autowired
	private InstrumentoService service;
	
	/**
	 * Recupera todos os instrumentos.
	 * 
	 * @return Lista de DTOs representando todos os instrumentos.
	 */
	@GetMapping
	public ResponseEntity<List<InstrumentoDTO>> findAll() {
		List<InstrumentoDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	/**
	 * Recupera um instrumento pelo seu ID.
	 * 
	 * @param id ID do instrumento a ser encontrado.
	 * @return DTO do instrumento encontrado.
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<InstrumentoDTO> findById(@PathVariable Long id){
		InstrumentoDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	/**
	 * Cria um novo instrumento.
	 * 
	 * @param dto DTO contendo os dados do novo instrumento.
	 * @return DTO do novo instrumento criado.
	 */
	@PostMapping
	public ResponseEntity<InstrumentoDTO> insert(@RequestBody InstrumentoDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(dto.getId())
					.toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	/**
	 * Atualiza um instrumento existente.
	 * 
	 * @param id  ID do instrumento a ser atualizado.
	 * @param dto DTO contendo os novos dados do instrumento.
	 * @return DTO do instrumento atualizado.
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<InstrumentoDTO> update(
			@PathVariable Long id,
			@RequestBody InstrumentoDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	/**
	 * Remove um instrumento pelo seu ID.
	 * 
	 * @param id ID do instrumento a ser removido.
	 * @return Resposta sem conteúdo.
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
