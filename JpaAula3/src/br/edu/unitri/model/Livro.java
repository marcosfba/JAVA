/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * @author marcos.fernando
 *
 */
@Entity
public class Livro implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Colunas(nome = "Nome do Livro", size = 145)
	private String nome;
	@Colunas(nome = "Nome da Editora", size = 145)
	private String editora;
	@ManyToMany
	@JoinTable(name = "Liv_Aut", joinColumns = @JoinColumn(name = "Liv_ID"), inverseJoinColumns = @JoinColumn(name = "Aut_ID"))
	private Collection<Autor> autores;

	public Livro() {
		super();
	}

	public Livro(String nome, String editora) {
		super();
		this.nome = nome;
		this.editora = editora;
	}

	public Livro(String nome, String editora, Collection<Autor> autores) {
		super();
		this.nome = nome;
		this.editora = editora;
		this.autores = autores;
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

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public Collection<Autor> getAutores() {
		return autores;
	}

	public void setAutores(Collection<Autor> autores) {
		this.autores = autores;
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
		Livro other = (Livro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome  + "->" + editora;
	}

}
