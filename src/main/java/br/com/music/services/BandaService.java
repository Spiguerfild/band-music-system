package br.com.music.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.dto.BandaDTO;
import br.com.music.entities.Banda;
import br.com.music.repositories.BandaRepository;

@Service
public class BandaService {

	@Autowired
	private BandaRepository repository;
	
	

	@Transactional(readOnly = true)
	public List<BandaDTO> findAll() {
		
		List<Banda> list = repository.findAll();
		return list.stream().map(x -> new BandaDTO(x)).collect(Collectors.toList());
		
	}

	@Transactional(readOnly = true)
	public BandaDTO findById(Long id) {
		
		Optional<Banda> obj = repository.findById(id);
		Banda entity = obj.get();
		return new BandaDTO(entity);
		
	}
}
