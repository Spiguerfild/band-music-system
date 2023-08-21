package br.com.music.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.music.dto.MusicaDTO;

import br.com.music.services.MusicaService;

@RestController
@RequestMapping(value = "/musicas")
public class MusicaResource {

	@Autowired
	private MusicaService service;

	@GetMapping
	public ResponseEntity<List<MusicaDTO>> findAll() {
		List<MusicaDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<MusicaDTO> findById(@PathVariable Long id) {
		MusicaDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
}
