package com.mindata.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class WebMockTest {

	@Autowired
	private MockMvc mockMvc;

	// Retorna todos los superheroes con status 200
	@Test
	public void getAllSuperHeroes() throws Exception {
		String responseString = mockMvc.perform(get("/justicie/getAllSuperHeroes")).andExpect(status().isOk())//200
				.andDo(print()).andReturn().getResponse().getContentAsString();

		assertThat(responseString).isNotNull().isNotEmpty();
	}

	// Retorna SuperHeroeById con parametro status 200
	@Test
	public void getSuperHeroeById() throws Exception {
		String responseString = mockMvc.perform(get("/justicie/getSuperHeroeById").
				param("id", "2"))
				.andExpect(status().isOk())
				.andDo(print()).andReturn()
				.getResponse().getContentAsString();

		assertThat(responseString).isNotNull().isNotEmpty();
	}
	
	// Retorna SuperHeroeById sin parametro status 400
	@Test
	public void getSuperHeroeByIdNoParam() throws Exception {
		mockMvc.perform(get("/justicie/getSuperHeroeById"))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna SuperHeroeById con parametro erroneo status 400
	@Test
	public void getSuperHeroeByIdParamError() throws Exception {
		mockMvc.perform(get("/justicie/getSuperHeroeById").
				param("id", ""))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna SuperHeroeById con parametro alfanumerico status 400
	@Test
	public void getSuperHeroeByIdParamAlfaNUm() throws Exception {
		mockMvc.perform(get("/justicie/getSuperHeroeById").
				param("id", "24sfdsa"))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna getSuperHeroesByName con parametro inexistente en base status 204
	@Test
	public void getSuperHeroeByIdParamInexist() throws Exception {
		mockMvc.perform(get("/justicie/getSuperHeroeById").
				param("id", "63"))
				.andExpect(status().isNoContent())
				.andDo(print());
	}
	
	// Retorna getSuperHeroesByName sin parametro status 400
	@Test
	public void getSuperHeroesByNameNoParam() throws Exception {
		mockMvc.perform(get("/justicie/getSuperHeroesByName"))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna getSuperHeroesByName con parametro status 200
	@Test
	public void getSuperHeroesByName() throws Exception {
		String responseString = mockMvc.perform(get("/justicie/getSuperHeroesByName").
				param("name", "man"))
				.andExpect(status().isOk())
				.andDo(print()).andReturn()
				.getResponse().getContentAsString();

		assertThat(responseString).isNotNull().isNotEmpty();
	}
	
	// Retorna getSuperHeroesByName con parametro<3char status 400
	// Esto es para no matar a la Base
	@Test
	public void getSuperHeroesByNameParamCorto() throws Exception {
		mockMvc.perform(get("/justicie/getSuperHeroesByName").
				param("name", "ma"))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna getSuperHeroesByName con parametro>20char status 400
	// Esto es para no matar a la Base
	@Test
	public void getSuperHeroesByNameParamLargo() throws Exception {
		mockMvc.perform(get("/justicie/getSuperHeroesByName").
				param("name", "123456789012345678901"))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna getSuperHeroesByName con parametro inexistente en base status 204
	@Test
	public void getSuperHeroesByName_Param_Inexist() throws Exception {
		mockMvc.perform(get("/justicie/getSuperHeroesByName").
				param("name", "mano"))
				.andExpect(status().isNoContent())
				.andDo(print());
	}
}
