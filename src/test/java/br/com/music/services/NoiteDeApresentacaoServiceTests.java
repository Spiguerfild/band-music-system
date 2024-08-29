package br.com.music.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.music.dto.NoiteDeApresentacaoDTO;
import br.com.music.entities.Banda;
import br.com.music.entities.Musica;
import br.com.music.entities.NoiteDeApresentacao;
import br.com.music.repositories.MusicaRepository;
import br.com.music.repositories.NoiteDeApresentacaoRepository;
import br.com.music.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class NoiteDeApresentacaoServiceTests {

    @Autowired
    private NoiteDeApresentacaoService service;

    @Autowired
    private NoiteDeApresentacaoRepository repository;

    @Autowired
    private MusicaRepository musicaRepository;

    private NoiteDeApresentacao noiteDeApresentacao;
    private Banda banda;
    private Musica musica;

    @BeforeEach
    void setUp() {
        banda = new Banda();
        banda.setNome("Banda Teste");

        musica = new Musica();
        musica.setNome("Musica Teste");

        noiteDeApresentacao = new NoiteDeApresentacao();
        noiteDeApresentacao.setBanda(banda);
        noiteDeApresentacao.setData(LocalDate.now());

        repository.save(noiteDeApresentacao);
        musicaRepository.save(musica);
    }

    @Test
    void testFindById() {
        NoiteDeApresentacaoDTO dto = service.findById(noiteDeApresentacao.getId());
        assertNotNull(dto);
        assertEquals(noiteDeApresentacao.getId(), dto.getId());
    }

    @Test
    void testInsert() {
        NoiteDeApresentacaoDTO dto = new NoiteDeApresentacaoDTO();
        dto.setBanda(banda);
        dto.setData(LocalDate.now().plusDays(1));

        NoiteDeApresentacaoDTO result = service.insert(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    void testUpdate() {
        NoiteDeApresentacaoDTO dto = new NoiteDeApresentacaoDTO();
        dto.setBanda(banda);
        dto.setData(LocalDate.now().plusDays(1));

        NoiteDeApresentacaoDTO result = service.update(noiteDeApresentacao.getId(), dto);

        assertNotNull(result);
        assertEquals(LocalDate.now().plusDays(1), result.getData());
    }

    @Test
    void testAssociarMusicaANoite() {
        service.associarMusicaANoite(musica.getId(), noiteDeApresentacao.getId());
        NoiteDeApresentacao noite = repository.findById(noiteDeApresentacao.getId()).orElseThrow();

        assertTrue(noite.getMusicas().contains(musica));
    }

    @Test
    void testDeleteMusicaDaNoite() {
        service.associarMusicaANoite(musica.getId(), noiteDeApresentacao.getId());
        service.deleteMusicaDaNoite(musica.getId(), noiteDeApresentacao.getId());
        NoiteDeApresentacao noite = repository.findById(noiteDeApresentacao.getId()).orElseThrow();

        assertFalse(noite.getMusicas().contains(musica));
    }

    @Test
    void testFindAllMusicasDaNoite() {
        service.associarMusicaANoite(musica.getId(), noiteDeApresentacao.getId());
        List<Musica> musicas = service.findAllMusicasDaNoite(noiteDeApresentacao.getId());

        assertNotNull(musicas);
        assertEquals(1, musicas.size());
        assertTrue(musicas.contains(musica));
    }

    @Test
    void testAssociarMusicaANoite_MusicaNaoEncontrada() {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.associarMusicaANoite(musica.getId() + 1, noiteDeApresentacao.getId());
        });
    }

    @Test
    void testAssociarMusicaANoite_NoiteNaoEncontrada() {
        assertThrows(ResourceNotFoundException.class, () -> {
            service.associarMusicaANoite(musica.getId(), noiteDeApresentacao.getId() + 1);
        });
    }

   
}
