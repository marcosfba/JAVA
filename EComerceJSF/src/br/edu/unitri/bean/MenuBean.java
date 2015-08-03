/**
 * 
 */
package br.edu.unitri.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * @author marcos.fernando
 *
 */
@SessionScoped
@ManagedBean
public class MenuBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void index() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("index.xhtml");
	}

	public void administrador() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("pages/adm/mainAdm.xhtml");
	}

	public void userSite() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("pages/user/mainUser.xhtml");
	}

	public void indexSite() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("main.xhtml");
	}

	public void userComum() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("pages/site/main.xhtml");
	}

	public void admCategoria() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadCategoria.xhtml");
	}

	public void admMarca() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadMarca.xhtml");
	}

	public void admTelefone() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadTelefone.xhtml");
	}

	public void admUsuario() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadUsuario.xhtml");
	}

	public void admEndereco() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadEndereco.xhtml");
	}

	public void admTipoContato() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadContato.xhtml");
	}

	public void admPessoa() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadPessoa.xhtml");
	}

	public void admCliente() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadCliente.xhtml");
	}

	public void admPedido() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadPedido.xhtml");
	}

	public void admFornecedor() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadFornecedor.xhtml");
	}

	public void admProduto() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("cadProduto.xhtml");
	}

	public void admConsCategoria() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("consCategoria.xhtml");
	}

	public void admConsCliente() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("consCliente.xhtml");
	}

	public void admConsContato() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("consContato.xhtml");
	}

	public void admConsEndereco() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("consEndereco.xhtml");
	}

	public void admConsFornecedor() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("consFornecedor.xhtml");
	}

	public void admConsMarca() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("consMarca.xhtml");
	}

	public void admConsPessoa() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("consPessoa.xhtml");
	}

	public void admConsTelefone() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("consTelefone.xhtml");
	}

	public void admConsUsuario() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("consUsuario.xhtml");
	}

	public void admConsProduto() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("consProduto.xhtml");
	}

	public void admConsPedido() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("consPedido.xhtml");
	}

	public void admRelUsuario() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("relUsuario.xhtml");
	}

	public void admRelCliente() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("relCliente.xhtml");
	}

	public void admRelFornecedor() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("relFornecedor.xhtml");
	}

	public void admRelPedido() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("relPedido.xhtml");
	}
	
	public void admRelProdutos() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("relProdutos.xhtml");
	}
	
	public void admGrafClientes() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("grafClientes.xhtml");
	}

	public void admGrafProdutos() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("grafProdutos.xhtml");
	}

	public void admGrafOperacoes() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("grafOperacoes.xhtml");
	}

	public void userProdFiltro() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("prodFiltro.xhtml");
	}

	public void userViewCarrinho() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("prodCarrinho.xhtml");
	}

	public void userViewPedido() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("prodFechamento.xhtml");
	}

	public void userLogadoIndex() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("index.xhtml");
	}
	
	public void userLogadoProdFiltro() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("userProdFiltro.xhtml");
	}

	public void userLogadoCarrinho() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("userProdCarrinho.xhtml");
	}

	public void userLogadoFechamento() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("userFechamento.xhtml");
	}
}
