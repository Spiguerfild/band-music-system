package br.com.music.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.music.dto.NoiteDeApresentacaoDTO;
import br.com.music.services.NoiteDeApresentacaoService;

@RestController
@RequestMapping(value = "/noitesdeapresentacao")
public class NoiteDeApresentacaoResource {

	@Autowired
	private NoiteDeApresentacaoService service;

	@GetMapping
	public ResponseEntity<List<NoiteDeApresentacaoDTO>> findAll() {
		List<NoiteDeApresentacaoDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<NoiteDeApresentacaoDTO> findById(@PathVariable Long id) {
		NoiteDeApresentacaoDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
}
