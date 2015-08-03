/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.edu.unitri.enumerators.TipoFoto;

/**
 * @author MARCOS FERNANDO
 *
 */
@Entity
@Table(name = "tbFoto")
public class Foto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "produto_id", referencedColumnName = "id")
	private Produto produto;

	@Column(name = "TPFOTO")
	@Enumerated(EnumType.STRING)
	private TipoFoto tipoFoto;

	@Column(name = "IMAGEM", nullable = true)
	private String nomeImagem;

	@Column(name = "EXTENSAO", nullable = true)
	private String extensao;

	@Column(name = "TAMANHO", nullable = true)
	private int tamanho;

	@Column(name = "TAMTUMB", nullable = true)
	private int tamanhoTumb;

	@Column(name = "FOTORIG", nullable = true)
	private String fotoOriginal;

	@Column(name = "FOTTUMB", nullable = true)
	private String fotoTumb;

	public Foto() {
		super();
	}

	public Foto(Long id, Produto produto, TipoFoto tipoFoto, String nomeImagem,
			String extensao, int tamanho, int tamanhoTumb, String fotoOriginal,
			String fotoTumb) {
		super();
		this.id = id;
		this.produto = produto;
		this.tipoFoto = tipoFoto;
		this.nomeImagem = nomeImagem;
		this.extensao = extensao;
		this.tamanho = tamanho;
		this.tamanhoTumb = tamanhoTumb;
		this.fotoOriginal = fotoOriginal;
		this.fotoTumb = fotoTumb;
	}

	public Foto(Produto produto, TipoFoto tipoFoto, String nomeImagem,
			String extensao, int tamanho, int tamanhoTumb, String fotoOriginal,
			String fotoTumb) {
		super();
		this.produto = produto;
		this.tipoFoto = tipoFoto;
		this.nomeImagem = nomeImagem;
		this.extensao = extensao;
		this.tamanho = tamanho;
		this.tamanhoTumb = tamanhoTumb;
		this.fotoOriginal = fotoOriginal;
		this.fotoTumb = fotoTumb;
	}

	public Foto(TipoFoto tipoFoto, String nomeImagem, String extensao,
			int tamanho, int tamanhoTumb, String fotoOriginal, String fotoTumb) {
		super();
		this.tipoFoto = tipoFoto;
		this.nomeImagem = nomeImagem;
		this.extensao = extensao;
		this.tamanho = tamanho;
		this.tamanhoTumb = tamanhoTumb;
		this.fotoOriginal = fotoOriginal;
		this.fotoTumb = fotoTumb;
	}
	

	public Foto(String nomeImagem, String fotoOriginal, String fotoTumb, String extensao) {
		super();
		this.nomeImagem = nomeImagem;
		this.fotoOriginal = fotoOriginal;
		this.fotoTumb = fotoTumb;
		this.extensao = extensao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public TipoFoto getTipoFoto() {
		return tipoFoto;
	}

	public void setTipoFoto(TipoFoto tipoFoto) {
		this.tipoFoto = tipoFoto;
	}

	public String getNomeImagem() {
		return nomeImagem;
	}

	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}

	public String getExtensao() {
		return extensao;
	}

	public void setExtensao(String extensao) {
		this.extensao = extensao;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public int getTamanhoTumb() {
		return tamanhoTumb;
	}

	public void setTamanhoTumb(int tamanhoTumb) {
		this.tamanhoTumb = tamanhoTumb;
	}

	public String getFotoOriginal() {
		return fotoOriginal;
	}

	public void setFotoOriginal(String fotoOriginal) {
		this.fotoOriginal = fotoOriginal;
	}

	public String getFotoTumb() {
		return fotoTumb;
	}

	public void setFotoTumb(String fotoTumb) {
		this.fotoTumb = fotoTumb;
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
		Foto other = (Foto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
