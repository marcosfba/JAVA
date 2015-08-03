/**
 * 
 */
package br.edu.unitri.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import br.edu.unitri.enumerators.TipoContato;
import br.edu.unitri.enumerators.TipoEndereco;
import br.edu.unitri.enumerators.TipoOperacao;
import br.edu.unitri.enumerators.TipoPagamento;
import br.edu.unitri.enumerators.TipoStatus;
import br.edu.unitri.enumerators.TipoTelefone;

/**
 * @author marcos.fernando
 *
 */
public class PedidoComplDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long idPedido;
	private String nomeCliente;
	private String cpfCliente;
	private String identidade;
	private String tipoPessoa;
	private TipoEndereco tipoEndereco;
	private String endereco;
	private String numero;
	private String bairro;
	private String cidade;
	private String estado;
	private TipoContato tipoContato;
	private String emailContato;
	private TipoTelefone tipoTelefone;
	private String numTelefone;
	private Date dtPedido;
	private TipoOperacao tipoOperacao;
	private TipoPagamento tipoPagamento;
	private TipoStatus tipoStatus;
	private BigDecimal vlrPedido;
	private String codProduto;
	private String codBarras;
	private String descProduto;
	private String unidadeProd;
	private int quantidade;
	private BigDecimal vlrItem;
	private BigDecimal vlrDesconto;
	private String nomeMarca;
	private String nomeCategoria;

	public PedidoComplDTO() {
		super();
	}

	public PedidoComplDTO(long idPedido, String nomeCliente, String cpfCliente,
			String identidade, String tipoPessoa, TipoEndereco tipoEndereco,
			String endereco, String numero, String bairro, String cidade,
			String estado, TipoContato tipoContato, String emailContato,
			TipoTelefone tipoTelefone, String numTelefone, Date dtPedido,
			TipoOperacao tipoOperacao, TipoPagamento tipoPagamento,
			TipoStatus tipoStatus, BigDecimal vlrPedido, String codProduto,
			String codBarras, String descProduto, String unidadeProd,
			int quantidade, BigDecimal vlrItem, BigDecimal vlrDesconto,
			String nomeMarca, String nomeCategoria) {
		super();
		this.idPedido = idPedido;
		this.nomeCliente = nomeCliente;
		this.cpfCliente = cpfCliente;
		this.identidade = identidade;
		this.tipoPessoa = tipoPessoa;
		this.tipoEndereco = tipoEndereco;
		this.endereco = endereco;
		this.numero = numero;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.tipoContato = tipoContato;
		this.emailContato = emailContato;
		this.tipoTelefone = tipoTelefone;
		this.numTelefone = numTelefone;
		this.dtPedido = dtPedido;
		this.tipoOperacao = tipoOperacao;
		this.tipoPagamento = tipoPagamento;
		this.tipoStatus = tipoStatus;
		this.vlrPedido = vlrPedido;
		this.codProduto = codProduto;
		this.codBarras = codBarras;
		this.descProduto = descProduto;
		this.unidadeProd = unidadeProd;
		this.quantidade = quantidade;
		this.vlrItem = vlrItem;
		this.vlrDesconto = vlrDesconto;
		this.nomeMarca = nomeMarca;
		this.nomeCategoria = nomeCategoria;
	}

	public long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(long idPedido) {
		this.idPedido = idPedido;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getCpfCliente() {
		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente) {
		this.cpfCliente = cpfCliente;
	}

	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public TipoEndereco getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(TipoEndereco tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public TipoContato getTipoContato() {
		return tipoContato;
	}

	public void setTipoContato(TipoContato tipoContato) {
		this.tipoContato = tipoContato;
	}

	public String getEmailContato() {
		return emailContato;
	}

	public void setEmailContato(String emailContato) {
		this.emailContato = emailContato;
	}

	public TipoTelefone getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefone tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public String getNumTelefone() {
		return numTelefone;
	}

	public void setNumTelefone(String numTelefone) {
		this.numTelefone = numTelefone;
	}

	public Date getDtPedido() {
		return dtPedido;
	}

	public void setDtPedido(Date dtPedido) {
		this.dtPedido = dtPedido;
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

	public String getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(String codProduto) {
		this.codProduto = codProduto;
	}

	public String getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	public String getDescProduto() {
		return descProduto;
	}

	public void setDescProduto(String descProduto) {
		this.descProduto = descProduto;
	}

	public String getUnidadeProd() {
		return unidadeProd;
	}

	public void setUnidadeProd(String unidadeProd) {
		this.unidadeProd = unidadeProd;
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

	public BigDecimal getVlrDesconto() {
		return vlrDesconto;
	}

	public void setVlrDesconto(BigDecimal vlrDesconto) {
		this.vlrDesconto = vlrDesconto;
	}

	public String getNomeMarca() {
		return nomeMarca;
	}

	public void setNomeMarca(String nomeMarca) {
		this.nomeMarca = nomeMarca;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	@Override
	public String toString() {
		return "PedidoComplDTO [idPedido=" + idPedido + ", nomeCliente="
				+ nomeCliente + ", cpfCliente=" + cpfCliente + ", identidade="
				+ identidade + ", tipoPessoa=" + tipoPessoa + ", tipoEndereco="
				+ tipoEndereco + ", endereco=" + endereco + ", numero="
				+ numero + ", bairro=" + bairro + ", cidade=" + cidade
				+ ", estado=" + estado + ", tipoContato=" + tipoContato
				+ ", emailContato=" + emailContato + ", tipoTelefone="
				+ tipoTelefone + ", numTelefone=" + numTelefone + ", dtPedido="
				+ dtPedido + ", tipoOperacao=" + tipoOperacao
				+ ", tipoPagamento=" + tipoPagamento + ", tipoStatus="
				+ tipoStatus + ", vlrPedido=" + vlrPedido + ", codProduto="
				+ codProduto + ", codBarras=" + codBarras + ", descProduto="
				+ descProduto + ", unidadeProd=" + unidadeProd
				+ ", quantidade=" + quantidade + ", vlrItem=" + vlrItem
				+ ", vlrDesconto=" + vlrDesconto + ", nomeMarca=" + nomeMarca
				+ ", nomeCategoria=" + nomeCategoria + "]";
	}
	
}
