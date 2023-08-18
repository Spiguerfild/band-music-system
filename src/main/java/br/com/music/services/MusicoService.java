package br.com.music.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.entities.Musico;
import br.com.music.repositories.MusicoRepository;

@Service
public class MusicoService {
	
	@Autowired
	private MusicoRepository repository;

	@Transactional(readOnly = true)
	public List<Musico> findAll() {
		return repository.findAll();
	}

}
