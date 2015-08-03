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
public class ProdutoMarcaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codProduto;
	private String nomeProduto;
	private BigDecimal valorProd;
	private BigDecimal valorCusto;
	private String descMarca;
	private String compMarca;
	private long idMarca;
	private String unidade;

	public ProdutoMarcaDTO() {
		super();
	}

	public ProdutoMarcaDTO(String codProduto, String nomeProduto,
			BigDecimal valorProd, BigDecimal valorCusto, String descMarca,
			String compMarca, long idMarca, String unidade) {
		super();
		this.codProduto = codProduto;
		this.nomeProduto = nomeProduto;
		this.valorProd = valorProd;
		this.valorCusto = valorCusto;
		this.descMarca = descMarca;
		this.compMarca = compMarca;
		this.idMarca = idMarca;
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

	public String getDescMarca() {
		return descMarca;
	}

	public void setDescMarca(String descMarca) {
		this.descMarca = descMarca;
	}

	public String getCompMarca() {
		return compMarca;
	}

	public void setCompMarca(String compMarca) {
		this.compMarca = compMarca;
	}

	public long getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(long idMarca) {
		this.idMarca = idMarca;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

}
