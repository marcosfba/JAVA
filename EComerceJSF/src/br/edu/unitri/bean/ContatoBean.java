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

import br.edu.unitri.controler.ContatoControler;
import br.edu.unitri.controler.PessoaControler;
import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Contato;
import br.edu.unitri.model.Pessoa;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class ContatoBean implements Serializable, Operacoes {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Contato contato = new Contato();
	private Contato contatoSel;
	
	private List<Contato> listaContato = new ArrayList<Contato>();
	private ContatoControler contatoControler = new ContatoControler();
	private PessoaControler pessoaControler = new PessoaControler();
	private List<Pessoa> listaPessoas = new ArrayList<Pessoa>();
	
	@ManagedProperty("#{usuarioBean}")
	public UsuarioBean usuarioBean;
	
	
	public ContatoBean() {
		super();
	}

	@PostConstruct
	public void init() {
		listarTodos();		
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public Contato getContatoSel() {
		return contatoSel;
	}

	public void setContatoSel(Contato contatoSel) {
		this.contatoSel = contatoSel;
	}

	public List<Contato> getListaContato() {
		return listaContato;
	}

	public void setListaContato(List<Contato> listaContato) {
		this.listaContato = listaContato;
	}

	public List<Pessoa> getListaPessoas() {
		return listaPessoas;
	}

	public void setListaPessoas(List<Pessoa> listaPessoas) {
		this.listaPessoas = listaPessoas;
	}

	public void listarTodos(){
		try {
			listaContato = contatoControler.findAll();
			listaPessoas = pessoaControler.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Contatos " + e.getMessage()));
		}
	}
	

	@Override
	public void salvar() {
		try {
			if (contatoControler.save(getContato()) != null) {
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
							"Erro ao incluir Contato " + e.getMessage()));
		}
	}

	@Override
	public void excluir() {
		if (getContatoSel()!=null) {
			try {
				contatoControler.delete(getContatoSel());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação",
								"Contato excluido com sucesso "));
				listarTodos();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir Contato " + e.getMessage()));
			}
		}
	}

	@Override
	public void editar() {
		setContato(getContatoSel());;
	}

	@Override
	public void limpar() {
		contato = new Contato();	
		listarTodos();
	}
	
	@Override
	public void alterar() {
		try {
			if (contatoControler.update(getContato())) {
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
							"Erro ao editar contato " + e.getMessage()));
		}
	}

	@Override
	public void buscar() {
		
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Contato> query = cb.createQuery(Contato.class);
		Root<Contato> root = query.from(Contato.class);
		
		Predicate where1 = null, where2 = null;
		if (getContato().getId() != null) {
			where1 = cb.equal(root.get("id"), getContato().getId());
		}
		if (getContato().getTipoContato() != null) {
			where2 = cb.equal(root.get("tipoContato"), getContato().getTipoContato());
		}
		List<Predicate> predicados = new ArrayList<Predicate>(); 
		if (where1 != null) { predicados.add(where1) ; }
		if (where2 != null) { predicados.add(where2) ; }
		
		if (predicados.size() > 0) {
			query.where(cb.or(predicados.toArray(new Predicate[]{})));
		}

		try {
			listaContato.clear();
			listaContato = JpaUtil.getManager().createQuery(query).getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar contatos " + e.getMessage()));
		} finally {
			if (listaContato.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
								"Não há contatos a serem exibidas com argumentos passados"));
			}
		}
	}
	
	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	public void salvar(String idUsuario){
		try {
			getContato().setPessoa(usuarioBean.auxListBean.getCliente());
			if (contatoControler.save(getContato()) != null) {
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
							"Erro ao incluir Contato " + e.getMessage()));
		}
	}

	public void alterar(String idUsuario){
		try {
			getContato().setPessoa(usuarioBean.auxListBean.getCliente());
			if (contatoControler.update(getContato())) {
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
							"Erro ao editar contato " + e.getMessage()));
		}
	}
}
 