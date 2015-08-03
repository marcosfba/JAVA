/**
 * 
 */
package br.edu.unitri.bean;

import java.io.FileNotFoundException;
import java.io.IOException;
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

import net.sf.jasperreports.engine.JRException;
import br.edu.unitri.controler.UsuarioControler;
import br.edu.unitri.dataSource.DadosFactory;
import br.edu.unitri.enumerators.TipoUsuario;
import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Usuario;
import br.edu.unitri.security.SessionContext;
import br.edu.unitri.util.JpaUtil;

/**
 * @author Marcos
 *
 */
@ManagedBean
@SessionScoped
public class UsuarioBean implements Operacoes, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();
	private Usuario usuarioSite = new Usuario();
	private UsuarioControler usuarioControler = new UsuarioControler();
	private List<Usuario> listaUsuarios = new ArrayList<Usuario>();
	private Usuario usuarioSel = new Usuario();
	
	@ManagedProperty("#{auxListBean}")
	public AuxListBean auxListBean;

	
	public UsuarioBean() {
		super();
	}

	@PostConstruct
	public void init() {
		listarTodos();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getUsuarioSel() {
		return usuarioSel;
	}

	public void setUsuarioSel(Usuario usuarioSel) {
		this.usuarioSel = usuarioSel;
	}
	
	public Usuario getUsuarioSite() {
		return usuarioSite;
	}

	public void setUsuarioSite(Usuario usuarioSite) {
		this.usuarioSite = usuarioSite;
	}

	public AuxListBean getAuxListBean() {
		return auxListBean;
	}

	public void setAuxListBean(AuxListBean auxListBean) {
		this.auxListBean = auxListBean;
	}

	public void listarTodos(){
		try {
			listaUsuarios = usuarioControler.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar usuários " + e.getMessage()));
		}
	}

	@Override
	public void salvar() {
		try {
			if (getUsuario().getTipoUsuario() == null) {
				getUsuario().setTipoUsuario(TipoUsuario.USER);
			}
			if (usuarioControler.save(getUsuario()) != null) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
				limpar();
				listarTodos();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao incluir Usuário " + e.getMessage()));
		}
	}
	
	public void consultar(){

		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
		Root<Usuario> root = query.from(Usuario.class);
		EntityType<Usuario> typeCategoria = JpaUtil.getManager().getMetamodel().entity(Usuario.class);
		
		Predicate where1 = null, where2 = null, where3 = null;
		if (getUsuario().getId() != null) {
			where1 = cb.equal(root.get("id"), getUsuario().getId());
		}
		if (getUsuario().getUserName() != null) {
			where2 = cb.equal(root.get(typeCategoria.getDeclaredSingularAttribute("userName", String.class)),
					         getUsuario().getUserName());
		}
		if (getUsuario().getEmail() != null) {
			where3 = cb.equal(root.get(typeCategoria.getDeclaredSingularAttribute("email", String.class)),
					         getUsuario().getEmail());
		}
		List<Predicate> predicados = new ArrayList<Predicate>(); 
		if (where1 != null) { predicados.add(where1) ; }
		if (where2 != null) { predicados.add(where2) ; }
		if (where3 != null) { predicados.add(where3) ; }
		
		if (predicados.size() > 0) {
			query.where(cb.or(predicados.toArray(new Predicate[]{})));
		}

		try {
			listaUsuarios.clear();
			listaUsuarios = JpaUtil.getManager().createQuery(query).getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar usuários " + e.getMessage()));
		} finally {
			if (listaUsuarios.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
								"Não há usuários a serem exibidas com argumentos passados"));
			}
		}
	}

	@Override
	public void buscar() {
		usuario = usuarioControler.getUsuario(getUsuario().getEmail());
		if (usuario == null) { 
			usuario = new Usuario(); 
			FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário não encontrado!", "Erro no Login!"));
		}
	}

	@Override
	public void excluir() {
		if (getUsuarioSel()!=null) {
			try {
				usuarioControler.delete(getUsuarioSel());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação",
								"Usuário excluido com sucesso "));
				listarTodos();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir Usuário " + e.getMessage()));
			}
		}
	}

	@Override
	public void editar() {
		setUsuario(getUsuarioSel());				
	}

	@Override
	public void limpar() {
		usuario = new Usuario();	
		listarTodos();
	}

	@Override
	public void alterar() {
		try {
			if (usuarioControler.update(getUsuario())) {
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
							"Erro ao editar usuário " + e.getMessage()));
		}
	}
	
	public String logarUser() throws IOException {
		if (getUsuario() == null) {
			return "index.xhtml?faces-redirect=true";
		}
		usuarioSite = usuarioControler.getUsuario(getUsuario().getUserName(),
				getUsuario().getPassword());
		if (usuarioSite == null) {
			FacesContext.getCurrentInstance().addMessage("messUser",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro no login de usuario"));
		} else {
			if (usuarioSite.getTipoUsuario().equals(TipoUsuario.USERSITE)) {
				SessionContext.getInstance().setAttribute("usuarioLogado", usuarioSite);
				auxListBean.loginUser(usuarioSite);;
				return "/pages/user/index.xhtml?faces-redirect=true";
			} else {
				FacesContext.getCurrentInstance().addMessage("messUser",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Login e senha inexistente"));
			}
		}
		return null;
	}

	public String logar() throws IOException {
		if (getUsuario() == null) {
			return "index.xhtml?faces-redirect=true";
		}
		usuarioSite = usuarioControler.getUsuario(getUsuario().getUserName(),
				getUsuario().getPassword());
		if (usuarioSite == null) {
			FacesContext.getCurrentInstance().addMessage("messAdm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro no login de usuario - usuário não encontrado"));
		} else {
			SessionContext.getInstance().setAttribute("usuarioLogado", usuarioSite);
			if (usuarioSite.getTipoUsuario().equals(TipoUsuario.ADMINISTRADOR)) {
				return "/pages/adm/mainAdm.xhtml?faces-redirect=true";
			}
			if (usuarioSite.getTipoUsuario().equals(TipoUsuario.USER)) {
				return "/pages/user/mainUser.xhtml?faces-redirect=true";
			}
			if (usuarioSite.getTipoUsuario().equals(TipoUsuario.USERSITE)) {
				return "/pages/site/main.xhtml?faces-redirect=true";
			} 
		}
		return null;
	}
	
	public String logOff() throws IOException {
		usuarioSite = null;
		SessionContext.getInstance().encerrarSessao();
		return "/index.xhtml?faces-redirect=true";
	}

	public void gerarRelatorio(){
		
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
		Root<Usuario> root = query.from(Usuario.class);
		
		Predicate where1 = null;
		if (getUsuario().getTipoUsuario() != null) {
			where1 = cb.equal(root.get("tipoUsuario"), getUsuarioSel().getTipoUsuario());
		}
		if (where1 != null)	{ 
			query.where(where1);
		}
		
		List<Usuario> listRelatorios = new ArrayList<Usuario>();
		listRelatorios = JpaUtil.getManager().createQuery(query).getResultList();
		
		if (listRelatorios.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
							"Não há usuários com parametros passados"));
		} else {
			Report relatorio = new Report(0);
			try {
				relatorio.getArquivoRelatorio(new DadosFactory().createDataSourceUsuario(listRelatorios));
			} catch (FileNotFoundException e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao gerar relatório " + e.getMessage()));
			} catch (JRException e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao gerar relatório " + e.getMessage()));
			}
		}
	}
	
	public void alterar(String idUsuario) {
		try {
			Usuario userInt = usuarioControler.getById(Integer.valueOf(idUsuario));
			if (usuarioControler.update(userInt)) {
				usuarioSite = userInt;
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao editar usuário " + e.getMessage()));
		}
	}
}
