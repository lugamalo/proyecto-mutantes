package com.mercadolibre.proyecto_mutantes.lambda;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.mercadolibre.proyecto_mutantes.modelo.ResponseAdn;

public class EstadisticaHandler implements RequestHandler<Map<String, Object>, ResponseAdn> {

	static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
	private static String tableName = "VerificacionesADN";
	static DynamoDB dynamoDB = new DynamoDB(client);

	/**
	 * Este metodo devuelve un objeto Json con la respuesta de las estadisticas de verificaciones de ADN
	 */
	public ResponseAdn handleRequest(Map<String, Object> input, Context context) {

		ResponseAdn respuesta = estadisticas();
		return respuesta;
	}

	/**
	 * Es metodo consulta en BD la estadisticas de mutantes y humanos registrados en la BD
	 * @return ResponseAdn obtiene la estadistica en formato definido para la prueba
	 */
	public ResponseAdn estadisticas() {
		Table table = dynamoDB.getTable(tableName);
		ItemCollection<ScanOutcome> items = table.scan();
		int contador = 0;
		int contadorMutantes = 0;
		Iterator<Item> iter = items.iterator();
		while (iter.hasNext()) {
			Item item = iter.next();
			boolean valor = (Boolean) item.get("EsMutante");
			//System.out.println(valor);
			if (valor) {
				contadorMutantes++;
			}
			contador++;
			//System.out.println(item.toString());
		}
		System.out.println("Numero cadenas mutante " + contadorMutantes);
		System.out.println("Numero items tabla " + contador);
		ResponseAdn valor = stats(contador, contadorMutantes);

		return valor;
	}

	/**
	 * Este metodo obtiene la estadistica de las verificaciones de ADN
	 * 
	 * @return ResponseAdn Objeto JSON con la estadistica de las verificaciones de ADN
	 */
	public static ResponseAdn stats(int registrosTabla, int numeroMutantes) {
		int itemsTabla = registrosTabla;
		int itemsMutantes = numeroMutantes;

		int contadorHumanos = itemsTabla - itemsMutantes;
		double ratio = Double.valueOf(String.valueOf(itemsMutantes)) / Double.valueOf(String.valueOf(contadorHumanos));
		DecimalFormat formatoDouble = new DecimalFormat("#.##");

		ResponseAdn responseAdn = new ResponseAdn();
		responseAdn.setCount_human_dna(contadorHumanos);
		responseAdn.setCount_mutant_dna(numeroMutantes);
		responseAdn.setRatio(formatoDouble.format(ratio));
		System.out.println(responseAdn.toString());

		return responseAdn;
	}

}
