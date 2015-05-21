package br.edu.unitri.DTO.Consultas;

import java.io.Serializable;
import java.util.Date;

import br.edu.unitri.model.Colunas;
import br.edu.unitri.model.TipoDependente;
import br.edu.unitri.model.TipoSexo;

public class LetraBDep implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Colunas(nome = "Nome do Dependente", size = 275)
	private String nomeDependente;
	
	@Colunas(nome = "Sexo", size = 100)
	private TipoSexo sexoDependente;
	
	@Colunas(nome = "Tipo de Dependente", size = 175)
	private TipoDependente tipoDependente;
	
	@Colunas(nome = "Data de Nascimento Dependente", size = 200)
	private Date dtNascDependente;
	
	@Colunas(nome = "Idade Dependente", size = 175)
	private int idadeDependente;

	@Colunas(nome = "Nome Empregada", size = 175)
	private String nomeEmpregada;

	private Date dataAtual = new Date();

	public LetraBDep() {
		super();
	}

	public LetraBDep(String nomeDependente, TipoSexo sexoDependente,
			TipoDependente tipoDependente, Date dtNascDependente,
			String nomeEmpregada) {
		super();
		this.nomeDependente = nomeDependente;
		this.sexoDependente = sexoDependente;
		this.tipoDependente = tipoDependente;
		this.dtNascDependente = dtNascDependente;
		this.nomeEmpregada = nomeEmpregada;
	}

	public String getNomeDependente() {
		return nomeDependente;
	}

	public void setNomeDependente(String nomeDependente) {
		this.nomeDependente = nomeDependente;
	}

	public TipoSexo getSexoDependente() {
		return sexoDependente;
	}

	public void setSexoDependente(TipoSexo sexoDependente) {
		this.sexoDependente = sexoDependente;
	}

	public TipoDependente getTipoDependente() {
		return tipoDependente;
	}

	public void setTipoDependente(TipoDependente tipoDependente) {
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
