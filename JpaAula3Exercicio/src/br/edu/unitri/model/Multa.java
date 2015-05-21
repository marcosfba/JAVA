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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author marcos.fernando
 *
 */
@Entity
@Table(name = "tbMulta")
public class Multa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idMulta;

	@Colunas(nome = "Descrição da Multa", size = 275)
	private String descricao;
	@Colunas(nome = "Valor da Multa", size = 145)
	private BigDecimal valor;

	@OneToOne
	@JoinColumn(name = "idLocacao")
	@Colunas(nome = "Identificação da Locação", size = 275)
	private Locacao locacao;

	public Multa() {
		super();
	}

	public Multa(BigDecimal valor, String descricao, Locacao locacao) {
		super();
		this.valor = valor;
		this.descricao = descricao;
		this.locacao = locacao;
	}

	public long getIdMulta() {
		return idMulta;
	}

	public void setIdMulta(long idMulta) {
		this.idMulta = idMulta;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Locacao getLocacao() {
		return locacao;
	}

	public void setLocacao(Locacao locacao) {
		this.locacao = locacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idMulta ^ (idMulta >>> 32));
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
		Multa other = (Multa) obj;
		if (idMulta != other.idMulta)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Multa [idMulta=" + idMulta + ", valor=" + valor
				+ ", descricao=" + descricao + ", locacao=" + locacao + "]";
	}

}
