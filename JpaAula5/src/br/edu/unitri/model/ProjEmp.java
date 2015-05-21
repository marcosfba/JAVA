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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author marcos.fernando
 *
 */
@Entity
@Table(name="Projeto_Emp")
public class ProjEmp implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idProjEmp;
	
	@ManyToOne
	@JoinColumn(name = "projeto_id", referencedColumnName = "idProjeto")
	private Projeto projeto;

	@ManyToOne
	@JoinColumn(name = "empregado_id", referencedColumnName = "codEmpregado")
	private Empregado empregado;

	private int quantHoras;

	public ProjEmp() {
		super();
	}

	public ProjEmp(Projeto projeto, Empregado empregado,
			int quantHoras) {
		super();
		this.projeto = projeto;
		this.empregado = empregado;
		this.quantHoras = quantHoras;
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public Empregado getEmpregado() {
		return empregado;
	}

	public void setEmpregado(Empregado empregado) {
		this.empregado = empregado;
	}

	public int getQuantHoras() {
		return quantHoras;
	}

	public void setQuantHoras(int quantHoras) {
		this.quantHoras = quantHoras;
	}

	@Override
	public String toString() {
		return "ProjEmp [projeto=" + projeto + ", empregado="
				+ empregado + ", quantHoras=" + quantHoras + "]";
	}

}
