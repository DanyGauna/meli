package com.meli.mutants.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meli.mutants.dtos.DnaRequest;
import com.meli.mutants.dtos.Stat;
import com.meli.mutants.services.IMutantesService;

@RestController
@RequestMapping("/mutantes")
public class MutanteController {
	
	Logger logger = LoggerFactory.getLogger(MutanteController.class);
	
	@Autowired
	private IMutantesService mutantesService;

	@PostMapping("/mutant")
	public String esMutante(@RequestBody DnaRequest dna) {
		logger.info("::::Inicio método esMutante::::");
		logger.info("::::DNA: "+dna.getDna()+"::::");
		String response = "";
		try {
			boolean esMutante = mutantesService.isMutant(dna.getDna());
			if (esMutante) {
				response = HttpStatus.OK.toString();
			} else {
				response =  HttpStatus.FORBIDDEN.toString();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			response =  HttpStatus.INTERNAL_SERVER_ERROR.toString();
		}
		logger.info("::::Finaliza método esMutante::::");
		return response;
	}
	
	@GetMapping("/stats")
	public Stat stats() {
		logger.info("::::Inicio método stats::::");
		Stat stat = null;
		try {
			stat = mutantesService.getStats();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info("::::Finaliza método stats::::");
		return stat;
	}

}
