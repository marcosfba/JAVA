/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author marcos.fernando
 *
 */
@Entity
public class Estado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Colunas(nome = "Governador", size = 245)
	@OneToOne
	private Governador governador;

	@Colunas(nome = "Sigla do Estado", size = 155)
	private String sigla;
	@Colunas(nome = "Nome do Estado", size = 200)
	private String nomeEstado;

	public Estado() {
		super();
	}

	public Estado(String sigla, String nomeEstado) {
		super();
		this.sigla = sigla;
		this.nomeEstado = nomeEstado;
	}

	public Estado(Governador governador, String sigla, String nomeEstado) {
		super();
		this.governador = governador;
		this.sigla = sigla;
		this.nomeEstado = nomeEstado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Governador getGovernador() {
		return governador;
	}

	public void setGovernador(Governador governador) {
		this.governador = governador;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Estado other = (Estado) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Estado [id=" + id + ", governador=" + governador + ", sigla="
				+ sigla + ", nomeEstado=" + nomeEstado + "]";
	}

}
