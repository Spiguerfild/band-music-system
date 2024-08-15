package br.com.music.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import br.com.music.dto.MusicoDTO;
import br.com.music.entities.Musico;
import br.com.music.repositories.MusicoRepository;
import br.com.music.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

class MusicoServiceTests {

    @InjectMocks
    private MusicoService service;

    @Mock
    private MusicoRepository repository;

    private Musico musico;
    private MusicoDTO musicoDTO;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        existingId = 1L;
        nonExistingId = 100L;
        musico = new Musico();
        musico.setId(existingId);
        musico.setNome("Test Musico");

        musicoDTO = new MusicoDTO(musico);

        when(repository.findAll()).thenReturn(Arrays.asList(musico));
        when(repository.findById(existingId)).thenReturn(Optional.of(musico));
        when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
        when(repository.save(any(Musico.class))).thenReturn(musico);
        when(repository.getReferenceById(existingId)).thenReturn(musico);
        when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
        doNothing().when(repository).deleteById(existingId);
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
    }

    @Test
    void findAll_ShouldReturnListOfMusicoDTOs() {
        List<MusicoDTO> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals(musicoDTO.getNome(), result.get(0).getNome());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_ExistingId_ShouldReturnMusicoDTO() {
        MusicoDTO result = service.findById(existingId);

        assertEquals(musicoDTO.getNome(), result.getNome());
        verify(repository, times(1)).findById(existingId);
    }

    @Test
    void findById_NonExistingId_ShouldThrowResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));
        verify(repository, times(1)).findById(nonExistingId);
    }

    @Test
    void insert_ShouldSaveAndReturnMusicoDTO() {
        MusicoDTO result = service.insert(musicoDTO);

        assertEquals(musicoDTO.getNome(), result.getNome());
        verify(repository, times(1)).save(any(Musico.class));
    }

    @Test
    void update_ExistingId_ShouldUpdateAndReturnMusicoDTO() {
        MusicoDTO result = service.update(existingId, musicoDTO);

        assertEquals(musicoDTO.getNome(), result.getNome());
        verify(repository, times(1)).getReferenceById(existingId);
        verify(repository, times(1)).save(musico);
    }

    @Test
    void update_NonExistingId_ShouldThrowResourceNotFoundException() {
        assertThrows(ResourceNotFoundException.class, () -> service.update(nonExistingId, musicoDTO));
        verify(repository, times(1)).getReferenceById(nonExistingId);
    }

    
}