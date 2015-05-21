/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author marcos.fernando
 *
 */
public class ClientesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Colunas(nome = "Cód. Cliente", size = 150)
	private long codCliente;
	
	@Colunas(nome = "Nome do Cliente", size = 150)
	private String nomeCliente;
	
	@Colunas(nome = "Qtd Pedidos", size = 100)
	private int qtdPedidos;
	
	private int ano;
	private BigDecimal vlrTotal;
	

	public ClientesDTO() {
		super();
	}

	public ClientesDTO(long codCliente, String nomeCliente, int qtdPedidos) {
		super();
		this.codCliente = codCliente;
		this.nomeCliente = nomeCliente;
		this.qtdPedidos = qtdPedidos;
	}
	
	public ClientesDTO(long codCliente, String nomeCliente, int qtdPedidos, int ano) {
		super();
		this.codCliente = codCliente;
		this.nomeCliente = nomeCliente;
		this.qtdPedidos = qtdPedidos;
		this.ano = ano;
	}
	
	public ClientesDTO(long codCliente, String nomeCliente, int qtdPedidos, int ano, BigDecimal vlrTotal) {
		super();
		this.codCliente = codCliente;
		this.nomeCliente = nomeCliente;
		this.qtdPedidos = qtdPedidos;
		this.ano = ano;
		this.vlrTotal = vlrTotal;
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

	public int getQtdPedidos() {
		return qtdPedidos;
	}

	public void setQtdPedidos(int qtdPedidos) {
		this.qtdPedidos = qtdPedidos;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public BigDecimal getVlrTotal() {
		return vlrTotal;
	}

	public void setVlrTotal(BigDecimal vlrTotal) {
		this.vlrTotal = vlrTotal;
	}

	@Override
	public String toString() {
		return "ClientesDTO [codCliente=" + codCliente + ", nomeCliente=" + nomeCliente + ", qtdPedidos=" + qtdPedidos
				+ ", ano=" + ano + ", vlrTotal=" + vlrTotal + "]";
	}
	
}
