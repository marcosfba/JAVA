/**
 * 
 */
package br.edu.unitri.bean;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * @author marcos.fernando
 *
 */
@RequestScoped
@ManagedBean
public class MenuBean {

	public void index() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("index.xhtml");
	}

	public void calculator() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("calculadora.xhtml");
	}

	public void fibonacci() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("fibonacci.xhtml");
	}

	public void telefone() throws IOException {
		
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("telefone.xhtml");
	}

	public void media() throws IOException {
		
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("media.xhtml");
	}

	public void login() throws IOException {
		
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("login.xhtml");
	}

	public void exercicioFinal() throws IOException {
		
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("exercicioFinal.xhtml");
	}
	
	public void cadProduto() throws IOException {
		
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadproduto.xhtml");
	}
	
	public void listProduto() throws IOException {
		
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("listProduto.xhtml");
	}

	public void inicio() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("index1.xhtml");
	}
}
