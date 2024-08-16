package br.com.music.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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

import br.com.music.dto.MusicaDTO;
import br.com.music.services.MusicaService;

@SpringBootTest
@AutoConfigureMockMvc
public class MusicaResourceTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MusicaService service;

	private MusicaDTO musicaDTO;

	@BeforeEach
	void setUp() {
		musicaDTO = new MusicaDTO();
		musicaDTO.setId(1L);
		musicaDTO.setNome("Música Teste");

		Mockito.when(service.findAll()).thenReturn(Collections.singletonList(musicaDTO));
		Mockito.when(service.findById(1L)).thenReturn(musicaDTO);
		Mockito.when(service.insert(any())).thenReturn(musicaDTO);
		Mockito.when(service.update(eq(1L), any())).thenReturn(musicaDTO);
		Mockito.doNothing().when(service).delete(1L);
	}

	@Test
	void testFindAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/musicas")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nome").value("Música Teste"));
	}

	@Test
	void testFindById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/musicas/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.nome").value("Música Teste"));
	}

	@Test
	void testInsert() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(musicaDTO);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/musicas").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated()).andExpect(header().string("Location", "http://localhost/musicas/1"));
	}

	@Test
	void testUpdate() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(musicaDTO);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/musicas/1").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Música Teste"));
	}

	@Test
	void testDelete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/musicas/1")).andExpect(status().isNoContent());
	}
}
