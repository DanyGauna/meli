package com.meli.mutants.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Dnas")
public class Dna {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name= "id_dna")
	private Integer idDna;
	@Column(name= "dna")
	private String [] dna;
	@Column(name= "es_mutante")
	private Boolean esMutante;
	
}
