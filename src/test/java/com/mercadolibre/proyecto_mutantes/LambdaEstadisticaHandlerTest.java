package com.mercadolibre.proyecto_mutantes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.mercadolibre.proyecto_mutantes.lambda.EstadisticaHandler;

public class LambdaEstadisticaHandlerTest {
	
	private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
		// TODO: set up your sample input object here.
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("employee_id", "99");
        hashMap.put("employee_name", "Jimmy");
        hashMap.put("expense_type", "travel");
        hashMap.put("amount", "465.98");
        input = hashMap ;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("EstadisticasLambda");

        return ctx;
    }

    @Test
    public void testLambdaFormFunctionHandler() {
    	EstadisticaHandler handler = new EstadisticaHandler();
        Context ctx = createContext();
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        Object output = handler.handleRequest( hashMap , ctx);

        // TODO: validate output here if needed.
        if (output != null) {
            System.out.println(output.toString());
        }
    }

}
