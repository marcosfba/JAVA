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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import br.edu.unitri.controler.MarcaControler;
import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Marca;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class MarcaBean implements Serializable, Operacoes {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Marca marca = new Marca();
	private Marca marcaSel;
	private List<Marca> listaMarca = new ArrayList<Marca>();
	private MarcaControler marcaControler = new MarcaControler();

	
	public MarcaBean() {
		super();
	}
	
	@PostConstruct
	public void init() {
		listarTodos();
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Marca getMarcaSel() {
		return marcaSel;
	}

	public void setMarcaSel(Marca marcaSel) {
		this.marcaSel = marcaSel;
	}

	public List<Marca> getListaMarca() {
		return listaMarca;
	}

	public void setListaMarca(List<Marca> listaMarca) {
		this.listaMarca = listaMarca;
	}
	
	public void listarTodos(){
		try {
			listaMarca = marcaControler.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Marcas " + e.getMessage()));
		}
	}

	@Override
	public void salvar() {
		try {
			if (marcaControler.save(getMarca()) != null) {
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
							"Erro ao incluir Marca " + e.getMessage()));
		}
	}

	@Override
	public void excluir() {
		if (getMarcaSel()!=null) {
			try {
				marcaControler.delete(getMarcaSel());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação",
								"Marca excluida com sucesso "));
				listarTodos();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir Marca " + e.getMessage()));
			}
		}
	}

	@Override
	public void editar() {
		setMarca(getMarcaSel());
	}
	
	@Override
	public void limpar() {
		marca = new Marca();	
		listarTodos();
	}

	@Override
	public void alterar() {
		try {
			if (marcaControler.update(getMarca())) {
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
		CriteriaQuery<Marca> query = cb.createQuery(Marca.class);
		Root<Marca> root = query.from(Marca.class);
		EntityType<Marca> typeMarca = JpaUtil.getManager().getMetamodel().entity(Marca.class);
		
		Predicate where1 = null, where2 = null;
		if (getMarca().getId() != null) {
			where1 = cb.equal(root.get("id"), getMarca().getId());
		}
		if (getMarca().getDescricao() != null) {
			where2 = cb.like(root.get(typeMarca.getDeclaredSingularAttribute("descricao", String.class)),
					         getMarca().getDescricao());
		}
		List<Predicate> predicados = new ArrayList<Predicate>(); 
		if (where1 != null) { predicados.add(where1) ; }
		if (where2 != null) { predicados.add(where2) ; }
		
		if (predicados.size() > 0) {
			query.where(cb.or(predicados.toArray(new Predicate[]{})));
		}

		try {
			listaMarca.clear();
			listaMarca = JpaUtil.getManager().createQuery(query).getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar marca " + e.getMessage()));
		} finally {
			if (listaMarca.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
								"Não há marcas a serem exibidas com argumentos passados"));
			}
		}
	}

}
 