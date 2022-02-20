package com.meli.mutants.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meli.mutants.models.Dna;

@Repository
public interface IDnaRepository extends JpaRepository<Dna, Integer> {

	Dna findByDna(String[] dna);
	
	Integer countByEsMutante(Boolean esMutante);
}
