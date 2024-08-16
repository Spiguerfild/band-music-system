package br.com.music.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.music.dto.MusicoInstrumentoDTO;
import br.com.music.entities.Instrumento;
import br.com.music.entities.Musico;
import br.com.music.services.MusicoInstrumentoService;

public class MusicoInstrumentoResourceTests {

	@InjectMocks
	private MusicoInstrumentoResource resource;

	@Mock
	private MusicoInstrumentoService service;

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	private MusicoInstrumentoDTO musicoInstrumentoDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(resource).build();
		objectMapper = new ObjectMapper();

		// Criação de um DTO exemplo
		musicoInstrumentoDTO = new MusicoInstrumentoDTO();
		musicoInstrumentoDTO.setId(1L);
		musicoInstrumentoDTO.setMusico(new Musico());
		musicoInstrumentoDTO.setInstrumento(new Instrumento());
	}

	@Test
	void testFindAll() throws Exception {
		// Arrange
		when(service.findAll()).thenReturn(Collections.singletonList(musicoInstrumentoDTO));

		// Act & Assert
		mockMvc.perform(get("/musicosinstrumentos").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(musicoInstrumentoDTO.getId()));
	}

	@Test
	void testFindById() throws Exception {
		// Arrange
		when(service.findById(anyLong())).thenReturn(musicoInstrumentoDTO);

		// Act & Assert
		mockMvc.perform(get("/musicosinstrumentos/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(musicoInstrumentoDTO.getId()));
	}

	@Test
	void testInsert() throws Exception {
		// Arrange
		when(service.insert(any(MusicoInstrumentoDTO.class))).thenReturn(musicoInstrumentoDTO);
		String jsonBody = objectMapper.writeValueAsString(musicoInstrumentoDTO);

		// Act
		MvcResult result = mockMvc.perform(post("/musicosinstrumentos").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andReturn();

		// Assert
		String locationHeader = result.getResponse().getHeader("Location");
		assertNotNull(locationHeader);
		String expectedPath = "/musicosinstrumentos/1";

		// Extract the path from the Location header
		URI locationUri = new URI(locationHeader);
		String path = locationUri.getPath();

		// Compare the path with the expected path
		assertEquals(expectedPath, path, "Expected Location header path to be " + expectedPath + " but was " + path);
	}

	@Test
	void testUpdate() throws Exception {
		// Arrange
		when(service.update(anyLong(), any(MusicoInstrumentoDTO.class))).thenReturn(musicoInstrumentoDTO);
		String jsonBody = objectMapper.writeValueAsString(musicoInstrumentoDTO);

		// Act & Assert
		mockMvc.perform(put("/musicosinstrumentos/1").content(jsonBody).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(musicoInstrumentoDTO.getId()));
	}

	@Test
	void testDelete() throws Exception {
		// Act & Assert
		mockMvc.perform(delete("/musicosinstrumentos/1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}
}
