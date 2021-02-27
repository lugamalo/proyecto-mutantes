package com.mercadolibre.proyecto_mutantes.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mercadolibre.proyecto_mutantes.modelo.PersistenciaAdnDynamoDB;
import com.mercadolibre.proyecto_mutantes.modelo.VerificacionesAdn;
import com.mercadolibre.proyecto_mutantes.servicios.IsMutantService;

/**
 * Esta clase permite invocar la funcion lambda que valida si la cadena ingresada a traves de un servicio valida si es mutante
 * @author Luis Gabriel
 *
 */
public class MutantRequestHandler implements RequestHandler<String[], String> {
	
	public String handleRequest(String[] adn, Context context) {
		context.getLogger().log("Input: " + adn);

		String respuesta = null;
		IsMutantService secuenciaADN = new IsMutantService();

		boolean adnValido = secuenciaADN.isADNValido(adn);
		if (!adnValido) {
			respuesta = "400-Bad Request";
		}
		else {
			boolean resultado = secuenciaADN.isMutant(adn);
			if (resultado) {
				VerificacionesAdn vAdn = new VerificacionesAdn();
				vAdn.setEsMutante(true);
				vAdn.setEsHumano(false);
				vAdn.setAdn(adn);
				respuesta = "HTTP 200-OK";
				PersistenciaAdnDynamoDB.registrarAdnValida(vAdn);
			} else {
				VerificacionesAdn vAdn = new VerificacionesAdn();
				vAdn.setEsHumano(true);
				vAdn.setEsMutante(false);
				vAdn.setAdn(adn);
				respuesta = "403-Forbidden";
				PersistenciaAdnDynamoDB.registrarAdnValida(vAdn);
			}
		}
		return respuesta;
	}
}
