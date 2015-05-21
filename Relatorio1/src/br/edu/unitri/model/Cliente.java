/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author marcos.fernando
 *
 */
@Entity
@Table(name = "tbCliente")
public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codCliente;
	@Colunas(nome = "Nome do Cliente", size = 150)
	private String nome;
	@Colunas(nome = "Sexo", size = 100)
	private String sexo;
	@Colunas(nome = "Data de Nascimento", size = 200)
	private Date dtNascimento;
	@Colunas(nome = "Email", size = 250)
	private String email;

	public Cliente() {
		super();
	}

	public Cliente(String nome, String sexo, Date dtNascimento, String email) {
		super();
		this.nome = nome;
		this.sexo = sexo;
		this.dtNascimento = dtNascimento;
		this.email = email;
	}

	public long getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(long codCliente) {
		this.codCliente = codCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (codCliente ^ (codCliente >>> 32));
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
		Cliente other = (Cliente) obj;
		if (codCliente != other.codCliente)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [codCliente=" + codCliente + ", nome=" + nome
				+ ", sexo=" + sexo + ", dtNascimento=" + dtNascimento
				+ ", email=" + email + "]";
	}

}
