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
public class ConsultaLetraD implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Colunas(nome = "Nome Dependente", size = 225)
	private String nomeDependente;

	@Colunas(nome = "Nome Empregado", size = 225)
	private String nomeEmpregado;

	public ConsultaLetraD() {
		super();
	}

	public ConsultaLetraD(String nomeDependente, String nomeEmpregado) {
		super();
		this.nomeDependente = nomeDependente;
		this.nomeEmpregado = nomeEmpregado;
	}

	public String getNomeDependente() {
		return nomeDependente;
	}

	public void setNomeDependente(String nomeDependente) {
		this.nomeDependente = nomeDependente;
	}

	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}

	@Override
	public String toString() {
		return "ConsultaLetraD [nomeDependente=" + nomeDependente
				+ ", nomeEmpregado=" + nomeEmpregado + "]";
	}

}
