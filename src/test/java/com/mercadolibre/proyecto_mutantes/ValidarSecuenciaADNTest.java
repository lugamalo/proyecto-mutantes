
package com.mercadolibre.proyecto_mutantes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mercadolibre.proyecto_mutantes.modelo.PersistenciaAdnDynamoDB;
import com.mercadolibre.proyecto_mutantes.modelo.VerificacionesAdn;
import com.mercadolibre.proyecto_mutantes.servicios.IsMutantService;

public class ValidarSecuenciaADNTest {

	private IsMutantService validarSecuenciaADN = new IsMutantService();

	@Test
	public void testSecuenciaMutanteValida() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		boolean result = validarSecuenciaADN.isMutant(dna);
		assertTrue(result);
	}

	@Test
	public void testSecuenciaMutanteInvalida() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG" };
		boolean result = validarSecuenciaADN.isMutant(dna);
		assertFalse(result);
	}

	@Test
	public void testADNValido() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG" };
		boolean result = validarSecuenciaADN.isADNValido(dna);
		assertTrue(result);
	}

	@Test
	public void testADNNull() {
		String[] dna = null;
		boolean result = validarSecuenciaADN.isADNValido(dna);
		assertFalse(result);
	}

	@Test
	public void testMatrizCuadradaInValida() {
		String[] dna = { "ATGCG", "HAGTGC", "TTATTT", "ZGACGG", "GCGTCA", "TCACTG" };
		boolean result = validarSecuenciaADN.isADNValido(dna);
		assertFalse(result);
	}

	@Test
	public void testBaseNitrogenadaInvalida() {
		String[] dna = { "ATGCGA", "HAGTGC", "TTATTT", "ZGACGG", "GCGTCA", "TCACTG" };
		boolean result = validarSecuenciaADN.isADNValido(dna);
		assertFalse(result);
	}

	@Test
	public void insertarSecuenciaAdnBD() {
		String[] adn = { "AGACGG", "ATGCGA", "CAGTGC", "GCGTCA", "TCACTG", "TTATTT" };
		VerificacionesAdn vAdn = new VerificacionesAdn();
		vAdn.setEsHumano(true);
		vAdn.setEsMutante(false);
		vAdn.setAdn(adn);

		boolean respuesta = PersistenciaAdnDynamoDB.registrarAdnValida(vAdn);
		assertTrue(respuesta);
	}

}
