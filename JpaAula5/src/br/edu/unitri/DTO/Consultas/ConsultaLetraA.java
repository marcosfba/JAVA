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
public class ConsultaLetraA implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Colunas(nome = "Cód.Departamento", size = 175)
	private String codLocal;
	@Colunas(nome = "Nome Departamento", size = 275)
	private String nomeLocal;

	public ConsultaLetraA() {
		super();
	}

	public ConsultaLetraA(String codLocal, String nomeLocal) {
		super();
		this.codLocal = codLocal;
		this.nomeLocal = nomeLocal;
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
		return "ConsultaLetraA [codLocal=" + codLocal + ", nomeLocal="
				+ nomeLocal + "]";
	}

}
