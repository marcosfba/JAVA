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
@Table(name = "tbPessoaFisica")
public class PessoaFisica extends Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Colunas(nome = "CPF da Pessoa", size = 135)
	private String Cpf;
	@Colunas(nome = "Identidade da Pessoa", size = 135)
	private String Identidade;

	public PessoaFisica() {
		super();
	}

	public PessoaFisica(String nome, String email, Date dtNascimento) {
		super(nome, email, dtNascimento);
	}
	
	public PessoaFisica(String cpf, String identidade) {
		super();
		Cpf = cpf;
		Identidade = identidade;
	}

	public String getCpf() {
		return Cpf;
	}

	public void setCpf(String cpf) {
		Cpf = cpf;
	}

	public String getIdentidade() {
		return Identidade;
	}

	public void setIdentidade(String identidade) {
		Identidade = identidade;
	}

	@Override
	public String toString() {
		return  super.toString();
	}
}
