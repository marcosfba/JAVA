/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author marcos.fernando
 *
 */
@Entity
@Table(name = "tbLocal")
public class Local implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idLocal;
	@Colunas(nome = "Nome Local", size = 115)
	private String nomLocal;
	@Colunas(nome = "Descrição do Local", size = 200)
	private String descLocal;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "departamento_id", referencedColumnName = "idDepartamento")
	@JoinTable(name = "Local_Dept", joinColumns = @JoinColumn(name = "local_id"), inverseJoinColumns = @JoinColumn(name = "departamento_id"))
	private Collection<Departamento> departamentos;

	public Local() {
		super();
	}

	public Local(String nomLocal, String descLocal) {
		super();
		this.nomLocal = nomLocal;
		this.descLocal = descLocal;
	}

	public long getIdLocal() {
		return idLocal;
	}

	public void setIdLocal(long idLocal) {
		this.idLocal = idLocal;
	}

	public String getNomLocal() {
		return nomLocal;
	}

	public void setNomLocal(String nomLocal) {
		this.nomLocal = nomLocal;
	}

	public String getDescLocal() {
		return descLocal;
	}

	public void setDescLocal(String descLocal) {
		this.descLocal = descLocal;
	}

	public Collection<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(Collection<Departamento> departamentos) {
		this.departamentos = departamentos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idLocal ^ (idLocal >>> 32));
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
		Local other = (Local) obj;
		if (idLocal != other.idLocal)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nomLocal + " " + descLocal;
	}

}
