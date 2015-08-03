/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author MARCOS FERNANDO
 *
 */
@Entity
@Table(name = "tbProduto")
public class Produto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CODINT", length = 25, nullable = true)
	private String codigo;

	@Column(name = "CODEAN", length = 14, nullable = true)
	private String codBarras;

	@Column(name = "DESCRICAO", length = 100, nullable = true)
	private String descricao;

	@Column(name = "UNIDADE", length = 6)
	private String unidade;

	@Column(name = "CPL", length = 75)
	private String complemento;

	@OneToOne
	@JoinColumn(name = "idCategoria", referencedColumnName = "id")
	private Categoria categoria;

	@OneToOne
	@JoinColumn(name = "idMarca", referencedColumnName = "id")
	private Marca marca;

	@Column(name = "VLRCUSTO", precision = 18, scale = 2)
	private BigDecimal vlrCusto;

	@Column(name = "VLRVENDA", precision = 18, scale = 2)
	private BigDecimal vlrVenda;

	@Column(name = "ESTOQUE")
	private int qtdEstoque;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "produto", cascade=CascadeType.REMOVE)
	private Collection<Foto> listaFotos;

	@Column(name = "DOCFABRIC")
	private String docFabricante;

	public Produto() {
		super();
	}

	public Produto(Long id, String codigo, String codBarras, String descricao,
			String unidade, String complemento, Categoria categoria,
			Marca marca, BigDecimal vlrCusto, BigDecimal vlrVenda,
			int qtdEstoque, String docFabricante) {
		super();
		this.id = id;
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
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Collection<Foto> getListaFotos() {
		return listaFotos;
	}

	public void setListaFotos(Collection<Foto> listaFotos) {
		this.listaFotos = listaFotos;
	}

	public String getDocFabricante() {
		return docFabricante;
	}

	public void setDocFabricante(String docFabricante) {
		this.docFabricante = docFabricante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
