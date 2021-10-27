package com.mindata.challenge.web;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mindata.challenge.entity.SuperHeroe;
import com.mindata.challenge.service.SuperHeroeService;

@RestController
@RequestMapping("/justicie")
@Validated
public class SuperHeroeControler {
	@Autowired
	SuperHeroeService service;
	
	@RequestMapping(path = "/getAllSuperHeroes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SuperHeroe> getAllSuperHeroes() {
		return service.getAllSuperHeroes();
	}
	
	@RequestMapping(path = "/getSuperHeroeById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public SuperHeroe getSuperHeroeById(@RequestParam @NotNull Integer id) {
		return service.getSuperHeroeById(id);
	}
	
	@RequestMapping(path = "/getSuperHeroesByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SuperHeroe> getSuperHeroesByName(@Size(min= 3, max = 20) @RequestParam(name = "name", required = true) String name) {
		return service.getSuperHeroesByName(name.strip());
	}
	
	@RequestMapping(path = "/updSuperHeroe", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void updSuperHeroe(@Valid @RequestBody SuperHeroe superHeroe) {
		service.updSuperHeroe(superHeroe);
	}
	
	@RequestMapping(path = "/delSuperHeroe", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void delSuperHeroe(@RequestParam @NotNull Integer id) {
		service.delSuperHeroe(id);
	}
}
