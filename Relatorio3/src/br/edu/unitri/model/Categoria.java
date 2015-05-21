/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author marcos.fernando
 *
 */
@Entity
@Table(name = "tbCategoria")
public class Categoria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Colunas(nome = "Código", size = 100)
	private long codCategoria;
	@Colunas(nome = "Nome da Categoria", size = 150)
	private String nomeCategoria;
	@Colunas(nome = "Descrição da Categoria", size = 200)
	private String descCategoria;

	public Categoria() {
		super();
	}

	public Categoria(String nomeCategoria, String descCategoria) {
		super();
		this.nomeCategoria = nomeCategoria;
		this.descCategoria = descCategoria;
	}

	public long getCodCategoria() {
		return codCategoria;
	}

	public void setCodCategoria(long codCategoria) {
		this.codCategoria = codCategoria;
	}

	public String getNomeCategoria() {
		return nomeCategoria;
	}

	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}

	public String getDescCategoria() {
		return descCategoria;
	}

	public void setDescCategoria(String descCategoria) {
		this.descCategoria = descCategoria;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (codCategoria ^ (codCategoria >>> 32));
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
		Categoria other = (Categoria) obj;
		if (codCategoria != other.codCategoria)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nomeCategoria + "--" + descCategoria;
	}

}
