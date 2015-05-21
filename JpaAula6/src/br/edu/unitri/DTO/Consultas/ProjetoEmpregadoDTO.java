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
public class ProjetoEmpregadoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Colunas(nome = "Nome do Empregado", size = 250)
	private String nomeEmpregado;

	@Colunas(nome = "Nome do Departamento do Empregado", size = 250)
	private String nomeDepartamentoEmpregado;

	@Colunas(nome = "Numero do Projeto", size = 175)
	private String numProjeto;

	@Colunas(nome = "Descrição do Projeto", size = 225)
	private String descProjeto;

	@Colunas(nome = "Nome do Local", size = 225)
	private String nomeLocal;

	@Colunas(nome = "Nome do Departamento Projeto", size = 225)
	private String nomeDepartamentoProjeto;

	public ProjetoEmpregadoDTO() {
		super();
	}

	public ProjetoEmpregadoDTO(String nomeEmpregado,
			String nomeDepartamentoEmpregado, String numProjeto,
			String descProjeto, String nomeLocal, String nomeDepartamentoProjeto) {
		super();
		this.nomeEmpregado = nomeEmpregado;
		this.nomeDepartamentoEmpregado = nomeDepartamentoEmpregado;
		this.numProjeto = numProjeto;
		this.descProjeto = descProjeto;
		this.nomeLocal = nomeLocal;
		this.nomeDepartamentoProjeto = nomeDepartamentoProjeto;
	}

	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}

	public String getNomeDepartamentoEmpregado() {
		return nomeDepartamentoEmpregado;
	}

	public void setNomeDepartamentoEmpregado(String nomeDepartamentoEmpregado) {
		this.nomeDepartamentoEmpregado = nomeDepartamentoEmpregado;
	}

	public String getNumProjeto() {
		return numProjeto;
	}

	public void setNumProjeto(String numProjeto) {
		this.numProjeto = numProjeto;
	}

	public String getDescProjeto() {
		return descProjeto;
	}

	public void setDescProjeto(String descProjeto) {
		this.descProjeto = descProjeto;
	}

	public String getNomeLocal() {
		return nomeLocal;
	}

	public void setNomeLocal(String nomeLocal) {
		this.nomeLocal = nomeLocal;
	}

	public String getNomeDepartamentoProjeto() {
		return nomeDepartamentoProjeto;
	}

	public void setNomeDepartamentoProjeto(String nomeDepartamentoProjeto) {
		this.nomeDepartamentoProjeto = nomeDepartamentoProjeto;
	}

}
