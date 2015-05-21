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
public class ConsultaLetraI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Colunas(nome = "Código Empregado", size = 175)
	private String codEmpregado;
	
	@Colunas(nome = "Nome do  Empregado", size = 175)
	private String nomeEmpregado;

	@Colunas(nome = "Nº do Projeto", size = 175)
	private String numProjeto;

	public ConsultaLetraI() {
		super();
	}

	public ConsultaLetraI(String codEmpregado, String nomeEmpregado,
			String numProjeto) {
		super();
		this.codEmpregado = codEmpregado;
		this.nomeEmpregado = nomeEmpregado;
		this.numProjeto = numProjeto;
	}

	public String getCodEmpregado() {
		return codEmpregado;
	}

	public void setCodEmpregado(String codEmpregado) {
		this.codEmpregado = codEmpregado;
	}

	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}

	public String getNumProjeto() {
		return numProjeto;
	}

	public void setNumProjeto(String numProjeto) {
		this.numProjeto = numProjeto;
	}

	@Override
	public String toString() {
		return "ConsultaLetraI [codEmpregado=" + codEmpregado
				+ ", nomeEmpregado=" + nomeEmpregado + ", numProjeto="
				+ numProjeto + "]";
	}

}
