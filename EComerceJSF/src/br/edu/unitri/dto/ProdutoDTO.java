/**
 * 
 */
package br.edu.unitri.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import br.edu.unitri.model.Categoria;
import br.edu.unitri.model.Marca;

/**
 * @author Marcos
 *
 */
public class ProdutoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idProduto;
	private String codigo;
	private String codBarras;
	private String descricao;
	private String unidade;
	private String complemento;
	private Categoria categoria;
	private Marca marca;
	private BigDecimal vlrCusto;
	private BigDecimal vlrVenda;
	private int qtdEstoque;
	private String docFabricante;
	private String nomeImagem;
	private String extensao;
	private String fotoOriginal;
	private String fotoTumb;

	public ProdutoDTO() {
		super();
	}

	public ProdutoDTO(Long idProduto, String codigo, String codBarras,
			String descricao, String unidade, String complemento,
			Categoria categoria, Marca marca, BigDecimal vlrCusto,
			BigDecimal vlrVenda, int qtdEstoque, String docFabricante,
			String nomeImagem, String extensao, String fotoOriginal,
			String fotoTumb) {
		super();
		this.idProduto = idProduto;
		this.codigo = codigo;
		this.codBarras = codBarras;
		this.descricao = descricao;
		this.unidade = unidade;
		this.complemento = complemento;
		this.categoria = categoria;
		this.marca = marca;
		this.vlrCusto = vlrCusto;
		this.vlrVenda = vlrVenda;
		this.qtdEstoque = qtdEstoque;
		this.docFabricante = docFabricante;
		this.nomeImagem = nomeImagem;
		this.extensao = extensao;
		this.fotoOriginal = fotoOriginal;
		this.fotoTumb = fotoTumb;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public BigDecimal getVlrCusto() {
		return vlrCusto;
	}

	public void setVlrCusto(BigDecimal vlrCusto) {
		this.vlrCusto = vlrCusto;
	}

	public BigDecimal getVlrVenda() {
		return vlrVenda;
	}

	public void setVlrVenda(BigDecimal vlrVenda) {
		this.vlrVenda = vlrVenda;
	}

	public int getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public String getDocFabricante() {
		return docFabricante;
	}

	public void setDocFabricante(String docFabricante) {
		this.docFabricante = docFabricante;
	}

	public String getNomeImagem() {
		return nomeImagem;
	}

	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public String getFotoOriginal() {
		return fotoOriginal;
	}

	public void setFotoOriginal(String fotoOriginal) {
		this.fotoOriginal = fotoOriginal;
	}

	public String getFotoTumb() {
		return fotoTumb;
	}

	public void setFotoTumb(String fotoTumb) {
		this.fotoTumb = fotoTumb;
	}

	@Override
	public String toString() {
		return "ProdutoDTO [idProduto=" + idProduto + ", codigo=" + codigo
				+ ", codBarras=" + codBarras + ", descricao=" + descricao
				+ ", unidade=" + unidade + ", complemento=" + complemento
				+ ", categoria=" + categoria + ", marca=" + marca
				+ ", vlrCusto=" + vlrCusto + ", vlrVenda=" + vlrVenda
				+ ", qtdEstoque=" + qtdEstoque + ", docFabricante="
				+ docFabricante + ", nomeImagem=" + nomeImagem + ", extensao="
				+ extensao + ", fotoOriginal=" + fotoOriginal + ", fotoTumb="
				+ fotoTumb + "]";
	}
	

}
