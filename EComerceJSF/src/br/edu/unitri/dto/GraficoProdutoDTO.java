/**
 * 
 */
package br.edu.unitri.dto;

import java.io.Serializable;

/**
 * @author Marcos
 *
 */
public class GraficoProdutoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long quantidade;
	private String descricao;

	public GraficoProdutoDTO() {
		super();
	}

	public GraficoProdutoDTO(long quantidade, String descricao) {
		super();
		this.quantidade = quantidade;
		this.descricao = descricao;
	}

	public long getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(long quantidade) {
		this.quantidade = quantidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
