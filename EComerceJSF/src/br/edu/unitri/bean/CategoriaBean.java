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
import javax.faces.event.ActionEvent;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import br.edu.unitri.controler.CategoriaControler;
import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Categoria;
import br.edu.unitri.util.JpaUtil;

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
	
	public void limparTela(ActionEvent event) {
		categoria = new Categoria();	
		listarTodos();
	}
	
	@Override
	public void limpar() {
		categoria = new Categoria();	
		listarTodos();
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

	@Override
	public void buscar() {
		
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Categoria> query = cb.createQuery(Categoria.class);
		Root<Categoria> root = query.from(Categoria.class);
		EntityType<Categoria> typeCategoria = JpaUtil.getManager().getMetamodel().entity(Categoria.class);
		
		Predicate where1 = null, where2 = null;
		if (getCategoria().getId() != null) {
			where1 = cb.equal(root.get("id"), getCategoria().getId());
		}
		if (getCategoria().getDescricao() != null) {
			where2 = cb.like(root.get(typeCategoria.getDeclaredSingularAttribute("descricao", String.class)),
					         getCategoria().getDescricao());
		}
		List<Predicate> predicados = new ArrayList<Predicate>(); 
		if (where1 != null) { predicados.add(where1) ; }
		if (where2 != null) { predicados.add(where2) ; }
		
		if (predicados.size() > 0) {
			query.where(cb.or(predicados.toArray(new Predicate[]{})));
		}

		try {
			listaCategoria.clear();
			listaCategoria = JpaUtil.getManager().createQuery(query).getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar categoria " + e.getMessage()));
		} finally {
			if (listaCategoria.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
								"Não há categorias a serem exibidas com argumentos passados"));
			}
		}
	}
}
 