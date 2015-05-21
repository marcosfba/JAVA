/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;

/**
 * @author marcos.fernando
 *
 */
public class AutorLivroDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Colunas(nome = "Nome do Autor", size = 245)
	private String nomeAutor;
	@Colunas(nome = "Nome do Livro", size = 245)
	private String nomeLivro;

	public AutorLivroDTO() {
		super();
	}

	public AutorLivroDTO(String nomeAutor, String nomeLivro) {
		super();
		this.nomeAutor = nomeAutor;
		this.nomeLivro = nomeLivro;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public void setNomeAutor(String nomeAutor) {
		this.nomeAutor = nomeAutor;
	}

	public String getNomeLivro() {
		return nomeLivro;
	}

	public void setNomeLivro(String nomeLivro) {
		this.nomeLivro = nomeLivro;
	}

	@Override
	public String toString() {
		return "AutorLivroDTO [nomeAutor=" + nomeAutor + ", nomeLivro="
				+ nomeLivro + "]";
	}

}
