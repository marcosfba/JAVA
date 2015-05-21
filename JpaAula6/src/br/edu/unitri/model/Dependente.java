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
@Table(name = "tbDependente")
public class Dependente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codDependente;
	@Colunas(nome = "Nome do Dependente", size = 175)
	private String nome;
	@Colunas(nome = "Sexo", size = 100)
	private TipoSexo sexo;
	@Colunas(nome = "Tipo de Dependente", size = 150)
	private TipoDependente tipoDependente;
	@Colunas(nome = "Data de Nascimento", size = 175)
	@Temporal(TemporalType.DATE)
	private Date dtNascimento;

	@OneToOne
	@JoinColumn(name = "idEmpregado", referencedColumnName = "codEmpregado")
	private Empregado empregado;

	@Transient
	private int idade;
	@Transient
	private Date dataAtual = new Date();

	public Dependente() {
		super();
	}

	public Dependente(String nome, TipoSexo sexo,
			TipoDependente tipoDependente, Date dtNascimento, 
			Empregado empregado) {
		super();
		this.nome = nome;
		this.sexo = sexo;
		this.tipoDependente = tipoDependente;
		this.dtNascimento = dtNascimento;
		this.empregado = empregado;
	}

	public long getCodDependente() {
		return codDependente;
	}

	public void setCodDependente(long codDependente) {
		this.codDependente = codDependente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoSexo getSexo() {
		return sexo;
	}

	public void setSexo(TipoSexo sexo) {
		this.sexo = sexo;
	}

	public TipoDependente getTipoDependente() {
		return tipoDependente;
	}

	public void setTipoDependente(TipoDependente tipoDependente) {
		this.tipoDependente = tipoDependente;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	@SuppressWarnings("deprecation")
	public int getIdade() {
		return (dataAtual.getYear() - getDtNascimento().getYear());
	}

	public Empregado getEmpregado() {
		return empregado;
	}

	public void setEmpregado(Empregado empregado) {
		this.empregado = empregado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (codDependente ^ (codDependente >>> 32));
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
		Dependente other = (Dependente) obj;
		if (codDependente != other.codDependente)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return codDependente + " " + nome;
	}

}
