package com.mercadolibre.proyecto_mutantes;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mercadolibre.proyecto_mutantes.modelo.PersistenciaAdnDynamoDB;
import com.mercadolibre.proyecto_mutantes.modelo.VerificacionesAdn;

public class ValidarPersistenciaDynamo {

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

	@Test
	public void obtenerEstadisticaBD() {
		PersistenciaAdnDynamoDB estadisticaBD = new PersistenciaAdnDynamoDB();
		String respuesta = estadisticaBD.stats();
		assertNotNull(respuesta);
	}

}
