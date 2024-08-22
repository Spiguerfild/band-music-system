package br.com.music.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

import br.com.music.dto.MusicoDTO;
import br.com.music.services.MusicoService;

@SpringBootTest
@AutoConfigureMockMvc
public class MusicoResourceTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MusicoService service;

	private MusicoDTO musicoDTO;

	@BeforeEach
	void setUp() {
		musicoDTO = new MusicoDTO();
		musicoDTO.setId(1L);
		musicoDTO.setNome("John Doe");

		Mockito.when(service.findAll()).thenReturn(Collections.singletonList(musicoDTO));
		Mockito.when(service.findById(1L)).thenReturn(musicoDTO);
		Mockito.when(service.insert(any(MusicoDTO.class))).thenReturn(musicoDTO);
		Mockito.when(service.update(eq(1L), any(MusicoDTO.class))).thenReturn(musicoDTO);
		Mockito.doNothing().when(service).delete(1L);
	}

	@Test
	void testFindAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/musicos")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].nome").value("John Doe"));
	}

	@Test
	void testFindById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/musicos/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.nome").value("John Doe"));
	}

	@Test
	void testInsert() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(musicoDTO);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/musicos").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
	}

	@Test
	void testUpdate() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(musicoDTO);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/musicos/1").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nome").value("John Doe"));
	}

	@Test
	void testDelete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/musicos/1")).andExpect(status().isNoContent());
	}
}
