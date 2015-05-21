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
public class ConsultaLetraP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Colunas(nome = "Nome do Gerente", size = 175)
	private String nomeGerente;

	@Colunas(nome = "Nº do Departamento", size = 175)
	private String numDepartamento;

	@Colunas(nome = "Nome do Departamento", size = 175)
	private String nomeDepartamento;

	public ConsultaLetraP() {
		super();
	}

	public ConsultaLetraP(String nomeGerente, String numDepartamento,
			String nomeDepartamento) {
		super();
		this.nomeGerente = nomeGerente;
		this.numDepartamento = numDepartamento;
		this.nomeDepartamento = nomeDepartamento;
	}

	public String getNomeGerente() {
		return nomeGerente;
	}

	public void setNomeGerente(String nomeGerente) {
		this.nomeGerente = nomeGerente;
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
		return "ConsultaLetraP [nomeGerente=" + nomeGerente
				+ ", numDepartamento=" + numDepartamento
				+ ", nomeDepartamento=" + nomeDepartamento + "]";
	}

}
