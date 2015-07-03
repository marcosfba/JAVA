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
import br.edu.unitri.util.JpaUtilSession;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class CategoriaBean2 implements Serializable, Operacoes {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Categoria categoria = new Categoria();
	private Categoria categoriaSel;
	private List<Categoria> listaCategoria = new ArrayList<Categoria>();
	
	public CategoriaBean2() {
		super();
	}
	
	@PostConstruct
	public void init() {
		listarTodos();
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Categoria getCategoriaSel() {
		return categoriaSel;
	}

	public void setCategoriaSel(Categoria categoriaSel) {
		this.categoriaSel = categoriaSel;
	}

	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}
	
	@SuppressWarnings("unchecked")
	public void listarTodos(){
		try {
			listaCategoria = JpaUtilSession.currentEM().createQuery("from Categoria").getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Categorias " + e.getMessage()));
		}
	}

	@Override
	public void salvar() {
		try {
			JpaUtilSession.currentEM().persist(getCategoria());
			FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
			   listarTodos();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao incluir Categoria " + e.getMessage()));
		}
	}

	@Override
	public void excluir() {
		if (getCategoriaSel()!=null) {
			try {
				JpaUtilSession.currentEM().remove(getCategoriaSel());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação",
								"Categoria excluido com sucesso "));
				listarTodos();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir Categoria " + e.getMessage()));
			}
		}
	}

	@Override
	public void editar() {
		setCategoria(getCategoriaSel());
	}
	
	@Override
	public void limpar() {
		categoria = new Categoria();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void buscar() {
		String sql = "select m.* from tbCategoria m ";
		String param ="";
		if (!getCategoria().getDescricao().isEmpty()) {
			param = " m.descricao like  '%"+ getCategoria().getDescricao() + "%'";
		}
		
		if (!param.isEmpty()) {
			param = " where " + param;
		}
		
		try {
			listaCategoria.clear();
			listaCategoria = JpaUtilSession.currentEM().createNativeQuery(sql+param,Categoria.class).getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar Categorias " + e.getMessage()));
		}
	}

	@Override
	public void alterar() {
		try {
			JpaUtilSession.currentEM().merge(getCategoria());
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
