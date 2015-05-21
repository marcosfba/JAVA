/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
	
	@Temporal(TemporalType.DATE)
	@Colunas(nome = "Data de Nascimento", size = 150)
	private Date dtNascimento;

	@Transient
	@Colunas(nome = "Idade da Pessoa", size = 175)
	private int idade;
	@Transient
	private Date dataAtual = new Date();
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "endereco_id", referencedColumnName="id")
	@JoinTable(name = "Pessoa_Endereco", joinColumns = @JoinColumn(name = "pessoa_id"), inverseJoinColumns = @JoinColumn(name = "endereco_id"))
	private Collection<Endereco> enderecos;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "contato_id", referencedColumnName="id")
	@JoinTable(name = "Pessoa_Contato", joinColumns = @JoinColumn(name = "pessoa_id"), inverseJoinColumns = @JoinColumn(name = "contato_id"))
	private Collection<Contato> contatos;

	public Pessoa() {
		super();
	}

	public Pessoa(String nome, String email, Date dtNascimento) {
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

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	@SuppressWarnings("deprecation")
	public int getIdade() {
		return  (dataAtual.getYear()- getDtNascimento().getYear());
	}

	public Collection<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Collection<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Collection<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(Collection<Contato> contatos) {
		this.contatos = contatos;
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
		return "Pessoa com id: " +  id + " com nome " + nome;
	}

}
