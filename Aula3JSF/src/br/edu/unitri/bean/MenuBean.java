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

	public void validacaoManual() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("validManual.xhtml");
	}
	
	public void validacaImplicita() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("validImplicita.xhtml");
	}
	
	public void validacaoExplicita() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("validExplicita.xhtml");
	}
	
	public void validacaoPropria() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("validPropria.xhtml");
	}
	
	public void validateNumber() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("validNumber.xhtml");
	}
	
	public void validateData() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("validDate.xhtml");
	}
	
	public void validateRegex() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("validRegex.xhtml");
	}
	
	public void validateCpRequired() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("validRequired.xhtml");
	}
	
	public void cadCategoria() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadCategoria.xhtml");
	}

	public void cadProduto() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadProduto.xhtml");
	}

	public void cadCategoria2() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadCategoria2.xhtml");
	}

	public void cadProduto2() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadProduto2.xhtml");
	}
	
	public void cadCategoria3() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("cadCategoria2.xhtml");
	}

	public void cadProduto3() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("cadProduto2.xhtml");
	}
	
}
