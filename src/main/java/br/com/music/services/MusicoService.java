package br.com.music.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.dto.MusicoDTO;
import br.com.music.entities.Musico;
import br.com.music.repositories.MusicoRepository;

@Service
public class MusicoService {
	
	@Autowired
	private MusicoRepository repository;
	
	

	@Transactional(readOnly = true)
	public List<MusicoDTO> findAll() {
		
		List<Musico> list = repository.findAll();
		return list.stream().map(x -> new MusicoDTO(x)).collect(Collectors.toList());
		
	}

	@Transactional(readOnly = true)
	public MusicoDTO findById(Long id) {
		
		Optional<Musico> obj = repository.findById(id);
		Musico entity = obj.get();
		return new MusicoDTO(entity);
		
	}
	
}
