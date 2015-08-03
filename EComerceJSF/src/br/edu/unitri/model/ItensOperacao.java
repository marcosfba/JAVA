/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author MARCOS FERNANDO
 *
 */
@Entity
@Table(name = "tbItensOperacao")
public class ItensOperacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operacao_id", referencedColumnName = "id")
	private Operacao operacao;

	@OneToOne
	@JoinColumn(name = "produto_id", referencedColumnName = "id")
	private Produto produto;

	@Column(name = "QTD")
	private int quantidade;

	@Column(name = "VLRITEM", precision = 18, scale = 2)
	private BigDecimal vlrItem;

	@Column(name = "VLRDESC", precision = 18, scale = 2)
	private BigDecimal vlrdesc;

	public ItensOperacao() {
		super();
	}

	public ItensOperacao(Long id, Operacao operacao, Produto produto,
			int quantidade, BigDecimal vlrItem, BigDecimal vlrdesc) {
		super();
		this.id = id;
		this.operacao = operacao;
		this.produto = produto;
		this.quantidade = quantidade;
		this.vlrItem = vlrItem;
		this.vlrdesc = vlrdesc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getVlrItem() {
		return vlrItem;
	}

	public void setVlrItem(BigDecimal vlrItem) {
		this.vlrItem = vlrItem;
	}

	public BigDecimal getVlrdesc() {
		return vlrdesc;
	}

	public void setVlrdesc(BigDecimal vlrdesc) {
		this.vlrdesc = vlrdesc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((operacao == null) ? 0 : operacao.hashCode());
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
		ItensOperacao other = (ItensOperacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (operacao == null) {
			if (other.operacao != null)
				return false;
		} else if (!operacao.equals(other.operacao))
			return false;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}
	
	


}
