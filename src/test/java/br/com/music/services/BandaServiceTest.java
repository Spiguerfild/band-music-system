package br.com.music.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.music.dto.BandaDTO;
import br.com.music.entities.Banda;
import br.com.music.entities.MusicoInstrumento;
import br.com.music.repositories.BandaRepository;
import br.com.music.repositories.MusicoInstrumentoRepository;

public class BandaServiceTest {

    @InjectMocks
    private BandaService bandaService;

    @Mock
    private BandaRepository bandaRepository;

    @Mock
    private MusicoInstrumentoRepository musicoInstrumentoRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllMusicosInstrumentos() {
        Long bandaId = 1L;
        Banda banda = mock(Banda.class);
        Set<MusicoInstrumento> musicosInstrumentos = new HashSet<>(); // Usar um conjunto real ao invés de um mock

        when(bandaRepository.findById(bandaId)).thenReturn(Optional.of(banda));
        when(banda.getMusicosInstrumentos()).thenReturn(musicosInstrumentos);

        List<MusicoInstrumento> result = bandaService.findAllMusicosInstrumentos(bandaId);

        assertNotNull(result);
        verify(bandaRepository).findById(bandaId);
        verify(banda).getMusicosInstrumentos();
    }

    @Test
    public void testAssociarMusicoABanda() {
        Long bandaId = 1L;
        Long musicoInstrumentoId = 1L;
        Banda banda = mock(Banda.class);
        MusicoInstrumento musicoInstrumento = mock(MusicoInstrumento.class);

        when(bandaRepository.findById(bandaId)).thenReturn(Optional.of(banda));
        when(musicoInstrumentoRepository.findById(musicoInstrumentoId)).thenReturn(Optional.of(musicoInstrumento));

        bandaService.associarMusicoABanda(musicoInstrumentoId, bandaId);

        verify(banda).add(musicoInstrumento);
        verify(bandaRepository).save(banda);
    }

    @Test
    public void testDeleteMusicoInstrumentoDaBanda() {
        Long bandaId = 1L;
        Long musicoInstrumentoId = 1L;
        Banda banda = mock(Banda.class);
        MusicoInstrumento musicoInstrumento = mock(MusicoInstrumento.class);

        when(bandaRepository.findById(bandaId)).thenReturn(Optional.of(banda));
        when(musicoInstrumentoRepository.findById(musicoInstrumentoId)).thenReturn(Optional.of(musicoInstrumento));

        bandaService.deleteMusicoInstrumentoDaBanda(musicoInstrumentoId, bandaId);

        verify(banda).del(musicoInstrumento);
        verify(bandaRepository).save(banda);
    }

    @Test
    public void testFindAll() {
        List<Banda> bandas = new ArrayList<>();
        when(bandaRepository.findAll()).thenReturn(bandas);

        List<BandaDTO> result = bandaService.findAll();

        assertNotNull(result);
        verify(bandaRepository).findAll();
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Banda banda = mock(Banda.class);
        when(bandaRepository.findById(id)).thenReturn(Optional.of(banda));

        BandaDTO result = bandaService.findById(id);

        assertNotNull(result);
        verify(bandaRepository).findById(id);
    }

    @Test
    public void testInsert() {
        BandaDTO dto = new BandaDTO();
        dto.setNome("Banda Teste");
        Banda banda = new Banda();
        banda.setNome(dto.getNome());

        when(bandaRepository.save(any(Banda.class))).thenReturn(banda);

        BandaDTO result = bandaService.insert(dto);

        assertNotNull(result);
        assertEquals(dto.getNome(), result.getNome());
        verify(bandaRepository).save(any(Banda.class));
    }

    @Test
    public void testUpdate() {
        Long id = 1L;
        BandaDTO dto = new BandaDTO();
        dto.setNome("Banda Atualizada");
        Banda banda = new Banda();
        banda.setNome(dto.getNome());

        when(bandaRepository.getReferenceById(id)).thenReturn(banda);
        when(bandaRepository.save(any(Banda.class))).thenReturn(banda);

        BandaDTO result = bandaService.update(id, dto);

        assertNotNull(result);
        assertEquals(dto.getNome(), result.getNome());
        verify(bandaRepository).getReferenceById(id);
        verify(bandaRepository).save(any(Banda.class));
    }

    @Test
    public void testDelete() {
        Long id = 1L;

        when(bandaRepository.existsById(id)).thenReturn(true);
        doNothing().when(bandaRepository).deleteById(id);

        bandaService.delete(id);

        verify(bandaRepository).deleteById(id);
    }

    @Test
    public void testFindByBanda() {
        String nome = "Banda Teste";
        Banda banda = mock(Banda.class);

        when(bandaRepository.findByNome(nome)).thenReturn(banda);

        BandaDTO result = bandaService.findByBanda(nome);

        assertNotNull(result);
        verify(bandaRepository).findByNome(nome);
    }
}
