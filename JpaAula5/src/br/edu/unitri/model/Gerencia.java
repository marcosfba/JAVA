/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author marcos.fernando
 *
 */
@Entity
@Table(name = "tbGerencia")
public class Gerencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Colunas(nome = "Nome Gerente", size = 150)
	@Id
	@OneToOne
	@JoinColumn(name = "gerente_id", referencedColumnName = "codEmpregado")
	private Empregado empregado;

	@Colunas(nome = "Nome Departamento", size = 175)
	@OneToOne
	@JoinColumn(name = "departamento_id", referencedColumnName = "idDepartamento")
	private Departamento departamento;
	@Temporal(TemporalType.DATE)
	@Colunas(nome = "Data de Inicio Projeto", size = 170)
	private Date dtEmp;

	public Gerencia() {
		super();
	}

	public Gerencia(Empregado empregado, Departamento departamento,
			Date dtEmp) {
		super();
		this.empregado = empregado;
		this.departamento = departamento;
		this.dtEmp = dtEmp;
	}

	public Empregado getEmpregado() {
		return empregado;
	}

	public void setEmpregado(Empregado empregado) {
		this.empregado = empregado;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Date getDtEmp() {
		return dtEmp;
	}

	public void setDtEmp(Date dtEmp) {
		this.dtEmp = dtEmp;
	}

	@Override
	public String toString() {
		return getEmpregado().getCodEmpregado() + " " + getEmpregado().getNomeEmpregado();
	}

}
