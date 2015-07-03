/**
 * 
 */
package br.edu.unitri.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Categoria;
import br.edu.unitri.model.Produto;
import br.edu.unitri.util.JpaUtilSession;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class ProdutoBean2 implements Serializable, Operacoes {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Produto produto = new Produto();
	private Produto prodSel;
	private List<Produto> listaProdutos = new ArrayList<Produto>();
	private List<Produto> listaProdutosSel = new ArrayList<Produto>();
	private List<Categoria> listaCategorias = new ArrayList<Categoria>();

	public ProdutoBean2() {
		super();
	}
	
	@PostConstruct
	public void init(){
		listarTodos();
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
	
	@SuppressWarnings("unchecked")
	public void listarTodos(){
		try {
			listaProdutos = JpaUtilSession.currentEM().createQuery("from Produto").getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Produtos " + e.getMessage()));
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> getListaCategorias() {
		try {
			listaCategorias = JpaUtilSession.currentEM().createQuery("from Categoria").getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar categorias " + e.getMessage()));
		}
		return listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public List<Produto> getListaProdutosSel() {
		return listaProdutosSel;
	}

	public void setListaProdutosSel(List<Produto> listaProdutosSel) {
		this.listaProdutosSel = listaProdutosSel;
	}

	@Override
	public void salvar() {
		try {
			JpaUtilSession.currentEM().persist(getProduto());
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
							"Operação realizada com sucesso"));
			listarTodos();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao incluir produto " + e.getMessage()));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buscar() {
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
			listaProdutos = JpaUtilSession.currentEM().createNativeQuery(sql+param,Produto.class).getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar produtos " + e.getMessage()));
		}
	}

	@Override
	public void excluir() {
		if (getProdSel()!=null) {
			try {
				JpaUtilSession.currentEM().remove(getProdSel());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação",
								"Produto excluido com sucesso "));
				listarTodos();				
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir produto " + e.getMessage()));
			}
		}
	}

	@Override
	public void editar() {
		setProduto(getProdSel());
	}

	@Override
	public void limpar() {
		produto = new Produto();

	}

	@Override
	public void alterar() {
		try {
			JpaUtilSession.currentEM().merge(getProduto());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
				listarTodos();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao editar produto " + e.getMessage()));
		}
		
	}

}
