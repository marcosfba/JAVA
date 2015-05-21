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
public class ConsultaLetraF implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Colunas(nome = "Nº Departamento", size = 175)
	private String numDepartamento;
	
	@Colunas(nome = "Nome Departamento", size = 175)
	private String nomeDepartamento;

	@Colunas(nome = "Nº Projeto", size = 175)
	private String numProjeto;

	public ConsultaLetraF() {
		super();
	}

	public ConsultaLetraF(String numDepartamento, String nomeDepartamento,
			String numProjeto) {
		super();
		this.numDepartamento = numDepartamento;
		this.nomeDepartamento = nomeDepartamento;
		this.numProjeto = numProjeto;
	}

	public String getNumDepartamento() {
		return numDepartamento;
	}

	public void setNumDepartamento(String numDepartamento) {
		this.numDepartamento = numDepartamento;
	}

	public String getNomeDepartamento() {
		return nomeDepartamento;
	}

	public void setNomeDepartamento(String nomeDepartamento) {
		this.nomeDepartamento = nomeDepartamento;
	}

	public String getNumProjeto() {
		return numProjeto;
	}

	public void setNumProjeto(String numProjeto) {
		this.numProjeto = numProjeto;
	}

	@Override
	public String toString() {
		return "ConsultaLetraF [numDepartamento=" + numDepartamento
				+ ", nomeDepartamento=" + nomeDepartamento + ", numProjeto="
				+ numProjeto + "]";
	}

}
