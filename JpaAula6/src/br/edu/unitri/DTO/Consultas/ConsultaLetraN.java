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
public class ConsultaLetraN implements Serializable {

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
	
	@Colunas(nome = "Nome do Projeto", size = 175)
	private String nomeProjeto;

	@Colunas(nome = "Nome do Gerente", size = 175)
	private String nomeGerente;

	public ConsultaLetraN() {
		super();
	}

	public ConsultaLetraN(String nomeEmpregado, String numDepartamento,
			String nomeDepartamento, String nomeProjeto, String nomeGerente) {
		super();
		this.nomeEmpregado = nomeEmpregado;
		this.numDepartamento = numDepartamento;
		this.nomeDepartamento = nomeDepartamento;
		this.nomeProjeto = nomeProjeto;
		this.nomeGerente = nomeGerente;
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

	public String getNomeGerente() {
		return nomeGerente;
	}

	public void setNomeGerente(String nomeGerente) {
		this.nomeGerente = nomeGerente;
	}

	@Override
	public String toString() {
		return "ConsultaLetraN [nomeEmpregado=" + nomeEmpregado
				+ ", numDepartamento=" + numDepartamento
				+ ", nomeDepartamento=" + nomeDepartamento + ", nomeProjeto="
				+ nomeProjeto + ", nomeGerente=" + nomeGerente + "]";
	}

}
