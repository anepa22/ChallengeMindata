package com.mindata.challenge;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mindata.challenge.web.SuperHeroeControler;

@SpringBootTest
class SuperHeroesTests {

	@Autowired
	private SuperHeroeControler controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}
}
