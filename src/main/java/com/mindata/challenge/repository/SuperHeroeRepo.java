package com.mindata.challenge.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mindata.challenge.entity.SuperHeroe;

public interface SuperHeroeRepo extends JpaRepository<SuperHeroe, Integer> {

	public Optional<SuperHeroe> findById(@Param("id") Integer id);

	@Query("select a from SuperHeroe a where a.name like %:name%")
	public List<SuperHeroe> findByName(@Param("name") String name);
	
}
