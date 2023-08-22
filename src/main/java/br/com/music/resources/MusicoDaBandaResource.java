package br.com.music.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.music.dto.MusicoDaBandaDTO;
import br.com.music.services.MusicoDaBandaService;

@RestController
@RequestMapping(value = "/musicosdasbandas")
public class MusicoDaBandaResource {
	
	@Autowired
	private MusicoDaBandaService service;

	@GetMapping
	public ResponseEntity<List<MusicoDaBandaDTO>> findAll() {
		List<MusicoDaBandaDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<MusicoDaBandaDTO> findById(@PathVariable Long id) {
		MusicoDaBandaDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
}
