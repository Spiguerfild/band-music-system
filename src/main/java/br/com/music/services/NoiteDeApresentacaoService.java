package br.com.music.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.dto.NoiteDeApresentacaoDTO;
import br.com.music.entities.NoiteDeApresentacao;
import br.com.music.repositories.NoiteDeApresentacaoRepository;

@Service
public class NoiteDeApresentacaoService {

	@Autowired
	private NoiteDeApresentacaoRepository repository;
	
	

	@Transactional(readOnly = true)
	public List<NoiteDeApresentacaoDTO> findAll() {
		
		List<NoiteDeApresentacao> list = repository.findAll();
		return list.stream().map(x -> new NoiteDeApresentacaoDTO(x)).collect(Collectors.toList());
		
	}

	@Transactional(readOnly = true)
	public NoiteDeApresentacaoDTO findById(Long id) {
		
		Optional<NoiteDeApresentacao> obj = repository.findById(id);
		NoiteDeApresentacao entity = obj.get();
		return new NoiteDeApresentacaoDTO(entity);
		
	}
}
