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
import javax.persistence.Transient;

/**
 * @author marcos.fernando
 *
 */
@Entity
public class Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Colunas(nome = "Nome/Razão Social da Pessoa", size = 245)
	private String nome;
	@Colunas(nome = "Email da Pessoa", size = 145)
	private String email;

	@Colunas(nome = "Data de Nascimento", size = 155)
	private LocalDate nascimento;

	@Colunas(nome = "Idade da Pessoa", size = 125)
	@Transient
	private int idade;	
		
	private LocalDate dataAtual = LocalDate.now();

	public Pessoa() {
		super();
	}

	public Pessoa(String nome, String email, LocalDate nascimento) {
		super();
		this.nome = nome;
		this.email = email;
		this.nascimento = nascimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public int getIdade() {
		return  (dataAtual.getYear()- getNascimento().getYear());
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", email=" + email
				+ ", nascimento=" + nascimento + ", idade=" + getIdade() + "]";
	}
	
}
