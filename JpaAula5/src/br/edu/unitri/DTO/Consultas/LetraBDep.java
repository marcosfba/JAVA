package br.edu.unitri.DTO.Consultas;

import java.io.Serializable;
import java.util.Date;

import br.edu.unitri.model.Colunas;

public class LetraBDep implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Colunas(nome = "Nome do Dependente", size = 275)
	private String nomeDependente;
	
	@Colunas(nome = "Sexo", size = 100)
	private String sexoDependente;
	
	@Colunas(nome = "Tipo de Dependente", size = 175)
	private String tipoDependente;
	
	@Colunas(nome = "Data de Nascimento Dependente", size = 200)
	private Date dtNascDependente;
	
	@Colunas(nome = "Idade Dependente", size = 175)
	private int idadeDependente;

	private Date dataAtual = new Date();

	public LetraBDep() {
		super();
	}

	public LetraBDep(String nomeDependente, String sexoDependente,
			String tipoDependente, Date dtNascDependente) {
		super();
		this.nomeDependente = nomeDependente;
		this.sexoDependente = sexoDependente;
		this.tipoDependente = tipoDependente;
		this.dtNascDependente = dtNascDependente;
	}

	public String getNomeDependente() {
		return nomeDependente;
	}

	public void setNomeDependente(String nomeDependente) {
		this.nomeDependente = nomeDependente;
	}

	public String getSexoDependente() {
		return sexoDependente;
	}

	public void setSexoDependente(String sexoDependente) {
		this.sexoDependente = sexoDependente;
	}

	public String getTipoDependente() {
		return tipoDependente;
	}

	public void setTipoDependente(String tipoDependente) {
		this.tipoDependente = tipoDependente;
	}

	public Date getDtNascDependente() {
		return dtNascDependente;
	}

	public void setDtNascDependente(Date dtNascDependente) {
		this.dtNascDependente = dtNascDependente;
	}

	@SuppressWarnings("deprecation")
	public int getIdadeDependente() {
		return this.dataAtual.getYear() - this.dtNascDependente.getYear();
	}

	public void setIdadeDependente(int idadeDependente) {
		this.idadeDependente = idadeDependente;
	}

	@Override
	public String toString() {
		return "LetraBDep [nomeDependente=" + nomeDependente
				+ ", sexoDependente=" + sexoDependente + ", tipoDependente="
				+ tipoDependente + ", dtNascDependente=" + dtNascDependente
				+ ", idadeDependente=" + idadeDependente + ", dataAtual="
				+ dataAtual + "]";
	}
	
}
