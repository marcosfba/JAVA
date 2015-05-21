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
public class ConsultaLetraM implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Colunas(nome = "Nome do Empregado", size = 175)
	private String nomeEmpregado;
	
	@Colunas(nome = "Nº Departamento", size = 175)
	private String numDepartamento;
	
	@Colunas(nome = "Nome do Departamento", size = 175)
	private String nomeDepartamento;

	@Colunas(nome = "Nº Projeto", size = 175)
	private String nomeProjeto;

	public ConsultaLetraM() {
		super();
	}

	public ConsultaLetraM(String nomeEmpregado, String numDepartamento,
			String nomeDepartamento, String nomeProjeto) {
		super();
		this.nomeEmpregado = nomeEmpregado;
		this.numDepartamento = numDepartamento;
		this.nomeDepartamento = nomeDepartamento;
		this.nomeProjeto = nomeProjeto;
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

	public String getNomeProjeto() {
		return nomeProjeto;
	}

	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}

	@Override
	public String toString() {
		return "ConsultaLetraM [nomeEmpregado=" + nomeEmpregado
				+ ", numDepartamento=" + numDepartamento
				+ ", nomeDepartamento=" + nomeDepartamento + ", nomeProjeto="
				+ nomeProjeto + "]";
	}

}
