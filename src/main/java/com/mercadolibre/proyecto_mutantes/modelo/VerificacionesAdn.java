package com.mercadolibre.proyecto_mutantes.modelo;

public class VerificacionesAdn {
	
	private String id;
	private boolean esHumano;
	private boolean esMutante;
	private String[] adn;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isEsHumano() {
		return esHumano;
	}
	public void setEsHumano(boolean esHumano) {
		this.esHumano = esHumano;
	}
	public boolean isEsMutante() {
		return esMutante;
	}
	public String[] getAdn() {
		return adn;
	}
	public void setAdn(String[] adn) {
		this.adn = adn;
	}
	public void setEsMutante(boolean esMutante) {
		this.esMutante = esMutante;
	}

	
	

}
