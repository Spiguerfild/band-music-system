package br.com.music.resources;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.music.entities.Musico;
import br.com.music.services.MusicoService;


@RestController
@RequestMapping(value = "/musicos")
public class MusicoResource {

	@Autowired
	private MusicoService service;
	
	@GetMapping
	public ResponseEntity<List<Musico>> findAll(){
	    List<Musico> list =  service.findAll();
	    
	    return ResponseEntity.ok().body(list);
	}
}
