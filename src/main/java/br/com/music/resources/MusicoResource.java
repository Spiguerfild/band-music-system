package br.com.music.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.music.entities.Musico;


@RestController
@RequestMapping(value = "/musico")
public class MusicoResource {

	
	@GetMapping
	public ResponseEntity<List<Musico>> findAll(){
	    List<Musico> list = new ArrayList<>();
	    list.add(new Musico(1L, "Luis H."));
	    return ResponseEntity.ok().body(list);
	}
}
