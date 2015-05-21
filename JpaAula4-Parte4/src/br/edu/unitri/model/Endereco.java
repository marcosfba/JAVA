/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author marcos.fernando
 *
 */
@Embeddable
public class Endereco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pais;
	private String estado;
	private String cidade;
	private String logradouro;
	private int numero;
	private String complemento;
	private int cep;

	public Endereco() {
		super();
	}

	public Endereco(String pais, String estado, String cidade,
			String logradouro, int numero, String complemento, int cep) {
		super();
		this.pais = pais;
		this.estado = estado;
		this.cidade = cidade;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
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

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public int getCep() {
		return cep;
	}

	public void setCep(int cep) {
		this.cep = cep;
	}

	@Override
	public String toString() {
		return "Endereco [pais=" + pais + ", estado=" + estado + ", cidade="
				+ cidade + ", logradouro=" + logradouro + ", numero=" + numero
				+ ", complemento=" + complemento + ", cep=" + cep + "]";
	}
}
