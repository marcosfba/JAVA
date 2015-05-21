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
	@Colunas(nome = "Cod-Barras", size = 150)
	private String codBarras;
	@Colunas(nome = "Valor do Produto", size = 150)
	private BigDecimal valorProd;

	public Produto() {
		super();
	}

	public Produto(String nomeProduto, String codBarras, BigDecimal valorProd) {
		super();
		this.nomeProduto = nomeProduto;
		this.codBarras = codBarras;
		this.valorProd = valorProd;
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

	public String getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	public BigDecimal getValorProd() {
		return valorProd;
	}

	public void setValorProd(BigDecimal valorProd) {
		this.valorProd = valorProd;
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
		return "Produto [codProduto=" + codProduto + ", nomeProduto="
				+ nomeProduto + ", codBarras=" + codBarras + ", valorProd="
				+ valorProd + "]";
	}

}
