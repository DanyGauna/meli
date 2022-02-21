package com.meli.mutants.services.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meli.mutants.dtos.Stat;
import com.meli.mutants.models.Dna;
import com.meli.mutants.repositories.IDnaRepository;
import com.meli.mutants.services.IMutantesService;

@Service
public class MutantesServiceImpl implements IMutantesService {
	
	Logger logger = LoggerFactory.getLogger(MutantesServiceImpl.class);
	
	private static int SECUENCIA_LENGTH = 4;
	private static int SECUENCIAS_PERMITIDAS = 2;
	private static List<Character> LETRAS_ADMITIDAS = Arrays.asList('A', 'T', 'C', 'G');
	private static String EXISTEN_CARACTERES_INVALIDO = "Existen caracteres inválido.";
	

	private int secuenciasEncontradas;
	private int consecutivo;

	@Autowired
	private IDnaRepository dnaRepository;
	
	/**
	 * Guarda un DNA
	 * @param dna
	 */
	public void save(Dna dna) {
		dnaRepository.save(dna);
	}
	
	public Stat getStats() {
		Stat stats = new Stat();
		stats.setCountHumanDna(dnaRepository.countByEsMutante(false));
		stats.setCountMutantDna(dnaRepository.countByEsMutante(true));
		if (stats.getCountHumanDna() > 0L) {
			stats.setRatio((double) stats.getCountMutantDna() / (double) stats.getCountHumanDna());
		} else {
			stats.setRatio(0D);
		}
		return stats;
	}
	
	/**
	 * Metodo para verificar si un humano es mutante segun su adn
	 * @author Daniel Gauna
	 * @param dna
	 * @return boolean
	 * @throws Exception 
	 */
	public Boolean isMutant(String[] dna) throws Exception {
		
		Dna adn = new Dna();
		adn.setDna(dna);
		//verifico si es mutante o no en base al dna
		adn.setEsMutante(verificaMutacion(dna));
		Dna adnAux = dnaRepository.findByDna(dna);
		//Si no existe el dna lo guarda y sino devuelve el dna encontrado
		if (adnAux == null) {
			save(adn);
		} else {
			return adnAux.getEsMutante();
		}
		return adn.getEsMutante();
	}

	private boolean verificaMutacion(String[] dna) throws Exception {

		secuenciasEncontradas = 0;
		verificaMatrizHorizontal(dna);
		verificaMatrizVertical(dna);
		verificaMatrizOblicua(dna);

		if (secuenciasEncontradas >= SECUENCIAS_PERMITIDAS) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Metodo para verificar las filas de la matriz
	 * @param dna
	 * @throws Exception
	 */
	private void verificaMatrizHorizontal(String[] dna) throws Exception {

		if (dna.length > 0) {
			for (int i = 0; i < dna.length; i++) {
				if (validaLetra(dna, i, 0)) {
					consecutivo = 0;
					for (int j = 0; j < dna.length - 1; j++) {
						if (validaLetra(dna, i, j + 1)) {
							leePosicion(dna, i, j, i, j + 1);
						} else {
							throw new Exception(EXISTEN_CARACTERES_INVALIDO);
						}
					}
				} else {
					throw new Exception(EXISTEN_CARACTERES_INVALIDO);
				}
			}
		} else {
			throw new Exception(EXISTEN_CARACTERES_INVALIDO);
		}
	}

	/**
	 * Metodo para verificar las columnas de la matriz
	 * @param dna
	 */
	private void verificaMatrizVertical(String[] dna) {

		int j = 0;
		while ((secuenciasEncontradas < SECUENCIAS_PERMITIDAS) && j < dna.length) {
			int i = 0;
			consecutivo = 0;
			while ((secuenciasEncontradas < SECUENCIAS_PERMITIDAS) && validaEspacios(dna.length - i)) {
				leePosicion(dna, i, j, i + 1, j);
				i++;
			}
			j++;
		}
	}

	/**
	 * Metodo para verificar la matriz de forma oblicua
	 * @param dna
	 */
	private void verificaMatrizOblicua(String[] dna) {

		int row = SECUENCIA_LENGTH - 1;
		while ((secuenciasEncontradas < SECUENCIAS_PERMITIDAS) && row < dna.length) {
			int i = row;
			int j = 0;
			while ((secuenciasEncontradas < SECUENCIAS_PERMITIDAS) && validaEspacios((row + 1) - j)) {
				leePosicion(dna, i, j, i - 1, j + 1);
				i--;
				j++;
			}
			row++;
		}

		int column = 1;

		while ((secuenciasEncontradas < SECUENCIAS_PERMITIDAS) && column < (dna.length - SECUENCIA_LENGTH + 1)) {
			int i = dna.length - 1;
			int j = column;
			while ((secuenciasEncontradas < SECUENCIAS_PERMITIDAS) && validaEspacios(dna.length - j)) {
				leePosicion(dna, i, j, i - 1, j + 1);
				i--;
				j++;
			}
			column++;
		}

	}

	private boolean validaLetra(String[] dna, int i, int j) {

		char c = obtieneLetra(dna, i, j);

		if (!LETRAS_ADMITIDAS.contains(c)) {
			return false;
		} else {
			return true;
		}
	}

	private void leePosicion(String[] dna, int i, int j, int nextI, int nextJ) {

		char actual = obtieneLetra(dna, i, j);
		char proxima = obtieneLetra(dna, nextI, nextJ);

		if (actual == proxima) {
			consecutivo++;
		} else {
			consecutivo = 0;
		}

		if (consecutivo == SECUENCIA_LENGTH - 1) {
			secuenciasEncontradas++;
			consecutivo = 0;
		}
	}

	private boolean validaEspacios(int remainingSpace) {
		return remainingSpace >= SECUENCIA_LENGTH - consecutivo ;
	}

	private char obtieneLetra(String[] dna, int i, int j) {
		return dna[i].charAt(j);
	}


}
