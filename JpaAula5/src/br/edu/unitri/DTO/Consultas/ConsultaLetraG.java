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
public class ConsultaLetraG implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Colunas(nome = "Nome do  Departamento", size = 175)
	private String nomeDepartamento;
	
	@Colunas(nome = "Nº do Projeto", size = 175)
	private String numProjeto;

	public ConsultaLetraG() {
		super();
	}

	public ConsultaLetraG(String nomeDepartamento, String numProjeto) {
		super();
		this.nomeDepartamento = nomeDepartamento;
		this.numProjeto = numProjeto;
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
		return "ConsultaLetraG [nomeDepartamento=" + nomeDepartamento
				+ ", numProjeto=" + numProjeto + "]";
	}

}
