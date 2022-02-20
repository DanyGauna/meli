package com.meli.mutants.services;

import com.meli.mutants.dtos.Stat;

public interface IMutantesService {
	
	Boolean isMutant(String[] dna) throws Exception;
	
	Stat getStats();

}
