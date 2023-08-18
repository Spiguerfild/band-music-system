package br.com.music.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.music.dto.MusicoDTO;
import br.com.music.entities.Musico;
import br.com.music.services.MusicoService;

@RestController
@RequestMapping(value = "/musicos")
public class MusicoResource {

	@Autowired
	private MusicoService service;

	@GetMapping
	public ResponseEntity<List<MusicoDTO>> findAll() {
		List<MusicoDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<MusicoDTO> findById(@PathVariable Long id) {
		MusicoDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
}
