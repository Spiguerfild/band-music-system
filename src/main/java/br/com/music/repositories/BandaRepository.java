package br.com.music.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.music.entities.Banda;


public interface BandaRepository extends JpaRepository<Banda, Long>{

}
