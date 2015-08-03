/**
 * 
 */
package br.edu.unitri.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author marcos.fernando
 *
 */
public class ProdutoCategoriaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codProduto;
	private String nomeProduto;
	private BigDecimal valorProd;
	private BigDecimal valorCusto;
	private String descCategoria;
	private String compCategoria;
	private long idCategoria;
	private String unidade;

	public ProdutoCategoriaDTO() {
		super();
	}

	public ProdutoCategoriaDTO(String codProduto, String nomeProduto,
			BigDecimal valorProd, BigDecimal valorCusto, String descCategoria,
			String compCategoria, long idCategoria, String unidade) {
		super();
		this.codProduto = codProduto;
		this.nomeProduto = nomeProduto;
		this.valorProd = valorProd;
		this.valorCusto = valorCusto;
		this.descCategoria = descCategoria;
		this.compCategoria = compCategoria;
		this.idCategoria = idCategoria;
		this.unidade = unidade;
	}

	public String getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(String codProduto) {
		this.codProduto = codProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public BigDecimal getValorProd() {
		return valorProd;
	}

	public void setValorProd(BigDecimal valorProd) {
		this.valorProd = valorProd;
	}

	public BigDecimal getValorCusto() {
		return valorCusto;
	}

	public void setValorCusto(BigDecimal valorCusto) {
		this.valorCusto = valorCusto;
	}

	public String getDescCategoria() {
		return descCategoria;
	}

	public void setDescCategoria(String descCategoria) {
		this.descCategoria = descCategoria;
	}

	public String getCompCategoria() {
		return compCategoria;
	}

	public void setCompCategoria(String compCategoria) {
		this.compCategoria = compCategoria;
	}

	public long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

}
