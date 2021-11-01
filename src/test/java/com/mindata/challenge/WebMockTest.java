package com.mindata.challenge;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mindata.challenge.component.JwtProvider;
import com.mindata.challenge.entity.SuperHeroe;
import com.mindata.challenge.entity.User;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebMockTest extends AbsMockTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	private static final String PAHT_URL = "/justicie";
	private static final String PAHT_URL_AUTH = "/auth";

	@Autowired
	private MockMvc mockMvc;
	
	String token;
	
	@BeforeEach
	public void auth() throws Exception {
		// Creo Objeto para Request
		User user = new User("anepa", "sporman");

		String requestJson = getSuperToJson(user);
		
		String responseTokenHeader = mockMvc.perform(post(PAHT_URL_AUTH+"/authUser").contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn().getResponse().getHeader(JwtProvider.AUTHORIZATION);
		
		token = responseTokenHeader;

		assertThat(responseTokenHeader).isNotNull().isNotEmpty();
	}

	// Retorna todos los superheroes con status 200
	@Test
	@Order(1)
	public void getAllSuperHeroes() throws Exception {
		String responseString = mockMvc.perform(get(PAHT_URL+"/getAllSuperHeroes").header(JwtProvider.AUTHORIZATION, token))
				.andExpect(status().isOk())//200
				.andDo(print()).andReturn().getResponse().getContentAsString();

		assertThat(responseString).isNotNull().isNotEmpty();
	}

	// Retorna SuperHeroeById con parametro status 200
	@Test
	@Order(2)
	public void getSuperHeroeById() throws Exception {
		String responseString = mockMvc.perform(get(PAHT_URL+"/getSuperHeroeById").header(JwtProvider.AUTHORIZATION, token)
				.param("id", "2"))
				.andExpect(status().isOk())
				.andDo(print()).andReturn()
				.getResponse().getContentAsString();

		assertThat(responseString).isNotNull().isNotEmpty();
	}
	
	// Retorna SuperHeroeById sin parametro status 400
	@Test
	@Order(3)
	public void getSuperHeroeByIdNoParam() throws Exception {
		mockMvc.perform(get(PAHT_URL+"/getSuperHeroeById").header(JwtProvider.AUTHORIZATION, token))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna SuperHeroeById con parametro erroneo status 400
	@Test
	@Order(4)
	public void getSuperHeroeByIdParamError() throws Exception {
		mockMvc.perform(get(PAHT_URL+"/getSuperHeroeById").header(JwtProvider.AUTHORIZATION, token)
				.param("id", ""))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna SuperHeroeById con parametro alfanumerico status 400
	@Test
	@Order(5)
	public void getSuperHeroeByIdParamAlfaNUm() throws Exception {
		mockMvc.perform(get(PAHT_URL+"/getSuperHeroeById").header(JwtProvider.AUTHORIZATION, token)
				.param("id", "24sfdsa"))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna getSuperHeroesByName con parametro inexistente en base status 204
	@Test
	@Order(6)
	public void getSuperHeroeByIdParamInexist() throws Exception {
		mockMvc.perform(get(PAHT_URL+"/getSuperHeroeById").header(JwtProvider.AUTHORIZATION, token)
				.param("id", "63"))
				.andExpect(status().isNoContent())
				.andDo(print());
	}
	
	// Retorna getSuperHeroesByName sin parametro status 400
	@Test
	@Order(7)
	public void getSuperHeroesByNameNoParam() throws Exception {
		mockMvc.perform(get(PAHT_URL+"/getSuperHeroesByName").header(JwtProvider.AUTHORIZATION, token))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna getSuperHeroesByName con parametro status 200
	@Test
	@Order(8)
	public void getSuperHeroesByName() throws Exception {
		String responseString = mockMvc.perform(get(PAHT_URL+"/getSuperHeroesByName").header(JwtProvider.AUTHORIZATION, token)
				.param("name", "man"))
				.andExpect(status().isOk())
				.andDo(print()).andReturn()
				.getResponse().getContentAsString();

		assertThat(responseString).isNotNull().isNotEmpty();
	}
	
	// Retorna getSuperHeroesByName con parametro<3char status 400
	// Esto es para no matar a la Base
	@Test
	@Order(9)
	public void getSuperHeroesByNameParamCorto() throws Exception {
		mockMvc.perform(get(PAHT_URL+"/getSuperHeroesByName").header(JwtProvider.AUTHORIZATION, token)
				.param("name", "ma"))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna getSuperHeroesByName con parametro>20char status 400
	// Esto es para no matar a la Base
	@Test
	@Order(10)
	public void getSuperHeroesByNameParamLargo() throws Exception {
		mockMvc.perform(get(PAHT_URL+"/getSuperHeroesByName").header(JwtProvider.AUTHORIZATION, token)
				.param("name", "123456789012345678901"))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna getSuperHeroesByName con parametro inexistente en base status 204
	@Test
	@Order(11)
	public void getSuperHeroesByName_Param_Inexist() throws Exception {
		mockMvc.perform(get(PAHT_URL+"/getSuperHeroesByName").header(JwtProvider.AUTHORIZATION, token)
				.param("name", "mano"))
				.andExpect(status().isNoContent())
				.andDo(print());
	}
	
	// Retorna updSuperHeroe ok status 200
	@Test
	@Order(12)
	public void updSuperHeroe() throws Exception {

		// Creo Objeto para Request
		SuperHeroe superHeroe = new SuperHeroe();
		superHeroe.setId(2);
		superHeroe.setName("Robin Hood");
		superHeroe.setFlying(false);
		superHeroe.setMoney(2.0);
		superHeroe.setStrength(10);

		String requestJson = getSuperToJson(superHeroe);

		mockMvc.perform(put(PAHT_URL+"/updSuperHeroe").header(JwtProvider.AUTHORIZATION, token)
				.contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	// Retorna updSuperHeroe sin parametro id status 400
	@Test
	@Order(13)
	public void updSuperHeroeSinParametroId() throws Exception {

		// Creo Objeto para Request
		SuperHeroe superHeroe = new SuperHeroe();
		superHeroe.setName("Robin Hood");
		superHeroe.setFlying(false);
		superHeroe.setMoney(2.0);
		superHeroe.setStrength(10);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson=ow.writeValueAsString(superHeroe);

		mockMvc.perform(put(PAHT_URL+"/updSuperHeroe").header(JwtProvider.AUTHORIZATION, token)
				.contentType(APPLICATION_JSON_UTF8)
				.content(requestJson))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna updSuperHeroe sin parametro name status 400
	@Test
	@Order(14)
	public void updSuperHeroeSinParametroName() throws Exception {

		// Creo Objeto para Request
		SuperHeroe superHeroe = new SuperHeroe();
		superHeroe.setId(2);
		superHeroe.setFlying(false);
		superHeroe.setMoney(2.0);
		superHeroe.setStrength(10);

		mockMvc.perform(put(PAHT_URL+"/updSuperHeroe").header(JwtProvider.AUTHORIZATION, token)
				.contentType(APPLICATION_JSON_UTF8)
				.content(getSuperToJson(superHeroe)))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna updSuperHeroe con parametro name menos a 3 status 400
	@Test
	@Order(15)
	public void updSuperHeroeParametroNameMenorA3() throws Exception {

		// Creo Objeto para Request
		SuperHeroe superHeroe = new SuperHeroe();
		superHeroe.setId(2);
		superHeroe.setName("Ro");
		superHeroe.setFlying(false);
		superHeroe.setMoney(2.0);
		superHeroe.setStrength(10);

		mockMvc.perform(put(PAHT_URL+"/updSuperHeroe").header(JwtProvider.AUTHORIZATION, token)
				.contentType(APPLICATION_JSON_UTF8)
				.content(getSuperToJson(superHeroe)))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna updSuperHeroe con parametro name menos a 3 status 400
	@Test
	@Order(16)
	public void updSuperHeroeParametroNameMayorA20() throws Exception {

		// Creo Objeto para Request
		SuperHeroe superHeroe = new SuperHeroe();
		superHeroe.setId(2);
		superHeroe.setName("Roberto Gomez Bolaños");
		superHeroe.setFlying(false);
		superHeroe.setMoney(2.0);
		superHeroe.setStrength(10);

		mockMvc.perform(put(PAHT_URL+"/updSuperHeroe").header(JwtProvider.AUTHORIZATION, token)
				.contentType(APPLICATION_JSON_UTF8)
				.content(getSuperToJson(superHeroe)))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna delSuperHeroeById con parametro status 200
	@Test
	@Order(17)
	public void delSuperHeroeById() throws Exception {
		mockMvc.perform(delete(PAHT_URL+"/delSuperHeroeById").header(JwtProvider.AUTHORIZATION, token)
				.param("id", "2"))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	// Retorna SuperHeroeById sin parametro status 400
	@Test
	@Order(18)
	public void delSuperHeroeByIdNoParam() throws Exception {
		mockMvc.perform(delete(PAHT_URL+"/delSuperHeroeById").header(JwtProvider.AUTHORIZATION, token))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna SuperHeroeById con parametro erroneo status 400
	@Test
	@Order(19)
	public void delSuperHeroeByIdParamError() throws Exception {
		mockMvc.perform(delete(PAHT_URL+"/delSuperHeroeById").header(JwtProvider.AUTHORIZATION, token)
				.param("id", ""))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	// Retorna SuperHeroeById con parametro alfanumerico status 400
	@Test
	@Order(20)
	public void delSuperHeroeByIdParamAlfaNUm() throws Exception {
		mockMvc.perform(delete(PAHT_URL+"/delSuperHeroeById").header(JwtProvider.AUTHORIZATION, token)
				.param("id", "24sfdsa"))
				.andExpect(status().isBadRequest())
				.andDo(print());
	}
	
	@Test
	@Order(21)
	public void refreshToken() throws Exception {
		String responseTokenHeader = mockMvc.perform(post(PAHT_URL_AUTH+"/refreshToken").header(JwtProvider.AUTHORIZATION, token))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn().getResponse().getHeader(JwtProvider.AUTHORIZATION);
		
		token = responseTokenHeader;

		assertThat(responseTokenHeader).isNotNull().isNotEmpty();
	}
}
