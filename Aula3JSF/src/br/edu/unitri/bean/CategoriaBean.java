/**
 * 
 */
package br.edu.unitri.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.unitri.controler.CategoriaControler;
import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Categoria;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class CategoriaBean implements Serializable, Operacoes {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Categoria categoria = new Categoria();
	private Categoria categoriaSel;
	private List<Categoria> listaCategoria = new ArrayList<Categoria>();
	private CategoriaControler categoriaControler = new CategoriaControler();
	
	public CategoriaBean() {
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
	
	public void listarTodos(){
		try {
			listaCategoria = categoriaControler.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Categorias " + e.getMessage()));
		}
	}

	@Override
	public void salvar() {
		try {
			if (categoriaControler.save(getCategoria()) != null) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
			   listarTodos();
			}
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
				categoriaControler.delete(getCategoriaSel());
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
			listaCategoria = categoriaControler.findAll(sql,param);
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar Categorias " + e.getMessage()));
		}
	}

	@Override
	public void alterar() {
		try {
			if (categoriaControler.update(getCategoria())) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
				listarTodos();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao editar produto " + e.getMessage()));
		}
	}

}
