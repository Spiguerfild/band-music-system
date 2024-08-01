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

import br.com.music.dto.MusicoInstrumentoDTO;
import br.com.music.entities.Instrumento; // Importe a classe Instrumento
import br.com.music.entities.Musico;
import br.com.music.entities.MusicoInstrumento;
import br.com.music.repositories.MusicoInstrumentoRepository;
import br.com.music.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

class MusicoInstrumentoServiceTests {

    @InjectMocks
    private MusicoInstrumentoService service;

    @Mock
    private MusicoInstrumentoRepository repository;

    @Mock
    private Musico musico; // Mock para Musico

    @Mock
    private Instrumento instrumento; // Mock para Instrumento

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Instrumento instrumento = new Instrumento();
        instrumento.setNome("Guitarra");

        Musico musico = new Musico();
        musico.setNome("João");

        MusicoInstrumento musicoInstrumento = new MusicoInstrumento();
        musicoInstrumento.setInstrumento(instrumento);
        musicoInstrumento.setMusico(musico);

        List<MusicoInstrumento> list = List.of(musicoInstrumento);
        when(repository.findAll()).thenReturn(list);

        List<MusicoInstrumentoDTO> result = service.findAll();
        assertFalse(result.isEmpty());
        assertEquals("Guitarra", result.get(0).getInstrumento().getNome());
        assertEquals("João", result.get(0).getMusico().getNome());
    }

    @Test
    void testFindByIdWhenExists() {
        Instrumento instrumento = new Instrumento();
        instrumento.setNome("Piano");

        Musico musico = new Musico();
        musico.setNome("Maria");

        MusicoInstrumento musicoInstrumento = new MusicoInstrumento();
        musicoInstrumento.setInstrumento(instrumento);
        musicoInstrumento.setMusico(musico);

        when(repository.findById(anyLong())).thenReturn(Optional.of(musicoInstrumento));

        MusicoInstrumentoDTO result = service.findById(1L);
        assertNotNull(result);
        assertEquals("Piano", result.getInstrumento().getNome());
        assertEquals("Maria", result.getMusico().getNome());
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
    void testInsertWhenNotExists() {
        Instrumento instrumento = new Instrumento();
        instrumento.setNome("Bateria");

        Musico musico = new Musico();
        musico.setNome("Carlos");

        MusicoInstrumentoDTO dto = new MusicoInstrumentoDTO();
        dto.setInstrumento(instrumento);
        dto.setMusico(musico);

        when(repository.existsByMusicoAndInstrumento(dto.getMusico(), dto.getInstrumento())).thenReturn(false);

        MusicoInstrumento musicoInstrumento = new MusicoInstrumento();
        musicoInstrumento.setInstrumento(instrumento);
        musicoInstrumento.setMusico(musico);

        when(repository.save(any(MusicoInstrumento.class))).thenReturn(musicoInstrumento);

        MusicoInstrumentoDTO result = service.insert(dto);
        assertNotNull(result);
        assertEquals("Bateria", result.getInstrumento().getNome());
        assertEquals("Carlos", result.getMusico().getNome());
    }

    @Test
    void testInsertWhenExists() {
        Instrumento instrumento = new Instrumento();
        instrumento.setNome("Viola");

        Musico musico = new Musico();
        musico.setNome("Ana");

        MusicoInstrumentoDTO dto = new MusicoInstrumentoDTO();
        dto.setInstrumento(instrumento);
        dto.setMusico(musico);

        when(repository.existsByMusicoAndInstrumento(dto.getMusico(), dto.getInstrumento())).thenReturn(true);

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            service.insert(dto);
        });

        assertEquals("Registro já existe, este músico já toca este instrumento.", thrown.getMessage());
    }

    @Test
    void testUpdateWhenExists() {
        Instrumento instrumento = new Instrumento();
        instrumento.setNome("Violino");

        Musico musico = new Musico();
        musico.setNome("Pedro");

        MusicoInstrumentoDTO dto = new MusicoInstrumentoDTO();
        dto.setInstrumento(instrumento);
        dto.setMusico(musico);

        MusicoInstrumento musicoInstrumento = new MusicoInstrumento();
        musicoInstrumento.setInstrumento(new Instrumento()); // Use um objeto Instrumento diferente
        musicoInstrumento.setMusico(new Musico()); // Use um objeto Musico diferente

        when(repository.existsByMusicoAndInstrumento(dto.getMusico(), dto.getInstrumento())).thenReturn(false);
        when(repository.getReferenceById(anyLong())).thenReturn(musicoInstrumento);
        when(repository.save(any(MusicoInstrumento.class))).thenReturn(musicoInstrumento);

        MusicoInstrumentoDTO result = service.update(1L, dto);
        assertNotNull(result);
        assertEquals("Violino", result.getInstrumento().getNome());
        assertEquals("Pedro", result.getMusico().getNome());
    }

    @Test
    void testUpdateWhenNotExists() {
        Instrumento instrumento = new Instrumento();
        instrumento.setNome("Teclado");

        Musico musico = new Musico();
        musico.setNome("Laura");

        MusicoInstrumentoDTO dto = new MusicoInstrumentoDTO();
        dto.setInstrumento(instrumento);
        dto.setMusico(musico);

        when(repository.existsByMusicoAndInstrumento(dto.getMusico(), dto.getInstrumento())).thenReturn(false);
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
