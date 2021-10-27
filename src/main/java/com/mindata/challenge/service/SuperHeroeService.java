package com.mindata.challenge.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindata.challenge.entity.SuperHeroe;
import com.mindata.challenge.repository.SuperHeroeRepo;

@Service
public class SuperHeroeService {

	@Autowired
	SuperHeroeRepo repository;

	public SuperHeroe getSuperHeroeById(int id) {
		Optional<SuperHeroe> superHeroe = repository.findById(id);
		if (!superHeroe.isPresent()) {
			throw new EntityNotFoundException("No existe id para ese superHeroe.");
		}

		return superHeroe.get();
	}

	public List<SuperHeroe> getSuperHeroesByName(String name) {
		List<SuperHeroe> superHeroes = repository.findByName(name);
		if (superHeroes.isEmpty()) {
			throw new EntityNotFoundException("No existen superHeroes.");
		}

		return superHeroes;
	}

	public List<SuperHeroe> getAllSuperHeroes() {
		List<SuperHeroe> superHeroes = repository.findAll();
		if (superHeroes.isEmpty()) {
			throw new EntityNotFoundException("No existen superHeroes.");
		}

		return superHeroes;
	}

	public void updSuperHeroe(SuperHeroe newSuperHero) {
		Optional<SuperHeroe> superHeroe = repository.findById(newSuperHero.getId());

		if (!superHeroe.isPresent()) {
			throw new EntityNotFoundException("No existe id para actualizar ese superHeroe.");
		}
		
		newSuperHero.setId(superHeroe.get().getId());
		repository.save(newSuperHero);
	}

	public void delSuperHeroe(int id) {
		Optional<SuperHeroe> superHeroe = repository.findById(id);

		if (!superHeroe.isPresent()) {
			throw new EntityNotFoundException("No existe id para eliminar ese superHeroe.");
		}
		
		repository.delete(superHeroe.get());
	}
}
