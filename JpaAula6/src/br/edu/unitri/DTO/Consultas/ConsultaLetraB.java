/**
 * 
 */
package br.edu.unitri.DTO.Consultas;

import java.io.Serializable;
import java.util.Date;

import br.edu.unitri.model.Colunas;
import br.edu.unitri.model.TipoSexo;

/**
 * @author marcos.fernando
 *
 */

public class ConsultaLetraB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Colunas(nome ="Código Empregado", size = 100)
	private long codEmpregado;

	@Colunas(nome = "Nome Empregado", size = 200)
	private String nomeEmpregado;

	@Colunas(nome = "Data de Nascimento", size = 155)
	private Date dtnasc;

	@Colunas(nome = "Endereço", size = 200)
	private String endEmpregado;

	@Colunas(nome = "Sexo", size = 100)
	private TipoSexo sexo;

	private Date dataAtual = new Date();
	@Colunas(nome = "Idade Empregado", size = 100)
	private int idade;

	public ConsultaLetraB() {
		super();
	}

	public ConsultaLetraB(long codEmpregado, String nomeEmpregado, String endEmpregado, 
			Date dtnasc, TipoSexo sexo) {
		super();
		this.codEmpregado = codEmpregado;
		this.nomeEmpregado = nomeEmpregado;
		this.dtnasc = dtnasc;
		this.endEmpregado = endEmpregado;
		this.sexo = sexo;
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

	public Date getDtnasc() {
		return dtnasc;
	}

	public void setDtnasc(Date dtnasc) {
		this.dtnasc = dtnasc;
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

	public Date getDataAtual() {
		return dataAtual;
	}

	public void setDataAtual(Date dataAtual) {
		this.dataAtual = dataAtual;
	}

	@SuppressWarnings("deprecation")
	public int getIdade() {
		return this.dataAtual.getYear() - this.dtnasc.getYear();
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}
}
