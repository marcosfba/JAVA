package br.edu.unitri.DTO.Consultas;

import java.io.Serializable;

import br.edu.unitri.model.Colunas;

public class LetraBProj implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Colunas(nome = "Nº do Projeto", size = 155)
	private String numProjeto;
	
	@Colunas(nome = "Descrição do Projeto", size = 200)
	private String descProjeto;

	@Colunas(nome = "Quantidade de horas", size = 145)
	private int quantHoras;

	public LetraBProj() {
		super();
	}

	public LetraBProj(String numProjeto, String descProjeto, int quantHoras) {
		super();
		this.numProjeto = numProjeto;
		this.descProjeto = descProjeto;
		this.quantHoras = quantHoras;
	}

	public String getNumProjeto() {
		return numProjeto;
	}

	public void setNumProjeto(String numProjeto) {
		this.numProjeto = numProjeto;
	}

	public String getDescProjeto() {
		return descProjeto;
	}

	public void setDescProjeto(String descProjeto) {
		this.descProjeto = descProjeto;
	}

	public int getQuantHoras() {
		return quantHoras;
	}

	public void setQuantHoras(int quantHoras) {
		this.quantHoras = quantHoras;
	}

	@Override
	public String toString() {
		return "LetraBProj [numProjeto=" + numProjeto + ", descProjeto="
				+ descProjeto + ", quantHoras=" + quantHoras + "]";
	}	
	
}
