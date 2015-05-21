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
public class ConsultaLetraE implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Colunas(nome = "Nº Projeto", size = 175)
	private String numProjeto;
	
	@Colunas(nome = "Código Local", size = 175)
	private String codLocal;

	@Colunas(nome = "Descrição Local", size = 225)
	private String nomeLocal;

	public ConsultaLetraE() {
		super();
	}

	public ConsultaLetraE(String numProjeto, String codLocal, String nomeLocal) {
		super();
		this.numProjeto = numProjeto;
		this.codLocal = codLocal;
		this.nomeLocal = nomeLocal;
	}

	public String getNumProjeto() {
		return numProjeto;
	}

	public void setNumProjeto(String numProjeto) {
		this.numProjeto = numProjeto;
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

	@Override
	public String toString() {
		return "ConsultaLetraE [numProjeto=" + numProjeto + ", codLocal="
				+ codLocal + ", nomeLocal=" + nomeLocal + "]";
	}

}
