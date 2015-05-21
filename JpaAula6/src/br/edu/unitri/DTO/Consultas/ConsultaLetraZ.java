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
public class ConsultaLetraZ implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Colunas(nome = "Nº Departamento", size = 175)
	private String numDepartamento;
	
	@Colunas(nome = "Nome Departamento", size = 175)
	private String nomeDepartamento;
	
	@Colunas(nome = "Quantidade", size = 175)
	private Long qtd;

	public ConsultaLetraZ() {
		super();
	}

	public ConsultaLetraZ(String numDepartamento, String nomeDepartamento,
			Long qtd) {
		super();
		this.numDepartamento = numDepartamento;
		this.nomeDepartamento = nomeDepartamento;
		this.qtd = qtd;
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

	public Long getQtd() {
		return qtd;
	}

	public void setQtd(Long qtd) {
		this.qtd = qtd;
	}

	@Override
	public String toString() {
		return "ConsultaLetraZ [numDepartamento=" + numDepartamento
				+ ", nomeDepartamento=" + nomeDepartamento + ", qtd=" + qtd
				+ "]";
	}

}
