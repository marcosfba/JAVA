/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;

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
@Table(name = "tbEndereco")
public class Endereco implements Serializable {

	/**
	 * 
	 */
	public enum TipoEndereco {
		RESIDENCIAL("Endereço Residencial"), 
		COMERCIAL("Endereço Comercial"),
		REFERENCIA("Endereço de Referência"),
		ALUGUEL("Endereço de Aluguel"),
		CASAMAE("Endereço da Casa da Mãe"),
		CASAPAI("Endereço da Casa do Pai"),
		OUTROS("Outros");
		
		public final String opcao;
		
		TipoEndereco(String opcao) {
	       this.opcao = opcao;
		}
		
		public String toString(){
			return opcao;
		}
	};

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private TipoEndereco tipoEndereco;
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

	public Endereco(TipoEndereco tipoEndereco, String pais, String estado,
			String cidade, String logradouro, int numero, String complemento,
			int cep) {
		super();
		this.tipoEndereco = tipoEndereco;
		this.pais = pais;
		this.estado = estado;
		this.cidade = cidade;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.cep = cep;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TipoEndereco getTipoEndereco() {
		return tipoEndereco;
	}

	public void setTipoEndereco(TipoEndereco tipoEndereco) {
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
		Endereco other = (Endereco) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Endereco [id=" + id + ", tipoEndereco=" + tipoEndereco
				+ ", pais=" + pais + ", estado=" + estado + ", cidade="
				+ cidade + ", logradouro=" + logradouro + ", numero=" + numero
				+ ", complemento=" + complemento + ", cep=" + cep + "]";
	}

}
