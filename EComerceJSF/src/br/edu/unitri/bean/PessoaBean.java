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

import br.edu.unitri.controler.ContatoControler;
import br.edu.unitri.controler.EnderecoControler;
import br.edu.unitri.controler.PessoaControler;
import br.edu.unitri.controler.TelefoneControler;
import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Contato;
import br.edu.unitri.model.Endereco;
import br.edu.unitri.model.Pessoa;
import br.edu.unitri.model.Telefone;
import br.edu.unitri.model.Usuario;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class PessoaBean implements Serializable, Operacoes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PessoaControler pessoaControler = new PessoaControler();
	private TelefoneControler telefoneControler = new TelefoneControler();
	private EnderecoControler enderecoControler = new EnderecoControler();
	private ContatoControler contatoControler = new ContatoControler();

	private Pessoa pessoa = new Pessoa();
	private Pessoa pessoaSel;
	private List<Pessoa> listaPessoa = new ArrayList<Pessoa>();

	private Telefone telefone = new Telefone();
	private Endereco endereco = new Endereco();
	private Contato contato = new Contato();
	private Usuario usuario = new Usuario();

	private List<Telefone> telefones = new ArrayList<Telefone>();
	private List<Endereco> enderecos = new ArrayList<Endereco>();
	private List<Contato> contatos = new ArrayList<Contato>();

	public PessoaBean() {
		super();
	}

	@PostConstruct
	public void init() {
		listarTodos();
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Pessoa getPessoaSel() {
		return pessoaSel;
	}

	public void setPessoaSel(Pessoa pessoaSel) {
		this.pessoaSel = pessoaSel;
	}

	public List<Pessoa> getListaPessoa() {
		return listaPessoa;
	}

	public void setListaPessoa(List<Pessoa> listaPessoa) {
		this.listaPessoa = listaPessoa;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}

	public void listarTodos() {
		try {
			listaPessoa = pessoaControler.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage("mesPessoa",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Pessoas " + e.getMessage()));
		}
	}

	@Override
	public void excluir() {
		if (getPessoaSel() != null) {
			try {
				pessoaControler.delete(getPessoaSel());
				FacesContext.getCurrentInstance().addMessage("mesPessoa",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Informação", "Pessoa excluido com sucesso "));
				listarTodos();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("mesPessoa",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir Pessoa "
										+ e.getMessage()));
			}
		}
	}

	@Override
	public void editar() {

		enderecos.clear();
		contatos.clear();
		telefones.clear();
		
		setPessoa(getPessoaSel());
		
		for (Endereco enderecoInt : getPessoa().getListaEnderecos()) {
			enderecos.add(enderecoInt);
		}
		for (Contato contatoInt : getPessoa().getListaContatos()) {
			contatos.add(contatoInt);
		}
		for (Telefone telefoneInt : getPessoa().getListaTelefones()) {
			telefones.add(telefoneInt);
		}
	}

	@Override
	public void limpar() {
		pessoa = new Pessoa();
		limparObjetos();
		listarTodos();
	}

	@Override
	public void alterar() {
		try {
			if (pessoaControler.update(getPessoa())) {
				
				for (int i = 0; i < enderecos.size(); i++) {
					if (enderecos.get(i).getPessoa() != null) {
						enderecoControler.update(enderecos.get(i));
					} else {
						enderecos.get(i).setPessoa(getPessoa());
						enderecoControler.save(enderecos.get(i));
					}
				}				
				for (int i = 0; i < telefones.size(); i++) {
					if (telefones.get(i).getPessoa() != null) {
						telefoneControler.update(telefones.get(i));
					} else {
						telefones.get(i).setPessoa(getPessoa());
						telefoneControler.save(telefones.get(i));
					}
				}				
				for (int i = 0; i < contatos.size(); i++) {
					if (contatos.get(i).getPessoa() != null) {
						contatoControler.update(contatos.get(i));
					} else {
						contatos.get(i).setPessoa(getPessoa());
						contatoControler.save(contatos.get(i));
					}
				}
				FacesContext.getCurrentInstance().addMessage("mesPessoa",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
				limparObjetos();
				limpar();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("mesPessoa",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao editar pessoa " + e.getMessage()));
		}
	}

	public void adicionarTelefone(){
		boolean ok = false;
		if (telefones.size() == 0) { ok = true; }
		else {
			ok = true;
			for (Telefone tel : telefones) {
				if (tel.getTipoTelefone().equals(telefone.getTipoTelefone())) {
					ok = false;
					break;
				}
			}
		}
		if (ok) {
			telefones.add(telefone);
			telefone = new Telefone();
			FacesContext.getCurrentInstance().addMessage("mesTelefone",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
							"Telefone adicionado com sucesso"));
		} else {
			FacesContext.getCurrentInstance().addMessage("mesTelefone",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",
							"Não pode ser adicionado telefones do mesmo tipo"));

		}
	}

	public void adicionarEndereco() {
		boolean ok = false;
		if (enderecos.size() == 0) { ok = true; }
		else {
			ok = true;
			for (Endereco adress : enderecos) {
				if (adress.getTipoEndereco().equals(endereco.getTipoEndereco())) {
					ok = false;
					break;
				}
			}
		}
		if (ok) {
			enderecos.add(endereco);
			endereco = new Endereco();
			FacesContext.getCurrentInstance().addMessage("mesEndereco",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
							"Endereço adicionado com sucesso"));
		} else {
			FacesContext.getCurrentInstance().addMessage("mesEndereco",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",
							"Não pode ser adicionado endereços do mesmo tipo"));

		}
	}

	public void adicionarContato() {
		boolean ok = false;
		if (contatos.size() == 0) { ok = true; }
		else {
			ok = true;
			for (Contato contact : contatos) {
				if (contact.getTipoContato().equals(contato.getTipoContato())) {
					ok = false;
					break;
				}
			}
		}
		if (ok) {
			contatos.add(contato);
			contato = new Contato();
			FacesContext.getCurrentInstance().addMessage("mesContato",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
							"Contato adicionado com sucesso"));
		} else {
			FacesContext.getCurrentInstance().addMessage("mesContato",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",
							"Não pode ser adicionado contatos do mesmo tipo"));

		}
	}
	
	public void limparTelefone(){
		telefone = new Telefone();
	}
	
	public void limparContato(){
		contato = new Contato();
	}
	
	public void limparEndereco(){
		endereco = new Endereco();
	}
	
	public void adicionarUsuario(){
		setUsuario(getUsuario());
	}
	
	private void limparObjetos(){
		limparEndereco();
		limparContato();
		limparTelefone();
		usuario = new Usuario();
		enderecos.clear();
		contatos.clear();
		telefones.clear();
	}

	@Override
	public void salvar() {
		try {
			boolean ok = false;
			ok = getUsuario() == null;
			if (ok) {
			   FacesContext.getCurrentInstance().addMessage("mesPessoa",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",
								"Favor preencha o usuario da Pessoa"));
			}
			else {
				getPessoa().setUsuario(getUsuario());					
				pessoaControler.save(getPessoa());
	
				for (int i = 0; i < enderecos.size(); i++) {
					enderecos.get(i).setPessoa(pessoa);
					enderecoControler.save(enderecos.get(i));
				}
				
				for (int i = 0; i < telefones.size(); i++) {
					telefones.get(i).setPessoa(pessoa);
					telefoneControler.save(telefones.get(i));
				}
				
				for (int i = 0; i < contatos.size(); i++) {
					contatos.get(i).setPessoa(pessoa);
					contatoControler.save(contatos.get(i));
				}
				
				ok = pessoaControler.update(getPessoa());
			    if (ok) {
				   FacesContext.getCurrentInstance().addMessage("mesPessoa",
							new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
									"Operação realizada com sucesso"));
				   limparObjetos();
				   limpar();
				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("mesPessoa",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao incluir Cliente " + e.getMessage()));
		}
	}

	@Override
	public void buscar() {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Pessoa> query = cb.createQuery(Pessoa.class);
		Root<Pessoa> root = query.from(Pessoa.class);
		EntityType<Pessoa> typePessoa = JpaUtil.getManager().getMetamodel().entity(Pessoa.class);
		
		Predicate where1 = null, where2 = null, where3 = null;
		if (getPessoa().getId() != null) {
			where1 = cb.equal(root.get("id"), getPessoa().getId());
		}
		if (getPessoa().getNome() != null) {
			where2 = cb.like(root.get(typePessoa.getDeclaredSingularAttribute("nome", String.class)),
					getPessoa().getNome());
		}
		if (getPessoa().getTipoPessoa() != null) {
			where3 = cb.equal(root.get("tipoPessoa"), getPessoa().getTipoPessoa());
		}
		List<Predicate> predicados = new ArrayList<Predicate>(); 
		if (where1 != null) { predicados.add(where1) ; }
		if (where2 != null) { predicados.add(where2) ; }
		if (where3 != null) { predicados.add(where3) ; }
		
		if (predicados.size() > 0) {
			query.where(cb.or(predicados.toArray(new Predicate[]{})));
		}

		try {
			listaPessoa.clear();
			listaPessoa = JpaUtil.getManager().createQuery(query).getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar pessoa " + e.getMessage()));
		} finally {
			if (listaPessoa.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
								"Não há pessoas a serem exibidas com argumentos passados"));
			}
		}
	}

}
