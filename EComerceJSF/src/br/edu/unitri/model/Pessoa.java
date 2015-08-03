/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author MARCOS FERNANDO
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "tbPessoa")
public class Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME", length = 100, nullable = true)
	private String nome;

	@Column(name = "TIPO", length = 1, nullable = true)
	private String tipoPessoa;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "pessoa", cascade=CascadeType.REMOVE)
	private Collection<Endereco> listaEnderecos;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "pessoa", cascade=CascadeType.REMOVE)
	private Collection<Contato> listaContatos;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "pessoa", cascade=CascadeType.REMOVE)
	private Collection<Telefone> listaTelefones;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;

	@Column(name = "OBSERV")
	private String observacao;

	public Pessoa() {
		super();
	}

	public Pessoa(Long id, String nome, String tipoPessoa,
			Collection<Endereco> listaEnderecos,
			Collection<Contato> listaContatos,
			Collection<Telefone> listaTelefones, Usuario usuario,
			String observacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.tipoPessoa = tipoPessoa;
		this.listaEnderecos = listaEnderecos;
		this.listaContatos = listaContatos;
		this.listaTelefones = listaTelefones;
		this.usuario = usuario;
		this.observacao = observacao;
	}

	public Pessoa(Long id, String nome, String tipoPessoa, String observacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.tipoPessoa = tipoPessoa;
		this.observacao = observacao;
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
	
	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Collection<Endereco> getListaEnderecos() {
		return listaEnderecos;
	}

	public void setListaEnderecos(Collection<Endereco> listaEnderecos) {
		this.listaEnderecos = listaEnderecos;
	}

	public Collection<Contato> getListaContatos() {
		return listaContatos;
	}

	public void setListaContatos(Collection<Contato> listaContatos) {
		this.listaContatos = listaContatos;
	}

	public Collection<Telefone> getListaTelefones() {
		return listaTelefones;
	}

	public void setListaTelefones(Collection<Telefone> listaTelefones) {
		this.listaTelefones = listaTelefones;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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

}
