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
@Table(name = "tbContato")
public class Contato implements Serializable {

	/**
	 * 
	 */
	public static enum TipoContato {
		
		TELEFONERES("Telefone Residencial"), 
		TELEFONECEL("Telefone Celular"), 
		EMAIL("Email"), 
		TELEFONECONT("Telefone Contato"), 
		OUTROS("Outros");
		
		public final String opcao;
		
		TipoContato(String opcao) {
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

	private TipoContato tipoContato;
	private String descricao;
	private String complemento;

	public Contato() {
		super();
	}

	public Contato(TipoContato tipoContato, String descricao,
			String complemento) {
		super();
		this.tipoContato = tipoContato;
		this.descricao = descricao;
		this.complemento = complemento;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TipoContato getTipoContato() {
		return tipoContato;
	}

	public void setTipoContato(TipoContato tipoContato) {
		this.tipoContato = tipoContato;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
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
		Contato other = (Contato) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Contato [id=" + id + ", tipoContato=" + tipoContato
				+ ", descricao=" + descricao + ", complemento=" + complemento
				+ "]";
	}

}
