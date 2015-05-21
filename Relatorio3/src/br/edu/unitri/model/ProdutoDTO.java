/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author marcos.fernando
 *
 */
public class ProdutoDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Produto produto;
	private BigDecimal valor;
	private Long itens;
	private int mes;

	public ProdutoDTO() {
		super();
	}

	public ProdutoDTO(Produto produto, BigDecimal valor, Long itens, int mes) {
		super();
		this.produto = produto;
		this.valor = valor;
		this.itens = itens;
		this.mes = mes;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Long getItens() {
		return itens;
	}

	public void setItens(Long itens) {
		this.itens = itens;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	@Override
	public String toString() {
		return "ProdutoDTO [produto=" + produto + ", valor=" + valor + ", itens=" + itens + ", mes=" + mes + "]";
	}

}
