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
public class ConsultaLetraO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Colunas(nome = "Nome do Empregado", size = 175)
	private String nomeEmpregado;
	
	@Colunas(nome = "Nº do Departamento", size = 175)
	private String numDepartamento;

	@Colunas(nome = "Nome do Departamento", size = 175)
	private String nomeDepartamento;

	public ConsultaLetraO() {
		super();
	}

	public ConsultaLetraO(String nomeEmpregado, String numDepartamento,
			String nomeDepartamento) {
		super();
		this.nomeEmpregado = nomeEmpregado;
		this.numDepartamento = numDepartamento;
		this.nomeDepartamento = nomeDepartamento;
	}

	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
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

	@Override
	public String toString() {
		return "ConsultaLetraO [nomeEmpregado=" + nomeEmpregado
				+ ", numDepartamento=" + numDepartamento
				+ ", nomeDepartamento=" + nomeDepartamento + "]";
	}

}
