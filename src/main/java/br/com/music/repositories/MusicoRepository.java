package br.com.music.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.music.entities.Musico;


public interface MusicoRepository extends JpaRepository<Musico, Long> {

}
