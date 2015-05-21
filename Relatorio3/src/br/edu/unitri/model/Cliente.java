/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author marcos.fernando
 *
 */
@Entity
@Table(name = "tbCliente")
public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codCliente;

	@Colunas(nome = "Nome do Cliente", size = 150)
	private String nomeCliente;
	@Colunas(nome = "Descrição do Cargo", size = 125)
	private String descCargo;
	@Colunas(nome = "CEP", size = 100)
	private String descCep;
	@Colunas(nome = "Cidade", size = 135)
	private String descCidade;
	@Colunas(nome = "Endereço/Logradouro", size = 175)
	private String descEndereco;
	@Colunas(nome = "Nº Fax", size = 125)
	private String descFax;
	@Colunas(nome = "Nº Telefone", size = 100)
	private String descTelefone;
	@Colunas(nome = "País", size = 125)
	private String descPais;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "idCliente", referencedColumnName = "codCliente")
	private Collection<Pedido> pedidos;


	public Cliente() {
		super();
	}

	public Cliente(String nomeCliente, String descCargo, String descCep, String descCidade, String descEndereco,
			String descFax, String descTelefone, String descPais) {
		super();
		this.nomeCliente = nomeCliente;
		this.descCargo = descCargo;
		this.descCep = descCep;
		this.descCidade = descCidade;
		this.descEndereco = descEndereco;
		this.descFax = descFax;
		this.descTelefone = descTelefone;
		this.descPais = descPais;
	}

	public long getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(long codCliente) {
		this.codCliente = codCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getDescCargo() {
		return descCargo;
	}

	public void setDescCargo(String descCargo) {
		this.descCargo = descCargo;
	}

	public String getDescCep() {
		return descCep;
	}

	public void setDescCep(String descCep) {
		this.descCep = descCep;
	}

	public String getDescCidade() {
		return descCidade;
	}

	public void setDescCidade(String descCidade) {
		this.descCidade = descCidade;
	}

	public String getDescEndereco() {
		return descEndereco;
	}

	public void setDescEndereco(String descEndereco) {
		this.descEndereco = descEndereco;
	}

	public String getDescFax() {
		return descFax;
	}

	public void setDescFax(String descFax) {
		this.descFax = descFax;
	}

	public String getDescTelefone() {
		return descTelefone;
	}

	public void setDescTelefone(String descTelefone) {
		this.descTelefone = descTelefone;
	}

	public String getDescPais() {
		return descPais;
	}

	public void setDescPais(String descPais) {
		this.descPais = descPais;
	}

	public Collection<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(Collection<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (codCliente ^ (codCliente >>> 32));
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
		Cliente other = (Cliente) obj;
		if (codCliente != other.codCliente)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.valueOf(codCliente) + "--" + nomeCliente;
	}

}
