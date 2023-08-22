package br.com.music.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.music.entities.Instrumento;


public interface InstrumentoRepository extends JpaRepository<Instrumento, Long>{

}
