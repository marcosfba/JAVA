/**
 * 
 */
package br.edu.unitri.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.unitri.controler.ProdutoControler;
import br.edu.unitri.model.Produto;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class ProdutoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Produto produto = new Produto();
	private Produto prodSel;
	private List<Produto> listaProdutos = new ArrayList<Produto>();
	private ProdutoControler pdr = new ProdutoControler();

	public ProdutoBean() {
		super();
	}
	
	@PostConstruct
	public void init(){
		try {
			listaProdutos = pdr.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar produtos " + e.getMessage()));
		}
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}
	
	public Produto getProdSel() {
		return prodSel;
	}

	public void setProdSel(Produto prodSel) {
		this.prodSel = prodSel;
	}

	public void limpar(){
		produto = new Produto();
	}
	
	public void salvar(){
		try {
			if (pdr.save(getProduto()) != null) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao incluir produto " + e.getMessage()));
		}
	}
	
	public void buscar(){
		
		String sql = "select p.* from tbProduto p ";
		String param ="";
		if (!getProduto().getDescricao().isEmpty()) {
			param = " p.descricao like  '%"+ getProduto().getDescricao() + "%'";
		}
		if (!getProduto().getCodigo().isEmpty()) {
			if (param.isEmpty()) {
				param = " p.codigo = '"+ getProduto().getCodigo() +"'";
			} else {
				param += " and p.codigo = '"+ getProduto().getCodigo() +"'";
			}
		}
		
		if (!param.isEmpty()) {
			param = " where " + param;
		}
		
		try {
			listaProdutos.clear();
			listaProdutos = pdr.findAll(sql,param);
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar produtos " + e.getMessage()));
		}
	}
	
	public void excluir(){
		if (getProdSel()!=null) {
			try {
				pdr.delete(getProdSel());
				buscar();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação",
								"Produto excluido com sucesso "));
				
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir produto " + e.getMessage()));
			}
		}
		
	}
	
	public void editar() {
		this.produto = getProdSel();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("editproduto.xhtml");
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao tentar editar produto " + e.getMessage()));
		}
	}
	
	public void alterar() {
		try {
			if (pdr.update(getProduto(),(int)getProduto().getId())) {
				limpar();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao editar produto " + e.getMessage()));
		}
	}
}
