package br.com.music.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.music.entities.Instrumento;
import br.com.music.entities.Musico;
import br.com.music.entities.MusicoInstrumento;

public interface MusicoInstrumentoRepository extends JpaRepository<MusicoInstrumento, Long>{
	 boolean existsByMusicoAndInstrumento(Musico musico, Instrumento instrumento);
}
