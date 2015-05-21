/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author marcos.fernando
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
	private long codProduto;

	@Colunas(nome = "Descrição do Produto", size = 150)
	private String nomeProduto;
	@Colunas(nome = "Valor do Produto", size = 150)
	private BigDecimal valorProd;
	@Colunas(nome = "Quantidade", size = 150)
	private int quantidade;

	private String imagem;;

	@OneToOne
	@JoinColumn(name = "idCategoria", referencedColumnName = "codCategoria")
	@Colunas(nome = "Descrição da Categoria", size = 175)
	private Categoria categoria;

	public Produto() {
		super();
	}

	public Produto(String nomeProduto, BigDecimal valorProd, int quantidade, Categoria categoria) {
		super();
		this.nomeProduto = nomeProduto;
		this.valorProd = valorProd;
		this.quantidade = quantidade;
		this.categoria = categoria;
	}

	public long getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(long codProduto) {
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

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (codProduto ^ (codProduto >>> 32));
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
		if (codProduto != other.codProduto)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  codProduto + "--" + nomeProduto;
	}

}
