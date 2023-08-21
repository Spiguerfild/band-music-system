package br.com.music.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.music.dto.InstrumentoDTO;
import br.com.music.services.InstrumentoService;

@RestController
@RequestMapping(value = "/instrumentos")
public class InstrumentoResource {
	@Autowired
	private InstrumentoService service;

	@GetMapping
	public ResponseEntity<List<InstrumentoDTO>> findAll() {
		List<InstrumentoDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<InstrumentoDTO> findById(@PathVariable Long id) {
		InstrumentoDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
}
