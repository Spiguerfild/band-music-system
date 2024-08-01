package br.com.music.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import br.com.music.dto.MusicaDTO;
import br.com.music.entities.Musica;
import br.com.music.repositories.MusicaRepository;
import br.com.music.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

class MusicaServiceTests {

    @InjectMocks
    private MusicaService service;

    @Mock
    private MusicaRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Musica musica = new Musica();
        musica.setNome("Nome");
        musica.setAutor("Autor");

        List<Musica> list = List.of(musica);
        when(repository.findAll()).thenReturn(list);

        List<MusicaDTO> result = service.findAll();
        assertFalse(result.isEmpty());
        assertEquals("Nome", result.get(0).getNome());
    }

    @Test
    void testFindByIdWhenExists() {
        Musica musica = new Musica();
        musica.setNome("Nome");
        musica.setAutor("Autor");

        when(repository.findById(anyLong())).thenReturn(Optional.of(musica));

        MusicaDTO result = service.findById(1L);
        assertNotNull(result);
        assertEquals("Nome", result.getNome());
    }

    @Test
    void testFindByIdWhenNotExists() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(1L);
        });

        assertEquals("O registro solicitado não foi localizado.", thrown.getMessage());
    }

    @Test
    void testInsert() {
        MusicaDTO dto = new MusicaDTO();
        dto.setNome("Nome");
        dto.setAutor("Autor");

        Musica musica = new Musica();
        musica.setNome("Nome");
        musica.setAutor("Autor");

        when(repository.save(any(Musica.class))).thenReturn(musica);

        MusicaDTO result = service.insert(dto);
        assertNotNull(result);
        assertEquals("Nome", result.getNome());
    }

    @Test
    void testUpdateWhenExists() {
        MusicaDTO dto = new MusicaDTO();
        dto.setNome("Nome");
        dto.setAutor("Autor");

        Musica musica = new Musica();
        musica.setNome("Nome Antigo");
        musica.setAutor("Autor Antigo");

        when(repository.getReferenceById(anyLong())).thenReturn(musica);
        when(repository.save(any(Musica.class))).thenReturn(musica);

        MusicaDTO result = service.update(1L, dto);
        assertNotNull(result);
        assertEquals("Nome", result.getNome());
    }

    @Test
    void testUpdateWhenNotExists() {
        MusicaDTO dto = new MusicaDTO();
        dto.setNome("Nome");
        dto.setAutor("Autor");

        when(repository.getReferenceById(anyLong())).thenThrow(EntityNotFoundException.class);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            service.update(1L, dto);
        });

        assertEquals("O recurso com o ID 1 não foi localizado", thrown.getMessage());
    }

    @Test
    void testDeleteWhenExists() {
        when(repository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteWhenNotExists() {
        when(repository.existsById(anyLong())).thenReturn(false);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository, times(0)).deleteById(anyLong());
    }

    @Test
    void testDeleteWhenException() {
        when(repository.existsById(anyLong())).thenReturn(true);
        doThrow(new EmptyResultDataAccessException(1)).when(repository).deleteById(anyLong());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(1L);
        });

        assertEquals("O registro solicitado não foi localizado.", thrown.getMessage());
    }
}
