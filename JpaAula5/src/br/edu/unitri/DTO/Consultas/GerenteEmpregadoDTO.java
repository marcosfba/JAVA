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
public class GerenteEmpregadoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Colunas(nome = "Nome do Empregado", size = 275)
	private String nomeEmpregado;
	
	@Colunas(nome = "Nome do Gerente", size = 275)
	private String nomeGerente;

	public GerenteEmpregadoDTO() {
		super();
	}

	public GerenteEmpregadoDTO(String nomeEmpregado, String nomeGerente) {
		super();
		this.nomeEmpregado = nomeEmpregado;
		this.nomeGerente = nomeGerente;
	}

	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}

	public String getNomeGerente() {
		return nomeGerente;
	}

	public void setNomeGerente(String nomeGerente) {
		this.nomeGerente = nomeGerente;
	}

}
