package com.mercadolibre.proyecto_mutantes.modelo;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.TimeZone;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;

/**
 * Esta clase contiene la funcionalidad de base de datos que maneja la persistencia de las verificaciones de ADN ingresadas por API
 * @author Luis Gabriel
 *
 */
public class PersistenciaAdnDynamoDB {

	static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
	static DynamoDB dynamoDB = new DynamoDB(client);

	static String tableName = "VerificacionesADN";

	/**
	 * Este metodo permite registrar en la base de datos los ADN's que fueron validados por los APIs
	 * @param verificacionesAdn Objeto que contiene la información de las secuencias ADN's
	 * @return booleano true el registro fue insertado correctamente y false lo contrario 
	 */
	public static Boolean registrarAdnValida(VerificacionesAdn verificacionesAdn) {
		Table table = dynamoDB.getTable(tableName);
		try {
			Item item = new Item().withPrimaryKey("Id", obtenerFechaISO() )
					.withStringSet("Adn",
							new HashSet<String>(
									Arrays.asList(verificacionesAdn.getAdn())))
					.withBoolean("EsHumano", verificacionesAdn.isEsHumano())
					.withBoolean("EsMutante", verificacionesAdn.isEsMutante());
			table.putItem(item);
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Este metodo obtiene la fecha en formato ISO que es usada como partition key de la tabla de validaciones de ADNs
	 * @return String con el formato ISO
	 */
	private static String obtenerFechaISO() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		sdf.setTimeZone(TimeZone.getTimeZone("CET"));
		String text = sdf.format(date);
		System.out.println("fecha " + text);
		return text;
	}

}
