/**
 * 
 */
package br.edu.unitri.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.edu.unitri.model.Media;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@ViewScoped
public class MediaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Media media = new Media();
	private Double total = 0D;
	private Double resultado = 0D;

	public MediaBean() {
		super();
	}

	public MediaBean(Media media, Double total, Double resultado) {
		super();
		this.media = media;
		this.total = total;
		this.resultado = resultado;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getResultado() {
		return resultado;
	}

	public void setResultado(Double resultado) {
		this.resultado = resultado;
	}

	public void adicionar() {
		this.total += getMedia().getValor();
		getMedia().setCount(getMedia().getCount() + 1);
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
						"Valor recebido"));
	}

	public void calcular() {
		try {
			getMedia().setValor(this.total);
			this.resultado = getMedia().calcularMedia();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
							"Operação realizada com sucesso"));
		   this.total = 0D;
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO",
							"Erro de execução na operação " + e.getMessage()));
		}
	}

	public void limpar() {
		media = new Media(0D, 0);
		new MediaBean(media,0D,0D);
	}

}
