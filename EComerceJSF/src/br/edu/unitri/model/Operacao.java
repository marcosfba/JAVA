/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

import br.edu.unitri.enumerators.TipoOperacao;
import br.edu.unitri.enumerators.TipoPagamento;
import br.edu.unitri.enumerators.TipoStatus;

/**
 * @author MARCOS FERNANDO
 *
 */
@Entity
@Table(name = "tbOperacao")
public class Operacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TIPOOPE", nullable = true)
	@Enumerated(EnumType.STRING)
	private TipoOperacao tipoOperacao;

	@Column(name = "TIPOPAG", nullable = true)
	@Enumerated(EnumType.STRING)
	private TipoPagamento tipoPagamento;

	@Column(name = "TIPOSIT", nullable = true)
	@Enumerated(EnumType.STRING)
	private TipoStatus tipoStatus;

	@Temporal(TemporalType.DATE)
	private Date dtOperacao;

	@OneToOne
	@JoinColumn(name = "idPessoa", referencedColumnName = "id")
	private Pessoa pessoa;

	@Column(name = "VLRPEDIDO", precision = 18, scale = 2)
	private BigDecimal vlrPedido;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "operacao", cascade=CascadeType.REMOVE)
	private Collection<ItensOperacao> itens;

	public Operacao() {
		super();
	}

	public Operacao(Long id, TipoOperacao tipoOperacao,
			TipoPagamento tipoPagamento, TipoStatus tipoStatus,
			Date dtOperacao, Pessoa pessoa, BigDecimal vlrPedido,
			Collection<ItensOperacao> itens) {
		super();
		this.id = id;
		this.tipoOperacao = tipoOperacao;
		this.tipoPagamento = tipoPagamento;
		this.tipoStatus = tipoStatus;
		this.dtOperacao = dtOperacao;
		this.pessoa = pessoa;
		this.vlrPedido = vlrPedido;
		this.itens = itens;
	}
	
	public Operacao(TipoOperacao tipoOperacao, TipoPagamento tipoPagamento,
			TipoStatus tipoStatus, Date dtOperacao, Pessoa pessoa) {
		super();
		this.tipoOperacao = tipoOperacao;
		this.tipoPagamento = tipoPagamento;
		this.tipoStatus = tipoStatus;
		this.dtOperacao = dtOperacao;
		this.pessoa = pessoa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getDtOperacao() {
		return dtOperacao;
	}

	public void setDtOperacao(Date dtOperacao) {
		this.dtOperacao = dtOperacao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public BigDecimal getVlrPedido() {
		return vlrPedido;
	}

	public void setVlrPedido(BigDecimal vlrPedido) {
		this.vlrPedido = vlrPedido;
	}

	public Collection<ItensOperacao> getItens() {
		return itens;
	}

	public void setItens(Collection<ItensOperacao> itens) {
		this.itens = itens;
	}

}
