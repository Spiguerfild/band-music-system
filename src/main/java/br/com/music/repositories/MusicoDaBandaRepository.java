package br.com.music.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.music.entities.MusicoDaBanda;

public interface MusicoDaBandaRepository extends JpaRepository<MusicoDaBanda, Long>{

}
