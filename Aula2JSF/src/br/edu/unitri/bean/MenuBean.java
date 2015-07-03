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

	public void cadMarca() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadMarca.xhtml");
	}

	public void cadProduto() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadproduto.xhtml");
	}
	
	public void listMarca() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("listMarca.xhtml");
	}
	
	public void listProduto() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("listProduto.xhtml");
	}
}
