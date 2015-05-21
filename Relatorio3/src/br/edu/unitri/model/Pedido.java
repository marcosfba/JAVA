/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author marcos.fernando
 *
 */
@Entity
@Table(name = "tbPedido")
public class Pedido implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Colunas(nome = "Código Pedido", size = 100)
	private long codPedido;

	@Temporal(TemporalType.DATE)
	@Colunas(nome = "Data do Pedido", size = 150)
	private Date dtPedido;

	@OneToOne
	@JoinColumn(name = "idCliente", referencedColumnName = "codCliente")
	@Colunas(nome = "Cód/Nome Cliente", size = 150)
	private Cliente cliente;

	@Colunas(nome = "Observação", size = 150)
	private String Observacao;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "pedido_id", referencedColumnName = "codPedido")
	private Collection<ItensPedido> itens;

	public Pedido() {
		super();
	}

	public Pedido(Date dtPedido, Cliente cliente, String observacao) {
		super();
		this.dtPedido = dtPedido;
		this.cliente = cliente;
		this.Observacao = observacao;
	}

	public long getCodPedido() {
		return codPedido;
	}

	public void setCodPedido(long codPedido) {
		this.codPedido = codPedido;
	}

	public Date getDtPedido() {
		return dtPedido;
	}

	public void setDtPedido(Date dtPedido) {
		this.dtPedido = dtPedido;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Collection<ItensPedido> getItens() {
		return itens;
	}

	public void setItens(Collection<ItensPedido> itens) {
		this.itens = itens;
	}

	public String getObservacao() {
		return Observacao;
	}

	public void setObservacao(String observacao) {
		Observacao = observacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (codPedido ^ (codPedido >>> 32));
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
		Pedido other = (Pedido) obj;
		if (codPedido != other.codPedido)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.valueOf(codPedido);
	}

}
