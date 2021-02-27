package com.mercadolibre.proyecto_mutantes.servicios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Esta clase contiene el algoritmo que valida si la cadena de secuencia de ADN validada es humano o mutante
 * @author Luis Gabriel
 *
 */
public class IsMutantService {

	static String letrasBaseNitrogenada = "ACGT";
	static final int NUMERO_SECUENCIAS_VALIDAS_MUTANTE = 1;

	/**
	 * Este metodo valida si la secuencia de ADN ingresada es valida
	 * 
	 * @param dna secuencias ADN
	 * @return boolean que indica si la cadena de secuencia de ADN es valida
	 */
	public boolean isADNValido(String[] dna) {
		
		if (dna == null || dna.length == 0) {
			System.out.println("La secuencia de ADN no puede ser nula");
			return false;
		}
		for (int i = 0; i < dna.length; i++) {
			for (int j = 0; j < dna.length; j++) {
				// Validar matriz cuadrada
				if (dna[i].length() != dna.length) {
					System.out.println("No es una matriz cuadrada");
					return false;
				}
				String letraCadena = String.valueOf(dna[i].charAt(j));
				// System.out.println("letra cadena " + letraCadena);
				if (!letrasBaseNitrogenada.contains(letraCadena)) {
					System.out.println(
							"La secuencia de ADN tiene letras que no corresponden a la base nitrogenada (A,T,C,G) ");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Este metodo permite convertir la matriz unidimensional en una matriz bidimensional
	 * @param arrayUnidimensional ingresa una matriz unidimensional con la secuencia ADN
	 * @return matriz bidiomensional con la secuencia de ADN
	 */
	public static String[][] ConvertirArrayBidimensional(String[] arrayUnidimensional) {

		int numero = arrayUnidimensional.length;
		String[][] matriz = new String[numero][numero];
		for (int i = 0; i < arrayUnidimensional.length; i++) {
			//System.out.println(arrayUnidimensional[i]);
			for (int j = 0; j < arrayUnidimensional.length; j++) {
				matriz[i][j] = String.valueOf(arrayUnidimensional[i].charAt(j));
				//System.out.println(" Prueba matriz " + matriz[i][j]);
			}
		}
		return matriz;
	}

	/**
	 * Este metodo convierte la matriz bidimensional a unidimensional por columnas
	 * 
	 * @param matriz bidimensional cuadrada organizada por filas y columnas
	 * @return matriz unidimensional organizada para validar las columnas de la secuencia ADN
	 */
	public static String[] columnasArreglo(String[][] matriz) {
		int contador = matriz.length;
		String[] columnasADN = new String[contador];
		String token;
		for (int i = 0; i < matriz.length; i++) {
			token = "";
			//System.out.println("columna " + i);
			for (int j = 0; j < matriz.length; j++) {
				token = token + matriz[j][i];
				//System.out.println(token);
			}
			columnasADN[i] = token;
		}
		return columnasADN;
	}

	/**
	 * Este metodo obtiene en una arreglo unidimensional con la diagonal principal y
	 * vertical de la secuencia de ADN
	 * 
	 * @param matriz bidiomensional organizada por filas y columnas
	 * @return arreglo unidimensional con la diagonal principal y vertical de la secuencia de ADN
	 */
	public static String[] obtenerSecuenciaDiagonales(String[][] matriz) {
		String[] diagonales = new String[2];
		String dp = "";
		String ds = "";
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				if (i == j) {
					dp = dp + matriz[i][j];
				}
				if (i + j == matriz.length - 1) {
					ds = ds + matriz[i][j];
				}
			}
		}
		diagonales[0] = dp;
		diagonales[1] = ds;

		return diagonales;
	}

	/**
	 * Este metodo detecta si es mutante dado el patron de cuatro letras iguales por cada secuencia ADN
	 * 
	 * @param dna Cadena de secuencias ADN
	 * @return List con las cadenas de secuencias de ADN que hicieron match con el patron de una secuencia de 4 letras iguales por cada secuencia
	 */
	static List<String> validarAlgoritmo(String[] dna) {
		List<String> secuenciaCorrecta = new ArrayList<String>();
		for (int i = 0; i < dna.length; i++) {
			String hit = null;
			String[] out = dna[i].split("(?<=(.))(?!\\1)");
			System.out.println(Arrays.toString(out));
			for (int z = 0; z < out.length; z++) {
				if (out[z].length() == 4) {
					System.out.println("\npatron correcto");
					System.out.println(out[z]);
					hit = dna[i];
					System.out.println("cadena correcta " + hit);
					secuenciaCorrecta.add(hit);
				}
			}
		}
		return secuenciaCorrecta;
	}

	/**
	 * Este metodo valida si dada la cadena de secuencia ADN es mutante o humano
	 * 
	 * @param dna Cadena de secuencia ADN
	 * @return booleano que valida true si es mutante o false si es humano
	 */
	public boolean isMutant(String[] dna) {
		int contadorSecuencias = 0;
		// Validar filas
		List<String> lFilas = IsMutantService.validarAlgoritmo(dna);
		System.out.println("Elementos Filas " + lFilas.size());
		if ( lFilas.size() > NUMERO_SECUENCIAS_VALIDAS_MUTANTE ) {
			return true;
		}
		else {
			// Validar Columnas
			String[][] matriz = IsMutantService.ConvertirArrayBidimensional(dna);
			String[] columnas = IsMutantService.columnasArreglo(matriz);
			System.out.println("\nColumnas");
			List<String> lColumnas = IsMutantService.validarAlgoritmo(columnas);
			System.out.println("Elementos columnas " + lColumnas.size());
			contadorSecuencias = ( lColumnas.size() + lFilas.size() );
			if (contadorSecuencias > NUMERO_SECUENCIAS_VALIDAS_MUTANTE) {
				System.out.println(
						"################################################# Es mutante ##########################################################");
				return true;
			}
			// validar Diagonales
			System.out.println("\n\nDiagonales");
			String[] diagonales = IsMutantService.obtenerSecuenciaDiagonales(matriz);
			List<String> lDiagonales = IsMutantService.validarAlgoritmo(diagonales);

			contadorSecuencias = (lColumnas.size() + lFilas.size() + lDiagonales.size());
			System.out.println("Numero de secuencias Validas de ADN " + contadorSecuencias);
			
			if (contadorSecuencias > NUMERO_SECUENCIAS_VALIDAS_MUTANTE) {
				System.out.println(
						"################################################# Es mutante ##########################################################");
				return true;
			}
		}
		return false;
	}
}
