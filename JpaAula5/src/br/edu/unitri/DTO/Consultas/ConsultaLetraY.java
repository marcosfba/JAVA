/**
 * 
 */
package br.edu.unitri.DTO.Consultas;

import java.io.Serializable;

import br.edu.unitri.model.Colunas;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaLetraY implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Colunas(nome = "Descrição do Projeto", size = 175)
	private String descProjeto;

	@Colunas(nome = "Nº do projeto", size = 175)
	private String numProjeto;
	
	@Colunas(nome = "Nome Local", size = 175)
	private String descLocal;
	
	@Colunas(nome = "Código Local", size = 175)
	private String nomeLocal;

	public ConsultaLetraY() {
		super();
	}

	public ConsultaLetraY(String descProjeto, String numProjeto,
			String descLocal, String nomeLocal) {
		super();
		this.descProjeto = descProjeto;
		this.numProjeto = numProjeto;
		this.descLocal = descLocal;
		this.nomeLocal = nomeLocal;
	}

	public String getDescProjeto() {
		return descProjeto;
	}

	public void setDescProjeto(String descProjeto) {
		this.descProjeto = descProjeto;
	}

	public String getNumProjeto() {
		return numProjeto;
	}

	public void setNumProjeto(String numProjeto) {
		this.numProjeto = numProjeto;
	}

	public String getDescLocal() {
		return descLocal;
	}

	public void setDescLocal(String descLocal) {
		this.descLocal = descLocal;
	}

	public String getNomeLocal() {
		return nomeLocal;
	}

	public void setNomeLocal(String nomeLocal) {
		this.nomeLocal = nomeLocal;
	}

	@Override
	public String toString() {
		return "ConsultaLetraY [descProjeto=" + descProjeto + ", numProjeto="
				+ numProjeto + ", descLocal=" + descLocal + ", nomeLocal="
				+ nomeLocal + "]";
	}

}
