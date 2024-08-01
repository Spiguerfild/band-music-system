package br.com.music.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.music.dto.InstrumentoDTO;
import br.com.music.entities.Instrumento;
import br.com.music.repositories.InstrumentoRepository;
import br.com.music.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;

public class InstrumentoServiceTests {

    @InjectMocks
    private InstrumentoService instrumentoService;

    @Mock
    private InstrumentoRepository instrumentoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Instrumento> lista = new ArrayList<>();
        lista.add(new Instrumento());
        lista.add(new Instrumento());

        when(instrumentoRepository.findAll()).thenReturn(lista);

        List<InstrumentoDTO> result = instrumentoService.findAll();

        assertNotNull(result);
        verify(instrumentoRepository).findAll();
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Instrumento instrumento = new Instrumento();
        instrumento.setNome("Guitarra");

        when(instrumentoRepository.findById(id)).thenReturn(Optional.of(instrumento));

        InstrumentoDTO result = instrumentoService.findById(id);

        assertNotNull(result);
        verify(instrumentoRepository).findById(id);
    }

    @Test
    public void testFindByIdNotFound() {
        Long id = 1L;

        when(instrumentoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            instrumentoService.findById(id);
        });
    }

    @Test
    public void testInsert() {
        InstrumentoDTO dto = new InstrumentoDTO();
        dto.setNome("Guitarra");

        Instrumento instrumento = new Instrumento();
        instrumento.setNome(dto.getNome());

        when(instrumentoRepository.save(instrumento)).thenReturn(instrumento);

        InstrumentoDTO result = instrumentoService.insert(dto);

        assertNotNull(result);
        verify(instrumentoRepository).save(instrumento);
    }

    @Test
    public void testUpdate() {
        Long id = 1L;
        InstrumentoDTO dto = new InstrumentoDTO();
        dto.setNome("Violino");

        Instrumento instrumento = new Instrumento();
        instrumento.setNome("Guitarra");

        when(instrumentoRepository.getReferenceById(id)).thenReturn(instrumento);
        when(instrumentoRepository.save(instrumento)).thenReturn(instrumento);

        InstrumentoDTO result = instrumentoService.update(id, dto);

        assertNotNull(result);
        verify(instrumentoRepository).getReferenceById(id);
        verify(instrumentoRepository).save(instrumento);
    }

    @Test
    public void testUpdateNotFound() {
        Long id = 1L;
        InstrumentoDTO dto = new InstrumentoDTO();
        dto.setNome("Violino");

        when(instrumentoRepository.getReferenceById(id)).thenThrow(new EntityNotFoundException());

        assertThrows(ResourceNotFoundException.class, () -> {
            instrumentoService.update(id, dto);
        });
    }

    @Test
    public void testDelete() {
        Long id = 1L;

        when(instrumentoRepository.existsById(id)).thenReturn(true);

        instrumentoService.delete(id);

        verify(instrumentoRepository).deleteById(id);
    }

   
    

}
