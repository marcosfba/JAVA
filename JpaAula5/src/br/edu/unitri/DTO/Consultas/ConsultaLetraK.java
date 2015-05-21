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
public class ConsultaLetraK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Colunas(nome = "Nº Departamento", size = 175)
	private String numDepartamento;
	
	@Colunas(nome = "Descrição do Departamento", size = 175)
	private String descDepartamento;

	@Colunas(nome = "Nome do Empregado", size = 175)
	private String nomeEmpregado;

	public ConsultaLetraK() {
		super();
	}

	public ConsultaLetraK(String numDepartamento, String descDepartamento,
			String nomeEmpregado) {
		super();
		this.numDepartamento = numDepartamento;
		this.descDepartamento = descDepartamento;
		this.nomeEmpregado = nomeEmpregado;
	}

	public String getNumDepartamento() {
		return numDepartamento;
	}

	public void setNumDepartamento(String numDepartamento) {
		this.numDepartamento = numDepartamento;
	}

	public String getDescDepartamento() {
		return descDepartamento;
	}

	public void setDescDepartamento(String descDepartamento) {
		this.descDepartamento = descDepartamento;
	}

	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}

	@Override
	public String toString() {
		return "ConsultaLetraK [numDepartamento=" + numDepartamento
				+ ", descDepartamento=" + descDepartamento + ", nomeEmpregado="
				+ nomeEmpregado + "]";
	}

}
