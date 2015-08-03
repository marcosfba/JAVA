/**
 * 
 */
package br.edu.unitri.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.edu.unitri.enumerators.TipoOperacao;
import br.edu.unitri.enumerators.TipoPagamento;
import br.edu.unitri.enumerators.TipoStatus;

/**
 * @author marcos.fernando
 *
 */
public class PedidoDTO implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idPessoa;
	private Long idOperacao;
	private Date dtOperacao;
	private String nomePessoa;
	private TipoOperacao tipoOperacao;
	private TipoPagamento tipoPagamento;
	private TipoStatus tipoStatus;
	private BigDecimal vlrPedido;
	private int qtdItens;

	public PedidoDTO() {
		super();
	}

	public PedidoDTO(Long idPessoa, Long idOperacao, Date dtOperacao, String nomePessoa, TipoOperacao tipoOperacao,
			TipoPagamento tipoPagamento, TipoStatus tipoStatus, BigDecimal vlrPedido, int qtdItens) {
		super();
		this.idPessoa = idPessoa;
		this.idOperacao = idOperacao;
		this.dtOperacao = dtOperacao;
		this.nomePessoa = nomePessoa;
		this.tipoOperacao = tipoOperacao;
		this.tipoPagamento = tipoPagamento;
		this.tipoStatus = tipoStatus;
		this.vlrPedido = vlrPedido;
		this.qtdItens = qtdItens;
	}

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public Long getIdOperacao() {
		return idOperacao;
	}

	public void setIdOperacao(Long idOperacao) {
		this.idOperacao = idOperacao;
	}

	public Date getDtOperacao() {
		return dtOperacao;
	}

	public void setDtOperacao(Date dtOperacao) {
		this.dtOperacao = dtOperacao;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}

	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public TipoStatus getTipoStatus() {
		return tipoStatus;
	}

	public void setTipoStatus(TipoStatus tipoStatus) {
		this.tipoStatus = tipoStatus;
	}

	public BigDecimal getVlrPedido() {
		return vlrPedido;
	}

	public void setVlrPedido(BigDecimal vlrPedido) {
		this.vlrPedido = vlrPedido;
	}

	public int getQtdItens() {
		return qtdItens;
	}

	public void setQtdItens(int qtdItens) {
		this.qtdItens = qtdItens;
	}

}
