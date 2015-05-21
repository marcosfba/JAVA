/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author marcos.fernando
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "tbPessoa")
public class Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Colunas(nome = "Nome do Pessoa", size = 100)
	private String nome;
	@Colunas(nome = "Email da Pessoa", size = 120)
	private String email;
	@Colunas(nome = "Data de Nascimento", size = 150)
	private LocalDate dtNascimento;

	@Transient
	@Colunas(nome = "Idade da Pessoa", size = 175)
	private int idade;	
	@Transient
	private LocalDate dataAtual = LocalDate.now();

	public Pessoa() {
		super();
	}

	public Pessoa(String nome, String email, LocalDate dtNascimento) {
		super();
		this.nome = nome;
		this.email = email;
		this.dtNascimento = dtNascimento;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(LocalDate dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public int getIdade() {
		return  (dataAtual.getYear()- getDtNascimento().getYear());
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", email=" + email
				+ ", dtNascimento=" + dtNascimento + "]";
	}

}
