package br.com.music.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.music.dto.BandaDTO;
import br.com.music.entities.Instrumento;
import br.com.music.entities.MusicoInstrumento;
import br.com.music.services.BandaService;

@SpringBootTest
@AutoConfigureMockMvc
public class BandaResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BandaService service;

    private BandaDTO bandaDTO;
    private MusicoInstrumento musicoInstrumento;

    @BeforeEach
    void setUp() {
        bandaDTO = new BandaDTO();
        bandaDTO.setId(1L);
        bandaDTO.setNome("Banda Teste");

        // Criando um objeto Instrumento e associando-o a MusicoInstrumento
        Instrumento instrumento = new Instrumento(1L, "Guitarra");

        musicoInstrumento = new MusicoInstrumento();
        musicoInstrumento.setId(1L);
        musicoInstrumento.setInstrumento(instrumento); // Associando o instrumento

        Mockito.when(service.findAll()).thenReturn(Collections.singletonList(bandaDTO));
        Mockito.when(service.findById(1L)).thenReturn(bandaDTO);
        Mockito.when(service.insert(any())).thenReturn(bandaDTO);
        Mockito.when(service.update(eq(1L), any())).thenReturn(bandaDTO);
        Mockito.doNothing().when(service).delete(1L);
        Mockito.when(service.findByBanda("Banda Teste")).thenReturn(bandaDTO);
        Mockito.when(service.findAllMusicosInstrumentos(1L)).thenReturn(Collections.singletonList(musicoInstrumento));
    }

    @Test
    void testFindAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bandas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Banda Teste"));
    }

    @Test
    void testFindById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bandas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Banda Teste"));
    }

    @Test
    void testInsert() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(bandaDTO);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/bandas")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
    }

    @Test
    void testUpdate() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(bandaDTO);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/bandas/1")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
              .andExpect(jsonPath("$.nome").value("Banda Teste"));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/bandas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testFindByBanda() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bandas/nomebanda/Banda Teste"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Banda Teste"));
    }

    @Test
    void testGetMusicosDaBanda() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bandas/1/musicosinstrumentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].instrumento.nome").value("Guitarra"));
    }

    @Test
    void testAssociarMusicoABanda() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/bandas/1/nabanda/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Associação realizada com sucesso"));
    }

    @Test
    void testDeleteMusicoInstrumentoDaBanda() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/bandas/musicoinstrumentoNaBanda/1/1"))
                .andExpect(status().isNoContent());
    }
}
