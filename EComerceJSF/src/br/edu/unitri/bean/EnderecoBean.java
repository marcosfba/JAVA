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

import br.edu.unitri.controler.EnderecoControler;
import br.edu.unitri.controler.PessoaControler;
import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Endereco;
import br.edu.unitri.model.Pessoa;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class EnderecoBean implements Serializable, Operacoes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Endereco endereco = new Endereco();
	private Endereco enderecoSel;
	
	private List<Endereco> listaEndereco = new ArrayList<Endereco>();
	private List<Pessoa> listaPessoas = new ArrayList<Pessoa>();
	
	private EnderecoControler enderecoControler = new EnderecoControler();
	private PessoaControler pessoaControler = new PessoaControler();
	
	@ManagedProperty("#{usuarioBean}")
	public UsuarioBean usuarioBean;

	public EnderecoBean() {
		super();
	}

	@PostConstruct
	public void init() {
		listarTodos();
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Endereco getEnderecoSel() {
		return enderecoSel;
	}

	public void setEnderecoSel(Endereco enderecoSel) {
		this.enderecoSel = enderecoSel;
	}

	public List<Endereco> getListaEndereco() {
		return listaEndereco;
	}

	public void setListaEndereco(List<Endereco> listaEndereco) {
		this.listaEndereco = listaEndereco;
	}

	public List<Pessoa> getListaPessoas() {
		return listaPessoas;
	}

	public void setListaPessoas(List<Pessoa> listaPessoas) {
		this.listaPessoas = listaPessoas;
	}

	public void listarTodos(){
		try {
			listaEndereco = enderecoControler.findAll();
			listaPessoas = pessoaControler.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Enderecos " + e.getMessage()));
		}
	}


	@Override
	public void salvar() {
		try {
			if (enderecoControler.save(getEndereco()) != null) {
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
							"Erro ao incluir Endereco " + e.getMessage()));
		}
	}

	@Override
	public void excluir() {
		if (getEnderecoSel()!=null) {
			try {
				enderecoControler.delete(getEnderecoSel());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação",
								"Endereco excluido com sucesso "));
				listarTodos();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir Endereco " + e.getMessage()));
			}
		}
	}

	@Override
	public void editar() {
		setEndereco(getEnderecoSel());;
	}

	@Override
	public void limpar() {
		endereco = new Endereco();	
		listarTodos();
	}
	
	@Override
	public void alterar() {
		try {
			if (enderecoControler.update(getEndereco())) {
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
							"Erro ao editar endereco " + e.getMessage()));
		}
	}

	@Override
	public void buscar() {
		
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Endereco> query = cb.createQuery(Endereco.class);
		Root<Endereco> root = query.from(Endereco.class);
		
		Predicate where1 = null, where2 = null, where3 = null, where4 = null;
		if (getEndereco().getId() != null) {
			where1 = cb.equal(root.get("id"), getEndereco().getId());
		}
		if (getEndereco().getEstado() != null) {
			where2 = cb.equal(root.get("estado"), getEndereco().getEstado());
		}
		if (getEndereco().getCidade() != null) {
			where3 = cb.equal(root.get("cidade"), getEndereco().getCidade());
		}
		if (getEndereco().getTipoEndereco() != null) {
			where4 = cb.equal(root.get("tipoEndereco"), getEndereco().getTipoEndereco());
		}
		List<Predicate> predicados = new ArrayList<Predicate>(); 
		if (where1 != null) { predicados.add(where1) ; }
		if (where2 != null) { predicados.add(where2) ; }
		if (where3 != null) { predicados.add(where3) ; }
		if (where4 != null) { predicados.add(where4) ; }
		
		if (predicados.size() > 0) {
			query.where(cb.or(predicados.toArray(new Predicate[]{})));
		}

		try {
			listaEndereco.clear();
			listaEndereco = JpaUtil.getManager().createQuery(query).getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar endereços " + e.getMessage()));
		} finally {
			if (listaEndereco.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
								"Não há endereços a serem exibidos com argumentos passados"));
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
			getEndereco().setPessoa(usuarioBean.auxListBean.getCliente());
			if (enderecoControler.save(getEndereco()) != null) {
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
							"Erro ao incluir Endereco " + e.getMessage()));
		}
	}
	
	public void alterar(String idUsuario){
		try {
			getEndereco().setPessoa(usuarioBean.auxListBean.getCliente());
			if (enderecoControler.update(getEndereco())) {
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
							"Erro ao editar endereco " + e.getMessage()));
		}
	}
}
