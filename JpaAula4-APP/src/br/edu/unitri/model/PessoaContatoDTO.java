/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * @author marcos.fernando
 *
 */
public class PessoaContatoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Colunas(nome = "Nome do Pessoa", size = 155)
	private String nomePessoa;
	@Colunas(nome = "Email da Pessoa", size = 200)
	private String emailPessoa;
	@Colunas(nome = "Data de Nascimento", size = 135)
	private LocalDate dtNascimento;
	@Colunas(nome = "Tipo de Contato", size = 175)
	private String tipoContato;
	@Colunas(nome = "Descrição do Tipo de Contato", size = 250)
	private String descricao;
	@Colunas(nome = "Complemento do Contato", size = 100)
	private String complemento;
	
	@Colunas(nome = "Idade da Pessoa", size = 175)
	private int idade;
	private LocalDate dataAtual = LocalDate.now();

	public PessoaContatoDTO() {
		super();
	}

	public PessoaContatoDTO(String nomePessoa, String emailPessoa,
			LocalDate dtNascimento, String tipoContato, String descricao,
			String complemento) {
		super();
		this.nomePessoa = nomePessoa;
		this.emailPessoa = emailPessoa;
		this.dtNascimento = dtNascimento;
		this.tipoContato = tipoContato;
		this.descricao = descricao;
		this.complemento = complemento;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getEmailPessoa() {
		return emailPessoa;
	}

	public void setEmailPessoa(String emailPessoa) {
		this.emailPessoa = emailPessoa;
	}

	public LocalDate getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(LocalDate dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getTipoContato() {
		return tipoContato;
	}

	public void setTipoContato(String tipoContato) {
		this.tipoContato = tipoContato;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public int getIdade() {
		return  (dataAtual.getYear()- getDtNascimento().getYear());
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	@Override
	public String toString() {
		return "PessoaContatoDTO [nomePessoa=" + nomePessoa + ", emailPessoa="
				+ emailPessoa + ", dtNascimento=" + dtNascimento
				+ ", tipoContato=" + tipoContato + ", descricao=" + descricao
				+ ", complemento=" + complemento + "]";
	}

}
