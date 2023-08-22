package br.com.music.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.music.dto.BandaDTO;
import br.com.music.services.BandaService;

@RestController
@RequestMapping(value = "/bandas")
public class BandaResource {
	@Autowired
	private BandaService service;

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
}
