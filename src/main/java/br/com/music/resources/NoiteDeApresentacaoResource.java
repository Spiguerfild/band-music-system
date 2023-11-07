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
import br.com.music.entities.MusicoInstrumento;
import br.com.music.services.NoiteDeApresentacaoService;

@RestController
@RequestMapping(value = "/noitesdeapresentacao")
public class NoiteDeApresentacaoResource {

	@Autowired
	private NoiteDeApresentacaoService service;
	
	
	
	@GetMapping("/{noiteId}/musicas")
	public ResponseEntity<List<Musica>> getMusicasDaNoite(@PathVariable Long noiteId) {
	    List<Musica> list = service.findAllMusicasDaNoite(noiteId);

	    return ResponseEntity.ok().body(list);
	}

	
	@PostMapping("/{musicaId}/nanoite/{noiteId}")
	public ResponseEntity<?> associarMusicaANoite(@PathVariable Long musicaId, @PathVariable Long noiteId) {
	    service.associarMusicaANoite(musicaId, noiteId);
	    return ResponseEntity.ok("Associação realizada com sucesso");
	}
	
	@DeleteMapping(value = "/musicaNaNoite/{musicaId}/{noiteId}")
	public ResponseEntity<Void> deleteMusicaDaNoite(@PathVariable Long musicaId,@PathVariable Long noiteId) {
		service.deleteMusicaDaNoite(musicaId, noiteId);
		return ResponseEntity.noContent().build();
	}
	////////////////////////////////////////////////////////////////////////////////////
	
	@GetMapping
	public ResponseEntity<List<NoiteDeApresentacaoDTO>> findAll() {
		List<NoiteDeApresentacaoDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<NoiteDeApresentacaoDTO> findById(@PathVariable Long id){
		NoiteDeApresentacaoDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<NoiteDeApresentacaoDTO> insert(@RequestBody NoiteDeApresentacaoDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(dto.getId())
					.toUri();
		return ResponseEntity.created(uri).body(null);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<NoiteDeApresentacaoDTO> update(
			@PathVariable Long id,
			@RequestBody NoiteDeApresentacaoDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
