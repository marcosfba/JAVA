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
public class ConsultaLetraL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Colunas(nome = "Nº Departamento", size = 175)
	private String numDepartamento;

	@Colunas(nome = "Descrição do Departamento", size = 175)
	private String descDepartamento;

	public ConsultaLetraL() {
		super();
	}

	public ConsultaLetraL(String numDepartamento, String descDepartamento) {
		super();
		this.numDepartamento = numDepartamento;
		this.descDepartamento = descDepartamento;
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

	@Override
	public String toString() {
		return "ConsultaLetraL [numDepartamento=" + numDepartamento
				+ ", descDepartamento=" + descDepartamento + "]";
	}

}
