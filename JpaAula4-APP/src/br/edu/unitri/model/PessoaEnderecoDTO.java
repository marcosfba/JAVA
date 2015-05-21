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
public class PessoaEnderecoDTO implements Serializable {

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
	@Colunas(nome = "Tipo de Endereço", size = 135)
	private String tipoEndereco;
	@Colunas(nome = "País", size = 100)
	private String pais;
	@Colunas(nome = "UF", size = 75)
	private String estado;
	@Colunas(nome = "Cidade", size = 150)
	private String cidade;
	@Colunas(nome = "Logradouro/Endereço", size = 200)
	private String logradouro;
	@Colunas(nome = "Número", size = 100)
	private String numero;
	@Colunas(nome = "Complemento", size = 175)
	private String complemento;
	@Colunas(nome = "CEP", size = 100)
	private String cep;

	@Colunas(nome = "Idade da Pessoa", size = 175)
	private int idade;
	private LocalDate dataAtual = LocalDate.now();

	public PessoaEnderecoDTO() {
		super();
	}

	public PessoaEnderecoDTO(String nomePessoa, String emailPessoa,
			LocalDate dtNascimento, String tipoEndereco, String pais,
			String estado, String cidade, String logradouro, String numero,
			String complemento, String cep) {
		super();
		this.nomePessoa = nomePessoa;
		this.emailPessoa = emailPessoa;
		this.dtNascimento = dtNascimento;
		this.tipoEndereco = tipoEndereco;
		this.pais = pais;
		this.estado = estado;
		this.cidade = cidade;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
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

	public String getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(String tipoEndereco) {
		this.tipoEndereco = tipoEndereco;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public int getIdade() {
		return  (dataAtual.getYear()- getDtNascimento().getYear());
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	@Override
	public String toString() {
		return "PessoaEnderecoDTO [nomePessoa=" + nomePessoa + ", emailPessoa="
				+ emailPessoa + ", dtNascimento=" + dtNascimento
				+ ", tipoEndereco=" + tipoEndereco + ", pais=" + pais
				+ ", estado=" + estado + ", cidade=" + cidade + ", logradouro="
				+ logradouro + ", numero=" + numero + ", complemento="
				+ complemento + ", cep=" + cep + ", idade=" + idade
				+ ", dataAtual=" + dataAtual + "]";
	}

}
