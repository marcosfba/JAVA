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
@Table(name = "tbVeiculo")
public class Veiculo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idVeiculo;

	@Colunas(nome = "Descrição do Veículo", size = 155)
	private String descricao;
	@Colunas(nome = "Modelo", size = 100)
	private String modelo;
	@Colunas(nome = "Fator Preço", size = 95)
	private BigDecimal fator;

	@OneToOne
	@JoinColumn(name = "categoria_id", referencedColumnName="idCategoria")
	@Colunas(nome = "Categoria", size = 75)
	private Categoria categoria;

	public Veiculo() {
		super();
	}

	public Veiculo(String descricao, String modelo, BigDecimal fator,
			Categoria categoria) {
		super();
		this.descricao = descricao;
		this.modelo = modelo;
		this.fator = fator;
		this.categoria = categoria;
	}

	public long getIdVeiculo() {
		return idVeiculo;
	}

	public void setIdVeiculo(long idVeiculo) {
		this.idVeiculo = idVeiculo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public BigDecimal getFator() {
		return fator;
	}

	public void setFator(BigDecimal fator) {
		this.fator = fator;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idVeiculo ^ (idVeiculo >>> 32));
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
		Veiculo other = (Veiculo) obj;
		if (idVeiculo != other.idVeiculo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return descricao + " ->" + modelo +  " -> " + categoria;
	}

}
