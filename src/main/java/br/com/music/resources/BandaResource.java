package br.com.music.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import br.com.music.entities.Banda;
import br.com.music.entities.MusicoInstrumento;
import br.com.music.repositories.BandaRepository;
import br.com.music.services.BandaService;
import br.com.music.services.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/bandas")
public class BandaResource {
	
	@Autowired
	private BandaService service;
	

	
//	@GetMapping("/{bandaId}/musicosinstrumentos")
//	public ResponseEntity<List<MusicoInstrumento>> getMusicosDaBanda(@PathVariable Long bandaId) {
//	    Banda banda = repository.findById(bandaId)
//	            .orElseThrow(() -> new ResourceNotFoundException("Banda não encontrada"));
//
//	    Set<MusicoInstrumento> musicosDaBanda = banda.getMusicosInstrumentos();
//
//	    return ResponseEntity.ok(new ArrayList<>(musicosDaBanda));
//	}

	@GetMapping("/{bandaId}/musicosinstrumentos")
	public ResponseEntity<List<MusicoInstrumento>> getMusicosDaBanda(@PathVariable Long bandaId) {
	    List<MusicoInstrumento> list = service.findAllMusicosInstrumentos(bandaId);

	    return ResponseEntity.ok().body(list);
	}

	
	@PostMapping("/{musicoInstrumentoId}/nabanda/{bandaId}")
	public ResponseEntity<?> associarMusicoABanda(@PathVariable Long musicoInstrumentoId, @PathVariable Long bandaId) {
	    service.associarMusicoABanda(musicoInstrumentoId, bandaId);
	    return ResponseEntity.ok("Associação realizada com sucesso");
	}
	
	@DeleteMapping(value = "/musicoinstrumentoNaBanda/{musicoInstrumentoId}/{bandaId}")
	public ResponseEntity<Void> deleteMusicoInstrumentoDaBanda(@PathVariable Long musicoInstrumentoId,@PathVariable Long bandaId) {
		service.deleteMusicoInstrumentoDaBanda(musicoInstrumentoId, bandaId);
		return ResponseEntity.noContent().build();
	}
	
	
	//CRUD----------------------------------------------------

	@GetMapping
	public ResponseEntity<List<BandaDTO>> findAll() {
		List<BandaDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<BandaDTO> findById(@PathVariable Long id) {
		BandaDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<BandaDTO> insert(@RequestBody BandaDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(null);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<BandaDTO> update(@PathVariable Long id, @RequestBody BandaDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//consulta personalizada----------------------------------------------------
	
	@GetMapping(value = "/nomebanda/{nome}")
	public ResponseEntity<BandaDTO> findByBanda(
	@PathVariable String nome){
	BandaDTO dto = service.findByBanda(nome);
	return ResponseEntity.ok().body(dto);
	}
}
