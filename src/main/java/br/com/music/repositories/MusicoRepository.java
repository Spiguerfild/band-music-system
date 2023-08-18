package br.com.music.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.music.entities.Musico;

@Repository
public interface MusicoRepository extends JpaRepository<Musico, Long> {

}
