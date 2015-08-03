/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author MARCOS FERNANDO
 *
 */
@Entity
@Table(name = "tbFornecedor")
@PrimaryKeyJoinColumn(name="id")
public class Fornecedor extends Pessoa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "CNPJ", length = 14, nullable = true)
	private String cnpj;

	@Column(name = "INSCMUN", length = 20, nullable = true)
	private String inscMunicipal;

	@Column(name = "INSCEST", length = 20, nullable = true)
	private String insEstadual;

	public Fornecedor() {
		super();
	}

	public Fornecedor(Long id, String nome, String tipoPessoa,
			Collection<Endereco> listaEnderecos,
			Collection<Contato> listaContatos,
			Collection<Telefone> listaTelefones, Usuario usuario,
			String observacao) {
		super(id, nome, tipoPessoa, listaEnderecos, listaContatos,
				listaTelefones, usuario, observacao);
	}

	public Fornecedor(Long id, String nome, String tipoPessoa, String observacao) {
		super(id, nome, tipoPessoa, observacao);
	}

	public Fornecedor(String cnpj, String inscMunicipal, String insEstadual) {
		super();
		this.cnpj = cnpj;
		this.inscMunicipal = inscMunicipal;
		this.insEstadual = insEstadual;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscMunicipal() {
		return inscMunicipal;
	}

	public void setInscMunicipal(String inscMunicipal) {
		this.inscMunicipal = inscMunicipal;
	}

	public String getInsEstadual() {
		return insEstadual;
	}

	public void setInsEstadual(String insEstadual) {
		this.insEstadual = insEstadual;
	}

}
