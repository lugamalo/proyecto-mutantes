package com.mercadolibre.proyecto_mutantes.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Esta clase mantiene el objeto Json que devuelve la respuesta de las estadisticas de verificaciones de ADN
 * @author Luis Gabriel
 *
 */
public class ResponseAdn {
	@JsonProperty("count_mutant_dna")
	private int count_mutant_dna;
	
	@JsonProperty("count_human_dna")
	private int count_human_dna;
	
	@JsonProperty("ratio")
	private String ratio;
	
	public int getCount_mutant_dna() {
		return count_mutant_dna;
	}

	public void setCount_mutant_dna(int count_mutant_dna) {
		this.count_mutant_dna = count_mutant_dna;
	}

	public int getCount_human_dna() {
		return count_human_dna;
	}

	public void setCount_human_dna(int count_human_dna) {
		this.count_human_dna = count_human_dna;
	}

	public String getRatio() {
		return ratio;
	}

	public void setRatio(String ratio) {
		this.ratio = ratio;
	}

	@Override
    public String toString() {
		String mensaje =  "count_human_dna " + String.valueOf(getCount_mutant_dna()) + " count_human_mutant " + String.valueOf(getCount_human_dna()) + " ratio " + ratio;
		return mensaje;
    }

}
