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
public class DepartamentoLocalDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Colunas(nome = "Descrição do Departamento", size = 275)
	private String nomeDepartamento;
	@Colunas(nome = "Descrição do Local", size = 275)
	private String nomeLocal;

	public DepartamentoLocalDTO() {
		super();
	}

	public DepartamentoLocalDTO(String nomeDepartamento, String nomeLocal) {
		super();
		this.nomeDepartamento = nomeDepartamento;
		this.nomeLocal = nomeLocal;
	}

	public String getNomeDepartamento() {
		return nomeDepartamento;
	}

	public void setNomeDepartamento(String nomeDepartamento) {
		this.nomeDepartamento = nomeDepartamento;
	}

	public String getNomeLocal() {
		return nomeLocal;
	}

	public void setNomeLocal(String nomeLocal) {
		this.nomeLocal = nomeLocal;
	}

}
