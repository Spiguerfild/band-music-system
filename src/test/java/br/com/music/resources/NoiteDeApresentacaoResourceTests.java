package br.com.music.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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

import br.com.music.dto.NoiteDeApresentacaoDTO;
import br.com.music.entities.Banda;
import br.com.music.entities.Musica;
import br.com.music.services.NoiteDeApresentacaoService;

@SpringBootTest
@AutoConfigureMockMvc
public class NoiteDeApresentacaoResourceTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private NoiteDeApresentacaoService service;

	private NoiteDeApresentacaoDTO noiteDTO;
	private Musica musica;

	@BeforeEach
	void setUp() {
		Banda banda = new Banda();
		banda.setId(1L);
		banda.setNome("Banda Exemplo");

		noiteDTO = new NoiteDeApresentacaoDTO(1L, LocalDate.of(2024, 8, 15), banda);

		musica = new Musica();
		musica.setId(1L);
		musica.setNome("Música Exemplo");
		musica.setAutor("Autor Exemplo");

		Mockito.when(service.findAll()).thenReturn(Collections.singletonList(noiteDTO));
		Mockito.when(service.findById(1L)).thenReturn(noiteDTO);
		Mockito.when(service.insert(any())).thenReturn(noiteDTO);
		Mockito.when(service.update(eq(1L), any())).thenReturn(noiteDTO);
		Mockito.doNothing().when(service).delete(1L);
		Mockito.when(service.findAllMusicasDaNoite(1L)).thenReturn(Collections.singletonList(musica));
		Mockito.doNothing().when(service).associarMusicaANoite(eq(1L), eq(1L));
		Mockito.doNothing().when(service).deleteMusicaDaNoite(eq(1L), eq(1L));
	}

	@Test
	void testGetMusicasDaNoite() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/noitesdeapresentacao/1/musicas")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].nome").value("Música Exemplo"))
				.andExpect(jsonPath("$[0].autor").value("Autor Exemplo"));
	}

	@Test
	void testGetMusicasDaNoite_NoMusicas() throws Exception {
		Mockito.when(service.findAllMusicasDaNoite(1L)).thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/noitesdeapresentacao/1/musicas")).andExpect(status().isOk())
				.andExpect(jsonPath("$").isEmpty());
	}

	
	@Test
	void testDeleteMusicaDaNoite() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/noitesdeapresentacao/musicaNaNoite/1/1"))
				.andExpect(status().isNoContent());

		Mockito.verify(service).deleteMusicaDaNoite(1L, 1L);
	}

	@Test
	void testFindById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/noitesdeapresentacao/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.banda.id").value(1)).andExpect(jsonPath("$.banda.nome").value("Banda Exemplo"));
	}

	@Test
	void testInsert() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(noiteDTO);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/noitesdeapresentacao").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
	}

	@Test
	void testDelete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/noitesdeapresentacao/1")).andExpect(status().isNoContent());

		Mockito.verify(service).delete(1L);
	}

	@Test
	void testFindAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/noitesdeapresentacao")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].banda.id").value(1));

	}

}
