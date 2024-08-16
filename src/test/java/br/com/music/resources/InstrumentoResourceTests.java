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

import br.com.music.dto.InstrumentoDTO;
import br.com.music.services.InstrumentoService;

@SpringBootTest
@AutoConfigureMockMvc
public class InstrumentoResourceTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private InstrumentoService service;

	private InstrumentoDTO instrumentoDTO;

	@BeforeEach
	void setUp() {
		instrumentoDTO = new InstrumentoDTO();
		instrumentoDTO.setId(1L);
		instrumentoDTO.setNome("Guitarra");

		Mockito.when(service.findAll()).thenReturn(Collections.singletonList(instrumentoDTO));
		Mockito.when(service.findById(1L)).thenReturn(instrumentoDTO);
		Mockito.when(service.insert(any())).thenReturn(instrumentoDTO);
		Mockito.when(service.update(eq(1L), any())).thenReturn(instrumentoDTO);
		Mockito.doNothing().when(service).delete(1L);
	}

	@Test
	void testFindAll() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/instrumentos")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nome").value("Guitarra"));
	}

	@Test
	void testFindById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/instrumentos/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.nome").value("Guitarra"));
	}

	@Test
	void testInsert() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(instrumentoDTO);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/instrumentos").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
	}

	@Test
	void testUpdate() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(instrumentoDTO);

		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.put("/instrumentos/1").content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk()).andExpect(jsonPath("$.nome").value("Guitarra"));
	}

	@Test
	void testDelete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/instrumentos/1")).andExpect(status().isNoContent());
	}
}
