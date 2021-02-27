package com.mercadolibre.proyecto_mutantes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.mercadolibre.proyecto_mutantes.lambda.MutantRequestHandler;

public class LambdaMutanteHandlerTest {

	MutantRequestHandler handler = new MutantRequestHandler();

	private Context createContext() {
		TestContextMutante ctx = new TestContextMutante();
		ctx.setFunctionName("MutanteLambda");
		return ctx;
	}

	@Test
	public void testSecuenciaMutanteValida() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG" };
		Context ctx = createContext();
		String output = handler.handleRequest(dna, ctx);
		assertEquals("HTTP 200-OK", output);
	}

	@Test
	public void testSecuenciaMutanteInvalida() {
		String[] dna = { "ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG" };
		Context ctx = createContext();
		String output = handler.handleRequest(dna, ctx);
		assertEquals("403-Forbidden", output);
	}

	@Test
	public void testBaseNitrogenadaInvalida() {
		String[] dna = { "ATGCGA", "HAGTGC", "TTATTT", "ZGACGG", "GCGTCA", "TCACTG" };
		Context ctx = createContext();
		String output = handler.handleRequest(dna, ctx);
		assertEquals("400-Bad Request", output);
	}
	
	@Test
	public void testADNNull() {
		String[] dna = null;
		Context ctx = createContext();
		String output = handler.handleRequest(dna, ctx);
		assertEquals("400-Bad Request", output);
	}
	
	@Test
	public void testMatrizCuadradaInValida() {
		String[] dna = { "ATGC", "HAGTGC", "TTATTT", "ZGACGG", "GCGTCA", "TCACTG" };
		Context ctx = createContext();
		String output = handler.handleRequest(dna, ctx);
		assertEquals("400-Bad Request", output);
	}

}
