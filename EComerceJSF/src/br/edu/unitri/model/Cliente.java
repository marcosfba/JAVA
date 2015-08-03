/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author MARCOS FERNANDO
 *
 */
@Entity
@Table(name = "tbCliente")
@PrimaryKeyJoinColumn(name="id")
public class Cliente extends Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private Date dtNascimento;

	@Column(name = "CPF", length = 11, nullable = true)
	private String cpf;

	@Column(name = "RG", length = 20, nullable = true)
	private String identidade;

	@Column(name = "TITULO", length = 35, nullable = true)
	private String titEleitor;

	@Column(name = "CNH", length = 25, nullable = true)
	private String cnh;

	public Cliente() {
		super();
	}

	public Cliente(Long id, String nome, String tipoPessoa,
			Collection<Endereco> listaEnderecos,
			Collection<Contato> listaContatos,
			Collection<Telefone> listaTelefones, Usuario usuario,
			String observacao) {
		super(id, nome, tipoPessoa, listaEnderecos, listaContatos,
				listaTelefones, usuario, observacao);
	}

	public Cliente(Long id, String nome, String tipoPessoa, String observacao) {
		super(id, nome, tipoPessoa, observacao);
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getIdentidade() {
		return identidade;
	}

	public void setIdentidade(String identidade) {
		this.identidade = identidade;
	}

	public String getTitEleitor() {
		return titEleitor;
	}

	public void setTitEleitor(String titEleitor) {
		this.titEleitor = titEleitor;
	}

	public String getCnh() {
		return cnh;
	}

	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	@Override
	public String toString() {
		return "Cliente [dtNascimento=" + dtNascimento + ", cpf=" + cpf
				+ ", identidade=" + identidade + ", titEleitor=" + titEleitor
				+ ", cnh=" + cnh + "]";
	}
	

}
