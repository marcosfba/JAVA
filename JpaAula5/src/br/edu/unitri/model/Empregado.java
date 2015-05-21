/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author marcos.fernando
 *
 */
@Entity
@Table(name ="tbEmpregado")
public class Empregado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codEmpregado;
	@Colunas(nome = "Nome Empregado", size = 220)
	private String nomeEmpregado;
	@Colunas(nome = "Endereço Empregado", size = 200)
	private String endEmpregado;
	@Colunas(nome = "Sexo", size = 100)
	private TipoSexo sexo;
	@Colunas(nome = "Data de Nascimento", size = 175)
	@Temporal(TemporalType.DATE)
	private Date dtNasc;
	
	@OneToOne
	@JoinColumn(name = "gerente_id", referencedColumnName="codEmpregado")
	private Empregado gerente;

	@Colunas(nome = "Idade", size = 100)
	@Transient
	private int idade;	
	@Transient
	private Date dataAtual = new Date();
	
	@OneToOne
	@JoinColumn(name = "departamento_id", referencedColumnName="idDepartamento")
	private Departamento departamento;

	public Empregado() {
		super();
	}

	public Empregado(String nomeEmpregado, String endEmpregado, TipoSexo sexo,
			Date dtNasc, Departamento departamento) {
		super();
		this.nomeEmpregado = nomeEmpregado;
		this.endEmpregado = endEmpregado;
		this.sexo = sexo;
		this.dtNasc = dtNasc;
		this.departamento = departamento;
	}

	public long getCodEmpregado() {
		return codEmpregado;
	}

	public void setCodEmpregado(long codEmpregado) {
		this.codEmpregado = codEmpregado;
	}

	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}

	public String getEndEmpregado() {
		return endEmpregado;
	}

	public void setEndEmpregado(String endEmpregado) {
		this.endEmpregado = endEmpregado;
	}

	public TipoSexo getSexo() {
		return sexo;
	}

	public void setSexo(TipoSexo sexo) {
		this.sexo = sexo;
	}

	public Date getDtNasc() {
		return dtNasc;
	}

	public void setDtNasc(Date dtNasc) {
		this.dtNasc = dtNasc;
	}

	@SuppressWarnings("deprecation")
	public int getIdade() {
		return  (dataAtual.getYear()- getDtNasc().getYear());
	}

	public Empregado getGerente() {
		return gerente;
	}

	public void setGerente(Empregado gerente) {
		this.gerente = gerente;
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
		result = prime * result + (int) (codEmpregado ^ (codEmpregado >>> 32));
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
		Empregado other = (Empregado) obj;
		if (codEmpregado != other.codEmpregado)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return codEmpregado + " " + nomeEmpregado;
	}

}
