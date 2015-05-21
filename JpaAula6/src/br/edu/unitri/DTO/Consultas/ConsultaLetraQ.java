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
public class ConsultaLetraQ implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Colunas(nome = "Código do Local", size = 175)
	private String codLocal;
	
	@Colunas(nome = "Descrição do Local", size = 175)
	private String nomeLocal;
	
	@Colunas(nome = "Cód Departamento", size = 175)
	private String codDepartamento;

	@Colunas(nome = "Nome do Departamento", size = 175)
	private String nomeDepartamento;

	public ConsultaLetraQ() {
		super();
	}

	public ConsultaLetraQ(String codLocal, String nomeLocal,
			String codDepartamento, String nomeDepartamento) {
		super();
		this.codLocal = codLocal;
		this.nomeLocal = nomeLocal;
		this.codDepartamento = codDepartamento;
		this.nomeDepartamento = nomeDepartamento;
	}

	public String getCodLocal() {
		return codLocal;
	}

	public void setCodLocal(String codLocal) {
		this.codLocal = codLocal;
	}

	public String getNomeLocal() {
		return nomeLocal;
	}

	public void setNomeLocal(String nomeLocal) {
		this.nomeLocal = nomeLocal;
	}

	public String getCodDepartamento() {
		return codDepartamento;
	}

	public void setCodDepartamento(String codDepartamento) {
		this.codDepartamento = codDepartamento;
	}

	public String getNomeDepartamento() {
		return nomeDepartamento;
	}

	public void setNomeDepartamento(String nomeDepartamento) {
		this.nomeDepartamento = nomeDepartamento;
	}

	@Override
	public String toString() {
		return "ConsultaLetraQ [codLocal=" + codLocal + ", nomeLocal="
				+ nomeLocal + ", codDepartamento=" + codDepartamento
				+ ", nomeDepartamento=" + nomeDepartamento + "]";
	}

}
