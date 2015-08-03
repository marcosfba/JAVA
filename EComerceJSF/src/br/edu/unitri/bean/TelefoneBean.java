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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import br.edu.unitri.controler.PessoaControler;
import br.edu.unitri.controler.TelefoneControler;
import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Pessoa;
import br.edu.unitri.model.Telefone;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class TelefoneBean implements Serializable, Operacoes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Telefone telefone = new Telefone();
	private Telefone telefoneSel;
	
	private List<Telefone> listaTelefone = new ArrayList<Telefone>();
	private TelefoneControler telefoneControler = new TelefoneControler();
	private PessoaControler pessoaControler = new PessoaControler();	
	private List<Pessoa> listaPessoas = new ArrayList<Pessoa>();
	
	@ManagedProperty("#{usuarioBean}")
	public UsuarioBean usuarioBean;
	
	public TelefoneBean() {
		super();
	}

	@PostConstruct
	public void init() {
		listarTodos();
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Telefone getTelefoneSel() {
		return telefoneSel;
	}

	public void setTelefoneSel(Telefone telefoneSel) {
		this.telefoneSel = telefoneSel;
	}

	public List<Telefone> getListaTelefone() {
		return listaTelefone;
	}

	public void setListaTelefone(List<Telefone> listaTelefone) {
		this.listaTelefone = listaTelefone;
	}

	public List<Pessoa> getListaPessoas() {
		return listaPessoas;
	}

	public void setListaPessoas(List<Pessoa> listaPessoas) {
		this.listaPessoas = listaPessoas;
	}

	public void listarTodos(){
		try {
			listaTelefone = telefoneControler.findAll();
			listaPessoas = pessoaControler.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Telefones " + e.getMessage()));
		}
	}


	@Override
	public void salvar() {
		try {
			if (telefoneControler.save(getTelefone()) != null) {
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
							"Erro ao incluir Telefone " + e.getMessage()));
		}
	}
	
	@Override
	public void excluir() {
		if (getTelefoneSel()!=null) {
			try {
				telefoneControler.delete(getTelefoneSel());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação",
								"Telefone excluido com sucesso "));
				listarTodos();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir Telefone " + e.getMessage()));
			}
		}
	}

	@Override
	public void editar() {
		setTelefone(getTelefoneSel());;
	}

	@Override
	public void limpar() {
		telefone = new Telefone();	
		listarTodos();
	}

	@Override
	public void alterar() {
		try {
			if (telefoneControler.update(getTelefone())) {
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
							"Erro ao editar telefone " + e.getMessage()));
		}
	}

	@Override
	public void buscar() {

		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Telefone> query = cb.createQuery(Telefone.class);
		Root<Telefone> root = query.from(Telefone.class);
		EntityType<Telefone> typeTelefone = JpaUtil.getManager().getMetamodel().entity(Telefone.class);
		
		Predicate where1 = null, where2 = null, where3 = null;
		if (getTelefone().getId() != null) {
			where1 = cb.equal(root.get("id"), getTelefone().getId());
		}
		if (getTelefone().getNumeroTelefone() != null) {
			where2 = cb.like(root.get(typeTelefone.getDeclaredSingularAttribute("numeroTelefone", String.class)),
					         getTelefone().getNumeroTelefone());
		}
		if (getTelefone().getTipoTelefone() != null) {
			where3 = cb.equal(root.get("tipoTelefone"), getTelefone().getTipoTelefone());
		}
		List<Predicate> predicados = new ArrayList<Predicate>(); 
		if (where1 != null) { predicados.add(where1) ; }
		if (where2 != null) { predicados.add(where2) ; }
		if (where3 != null) { predicados.add(where3) ; }
		
		if (predicados.size() > 0) {
			query.where(cb.or(predicados.toArray(new Predicate[]{})));
		}

		try {
			listaTelefone.clear();
			listaTelefone = JpaUtil.getManager().createQuery(query).getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar telefones " + e.getMessage()));
		} finally {
			if (listaTelefone.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
								"Não há telefones a serem exibidas com argumentos passados"));
			}
		}
	}
	
	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	public void salvar(String idUsuario) {
		try {
			getTelefone().setPessoa(usuarioBean.auxListBean.getCliente());
			if (telefoneControler.save(getTelefone()) != null) {
				usuarioBean.auxListBean.loginUser(usuarioBean.getUsuarioSite());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao incluir Telefone " + e.getMessage()));
		}
	}

	public void alterar(String idUsuario) {
		try {
			getTelefone().setPessoa(usuarioBean.auxListBean.getCliente());
			if (telefoneControler.update(getTelefone())) {
				usuarioBean.auxListBean.loginUser(usuarioBean.getUsuarioSite());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao editar telefone " + e.getMessage()));
		}
	}
}
