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
public class ProdutoGeralDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long idProduto;
	private String codInterno;
	private String codBarras;
	private String descProduto;
	private String unidade;
	private String complemento;
	private String nomeCategoria;
	private String compCategoria;
	private String nomeMarca;
	private String compMarca;
	private BigDecimal valorCusto;
	private BigDecimal valorVenda;
	private String docFabricante;
	private String fotoProduto;

	public ProdutoGeralDTO() {
		super();
	}

	public ProdutoGeralDTO(long idProduto, String codInterno, String codBarras,
			String descProduto, String unidade, String complemento,
			String nomeCategoria, String compCategoria, String nomeMarca,
			String compMarca, BigDecimal valorCusto, BigDecimal valorVenda,
			String docFabricante, String fotoProduto) {
		super();
		this.idProduto = idProduto;
		this.codInterno = codInterno;
		this.codBarras = codBarras;
		this.descProduto = descProduto;
		this.unidade = unidade;
		this.complemento = complemento;
		this.nomeCategoria = nomeCategoria;
		this.compCategoria = compCategoria;
		this.nomeMarca = nomeMarca;
		this.compMarca = compMarca;
		this.valorCusto = valorCusto;
		this.valorVenda = valorVenda;
		this.docFabricante = docFabricante;
		this.fotoProduto = fotoProduto;
	}

	public long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(long idProduto) {
		this.idProduto = idProduto;
	}

	public String getCodInterno() {
		return codInterno;
	}

	public void setCodInterno(String codInterno) {
		this.codInterno = codInterno;
	}

	public String getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	public String getDescProduto() {
		return descProduto;
	}

	public void setDescProduto(String descProduto) {
		this.descProduto = descProduto;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public String getCompCategoria() {
		return compCategoria;
	}

	public void setCompCategoria(String compCategoria) {
		this.compCategoria = compCategoria;
	}

	public String getNomeMarca() {
		return nomeMarca;
	}

	public void setNomeMarca(String nomeMarca) {
		this.nomeMarca = nomeMarca;
	}

	public String getCompMarca() {
		return compMarca;
	}

	public void setCompMarca(String compMarca) {
		this.compMarca = compMarca;
	}

	public BigDecimal getValorCusto() {
		return valorCusto;
	}

	public void setValorCusto(BigDecimal valorCusto) {
		this.valorCusto = valorCusto;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public String getDocFabricante() {
		return docFabricante;
	}

	public void setDocFabricante(String docFabricante) {
		this.docFabricante = docFabricante;
	}

	public String getFotoProduto() {
		return fotoProduto;
	}

	public void setFotoProduto(String fotoProduto) {
		this.fotoProduto = fotoProduto;
	}

}
