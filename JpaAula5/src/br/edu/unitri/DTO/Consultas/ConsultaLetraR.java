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
public class ConsultaLetraR implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Colunas(nome = "Quantidade de Horas", size = 175)
	private String qtdHoras;

	@Colunas(nome = "Nome do Empregado", size = 175)
    private String nomeEmpregado;

	public ConsultaLetraR() {
		super();
	}

	public ConsultaLetraR(String qtdHoras, String nomeEmpregado) {
		super();
		this.qtdHoras = qtdHoras;
		this.nomeEmpregado = nomeEmpregado;
	}

	public String getQtdHoras() {
		return qtdHoras;
	}

	public void setQtdHoras(String qtdHoras) {
		this.qtdHoras = qtdHoras;
	}

	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}

	@Override
	public String toString() {
		return "ConsultaLetraR [qtdHoras=" + qtdHoras + ", nomeEmpregado="
				+ nomeEmpregado + "]";
	}

}
