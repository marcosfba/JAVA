/**
 * 
 */
package br.edu.unitri.DTO.Consultas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.unitri.model.Colunas;

/**
 * @author marcos.fernando
 *
 */



public class ConsultaLetraB implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Colunas(nome = "Cod.Empregado", size = 125)
	private String codEmpregado;
	
	@Colunas(nome = "Nome Empregado", size = 200)
	private String nomeEmpregado;
	
	@Colunas(nome = "Data de Nascimento", size = 155)
	private Date dtNascimento;
	
	@Colunas(nome = "Endereço", size = 200)
	private String endereco;
	
	@Colunas(nome = "Sexo", size = 100)
	private String sexo;
	
	@Colunas(nome = "Idade", size = 100)
	private int idadeEmpregado;
	
	@Colunas(nome = "Nome do Gerente", size = 200)
	private String nomeGerente;	
	
	@Colunas(nome = "Nome Departamento", size = 200)
	private String nomeDepartamento;

	private List<LetraBDep> dependentes = new ArrayList<LetraBDep>();
	private List<LetraBProj> projetos = new ArrayList<LetraBProj>();
	
	private Date dataAtual = new Date();

	public ConsultaLetraB() {
		super();
	}

	public ConsultaLetraB(String codEmpregado, Date dtNascimento,
			String endereco, String nomeEmpregado, String sexo,
			String nomeDepartamento, String nomeGerente) {
		super();
		this.codEmpregado = codEmpregado;
		this.dtNascimento = dtNascimento;
		this.endereco = endereco;
		this.nomeEmpregado = nomeEmpregado;
		this.sexo = sexo;
		this.nomeGerente = nomeGerente;
		this.nomeDepartamento = nomeDepartamento;
	}

	public String getCodEmpregado() {
		return codEmpregado;
	}

	public void setCodEmpregado(String codEmpregado) {
		this.codEmpregado = codEmpregado;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@SuppressWarnings("deprecation")
	public int getIdadeEmpregado() {
		return  this.dataAtual.getYear() - this.dtNascimento.getYear();
	}

	public void setIdadeEmpregado(int idadeEmpregado) {
		this.idadeEmpregado = idadeEmpregado;
	}

	public String getNomeGerente() {
		return nomeGerente;
	}

	public void setNomeGerente(String nomeGerente) {
		this.nomeGerente = nomeGerente;
	}

	public String getNomeDepartamento() {
		return nomeDepartamento;
	}

	public void setNomeDepartamento(String nomeDepartamento) {
		this.nomeDepartamento = nomeDepartamento;
	}

	public List<LetraBDep> getDependentes() {
		return dependentes;
	}

	public void setDependentes(List<LetraBDep> dependentes) {
		this.dependentes = dependentes;
	}

	public List<LetraBProj> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<LetraBProj> projetos) {
		this.projetos = projetos;
	}

	@Override
	public String toString() {
		return "ConsultaLetraB [codEmpregado=" + codEmpregado
				+ ", nomeEmpregado=" + nomeEmpregado + ", dtNascimento="
				+ dtNascimento + ", endereco=" + endereco + ", sexo=" + sexo
				+ ", idadeEmpregado=" + idadeEmpregado + ", nomeGerente="
				+ nomeGerente + ", nomeDepartamento=" + nomeDepartamento
				+ ", dependentes=" + dependentes + ", projetos=" + projetos
				+ ", dataAtual=" + dataAtual + "]";
	}

}
