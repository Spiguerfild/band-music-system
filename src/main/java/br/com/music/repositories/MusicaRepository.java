package br.com.music.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.music.entities.Musica;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

}
