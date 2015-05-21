/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author marcos.fernando
 *
 */
public class PedidoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Colunas(nome = "Código Pedido", size = 100)
	private long codPedido;
	@Colunas(nome = "Data do Pedido", size = 150)
	private Date dtPedido;
	@Colunas(nome = "Nome do Cliente", size = 150)
	private String nomeCliente;
	@Colunas(nome = "Qtd Itens", size = 100)
	private int qtdItens;
	private long codCliente;
	private BigDecimal vlrPedido;

	public PedidoDTO() {
		super();
	}

	public PedidoDTO(long codPedido, Date dtPedido, String nomeCliente) {
		super();
		this.codPedido = codPedido;
		this.dtPedido = dtPedido;
		this.nomeCliente = nomeCliente;
	}

	public PedidoDTO(long codPedido, Date dtPedido, String nomeCliente, int qtdItens) {
		super();
		this.codPedido = codPedido;
		this.dtPedido = dtPedido;
		this.nomeCliente = nomeCliente;
		this.qtdItens = qtdItens;
	}

	public PedidoDTO(long codPedido, Date dtPedido, String nomeCliente, int qtdItens, BigDecimal vlrPedido) {
		super();
		this.codPedido = codPedido;
		this.dtPedido = dtPedido;
		this.nomeCliente = nomeCliente;
		this.qtdItens = qtdItens;
		this.vlrPedido = vlrPedido;
	}

	public PedidoDTO(long codPedido, Date dtPedido, String nomeCliente, long codCliente, int qtdItens,
			BigDecimal vlrPedido) {
		super();
		this.codPedido = codPedido;
		this.dtPedido = dtPedido;
		this.nomeCliente = nomeCliente;
		this.qtdItens = qtdItens;
		this.codCliente = codCliente;
		this.vlrPedido = vlrPedido;
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

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public int getQtdItens() {
		return qtdItens;
	}

	public void setQtdItens(int qtdItens) {
		this.qtdItens = qtdItens;
	}

	public BigDecimal getVlrPedido() {
		return vlrPedido;
	}

	public void setVlrPedido(BigDecimal vlrPedido) {
		this.vlrPedido = vlrPedido;
	}

	public long getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(long codCliente) {
		this.codCliente = codCliente;
	}

	@Override
	public String toString() {
		return "PedidoDTO [codPedido=" + codPedido + ", dtPedido=" + dtPedido + ", nomeCliente=" + nomeCliente
				+ ", qtdItens=" + qtdItens + ", codCliente=" + codCliente + ", vlrPedido=" + vlrPedido + "]";
	}

}
