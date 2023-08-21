package br.com.music.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.music.entities.Banda;

@Repository
public interface BandaRepository extends JpaRepository<Banda, Long>{

}
