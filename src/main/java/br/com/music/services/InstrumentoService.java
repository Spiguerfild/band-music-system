package br.com.music.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.dto.InstrumentoDTO;

import br.com.music.entities.Instrumento;

import br.com.music.repositories.InstrumentoRepository;


@Service
public class InstrumentoService {

	@Autowired
	private InstrumentoRepository repository;
	
	

	@Transactional(readOnly = true)
	public List<InstrumentoDTO> findAll() {
		
		List<Instrumento> list = repository.findAll();
		return list.stream().map(x -> new InstrumentoDTO(x)).collect(Collectors.toList());
		
	}

	@Transactional(readOnly = true)
	public InstrumentoDTO findById(Long id) {
		
		Optional<Instrumento> obj = repository.findById(id);
		Instrumento entity = obj.get();
		return new InstrumentoDTO(entity);
		
	}
}
