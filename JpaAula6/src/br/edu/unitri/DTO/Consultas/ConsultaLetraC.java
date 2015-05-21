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
public class ConsultaLetraC implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Colunas(nome = "Nome Departamento", size = 225)
	private String nomeDepartamento;
	
	@Colunas(nome = "Nome Empregado", size = 225)
	private String nomeEmpregado;

	public ConsultaLetraC() {
		super();
	}

	public ConsultaLetraC(String nomeEmpregado, String nomeDepartamento) {
		super();
		this.nomeDepartamento = nomeDepartamento;
		this.nomeEmpregado = nomeEmpregado;
	}

	public String getNomeDepartamento() {
		return nomeDepartamento;
	}

	public void setNomeDepartamento(String nomeDepartamento) {
		this.nomeDepartamento = nomeDepartamento;
	}

	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}

	@Override
	public String toString() {
		return "ConsultaLetraC [nomeDepartamento=" + nomeDepartamento
				+ ", nomeEmpregado=" + nomeEmpregado + "]";
	}

}
