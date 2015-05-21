/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "tb_ItensPedido")
public class ItensPedido implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codItemPedido;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pedido_id", referencedColumnName = "codPedido")
	@Colunas(nome = "Código do Pedido", size = 150)
	private Pedido pedido;

	@OneToOne
	@JoinColumn(name = "produto_id", referencedColumnName = "codProduto")
	@Colunas(nome = "Código do Produto", size = 150)
	private Produto produto;

	@Colunas(nome = "Quantidade", size = 150)
	private int QtdItem;

	@Colunas(nome = "Valor do Item", size = 175)
	private BigDecimal VlrItem;

	@Colunas(nome = "Valor do Desconto", size = 175)
	private BigDecimal VlrDesconto;

	public ItensPedido() {
		super();
	}

	public ItensPedido(Pedido pedido, Produto produto, int qtdItem, BigDecimal vlrItem, BigDecimal vlrDesconto) {
		super();
		this.pedido = pedido;
		this.produto = produto;
		this.QtdItem = qtdItem;
		this.VlrItem = vlrItem;
		this.VlrDesconto = vlrDesconto;
	}

	public long getCodItemPedido() {
		return codItemPedido;
	}

	public void setCodItemPedido(long codItemPedido) {
		this.codItemPedido = codItemPedido;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQtdItem() {
		return QtdItem;
	}

	public void setQtdItem(int qtdItem) {
		QtdItem = qtdItem;
	}

	public BigDecimal getVlrItem() {
		return VlrItem;
	}

	public void setVlrItem(BigDecimal vlrItem) {
		VlrItem = vlrItem;
	}

	public BigDecimal getVlrDesconto() {
		return VlrDesconto;
	}

	public void setVlrDesconto(BigDecimal vlrDesconto) {
		VlrDesconto = vlrDesconto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (codItemPedido ^ (codItemPedido >>> 32));
		result = prime * result + ((pedido == null) ? 0 : pedido.hashCode());
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
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
		ItensPedido other = (ItensPedido) obj;
		if (codItemPedido != other.codItemPedido)
			return false;
		if (pedido == null) {
			if (other.pedido != null)
				return false;
		} else if (!pedido.equals(other.pedido))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItensPedido [codItemPedido=" + codItemPedido + ", pedido=" + pedido + ", produto=" + produto
				+ ", QtdItem=" + QtdItem + ", VlrItem=" + VlrItem + ", VlrDesconto=" + VlrDesconto + "]";
	}

}
