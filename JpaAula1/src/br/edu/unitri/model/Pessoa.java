package br.edu.unitri.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idPessoa;
	@Colunas(nome = "Nome da Pessoa", size = 175)
	private String nomePessoa;
	@Colunas(nome = "Telefone", size = 135)
	private String fonePessoa;
	@Colunas(nome = "Email da Pessoa", size = 200)
	private String emailPessoa;

	public Pessoa() {
		super();
	}

	public Pessoa(String nomePessoa, String fonePessoa, String emailPessoa) {
		super();
		this.nomePessoa = nomePessoa;
		this.fonePessoa = fonePessoa;
		this.emailPessoa = emailPessoa;
	}

	public long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getFonePessoa() {
		return fonePessoa;
	}

	public void setFonePessoa(String fonePessoa) {
		this.fonePessoa = fonePessoa;
	}

	public String getEmailPessoa() {
		return emailPessoa;
	}

	public void setEmailPessoa(String emailPessoa) {
		this.emailPessoa = emailPessoa;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idPessoa ^ (idPessoa >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (idPessoa != other.idPessoa)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [idPessoa=" + idPessoa + ", nomePessoa=" + nomePessoa
				+ ", fonePessoa=" + fonePessoa + ", emailPessoa=" + emailPessoa
				+ "]";
	}

}
