package com.meli.mutants.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Stat {
	
	@JsonProperty("count_mutant_dna")
	private Integer countMutantDna;
	@JsonProperty("count_human_dna")
	private Integer countHumanDna;
	@JsonProperty("ratio")
	private Double ratio;
	
}
