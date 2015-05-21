/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;

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
@Table(name = "tbProjeto")
public class Projeto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idProjeto;

	@Colunas(nome = "Numero do Projeto", size = 150)
	private String numProjeto;
	@Colunas(nome = "Descrição do Projeto", size = 225)
	private String descProjeto;

	@Colunas(nome = "Nome do Local", size = 200)
	@OneToOne
	@JoinColumn(name = "local_id", referencedColumnName = "idLocal")
	private Local local;
	@Colunas(nome = "Nome do Departamento", size = 200)
	
	@OneToOne
	@JoinColumn(name = "departamento_id", referencedColumnName = "idDepartamento")
	private Departamento departamento;
	
	public Projeto() {
		super();
	}

	public Projeto(String numProjeto, String descProjeto, Local local,
			Departamento departamento) {
		super();
		this.numProjeto = numProjeto;
		this.descProjeto = descProjeto;
		this.local = local;
		this.departamento = departamento;
	}

	public long getIdProjeto() {
		return idProjeto;
	}

	public void setIdProjeto(long idProjeto) {
		this.idProjeto = idProjeto;
	}

	public String getNumProjeto() {
		return numProjeto;
	}

	public void setNumProjeto(String numProjeto) {
		this.numProjeto = numProjeto;
	}

	public String getDescProjeto() {
		return descProjeto;
	}

	public void setDescProjeto(String descProjeto) {
		this.descProjeto = descProjeto;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idProjeto ^ (idProjeto >>> 32));
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
		Projeto other = (Projeto) obj;
		if (idProjeto != other.idProjeto)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return numProjeto + " " + descProjeto;
	}
	

}
