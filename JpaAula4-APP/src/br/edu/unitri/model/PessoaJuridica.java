/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author marcos.fernando
 *
 */
@Entity
@Table(name = "tbPessoaJuridica")
public class PessoaJuridica extends Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Colunas(nome = "CNPJ da Pessoa", size = 175)
	private String cnpj;
	@Colunas(nome = "Inscrição Estadual", size = 175)
	private String InscEstadual;

	public PessoaJuridica() {
		super();
	}

	public PessoaJuridica(String nome, String email, Date dtNascimento) {
		super(nome, email, dtNascimento);
	}

	public PessoaJuridica(String cnpj, String inscEstadual) {
		super();
		this.cnpj = cnpj;
		InscEstadual = inscEstadual;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscEstadual() {
		return InscEstadual;
	}

	public void setInscEstadual(String inscEstadual) {
		InscEstadual = inscEstadual;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
