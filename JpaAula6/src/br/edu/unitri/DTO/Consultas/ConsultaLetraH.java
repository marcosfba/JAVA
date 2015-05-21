/**
 * 
 */
package br.edu.unitri.DTO.Consultas;

import java.io.Serializable;
import java.util.Date;

import br.edu.unitri.model.Colunas;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaLetraH implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Colunas(nome = "Nome do  Empregado", size = 175)
	private String nomeEmpregado;

	@Colunas(nome = "Nome do  Departamento", size = 175)
	private String nomeDepartamento;
	
	@Colunas(nome = "Data de Inicio", size = 175)
	private Date dtInicio;

	public ConsultaLetraH() {
		super();
	}

	public ConsultaLetraH(String nomeEmpregado, String nomeDepartamento,
			Date dtInicio) {
		super();
		this.nomeEmpregado = nomeEmpregado;
		this.nomeDepartamento = nomeDepartamento;
		this.dtInicio = dtInicio;
	}

	public String getNomeEmpregado() {
		return nomeEmpregado;
	}

	public void setNomeEmpregado(String nomeEmpregado) {
		this.nomeEmpregado = nomeEmpregado;
	}

	public String getNomeDepartamento() {
		return nomeDepartamento;
	}

	public void setNomeDepartamento(String nomeDepartamento) {
		this.nomeDepartamento = nomeDepartamento;
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	@Override
	public String toString() {
		return "ConsultaLetraH [nomeEmpregado=" + nomeEmpregado
				+ ", nomeDepartamento=" + nomeDepartamento + ", dtInicio="
				+ dtInicio + "]";
	}

}
