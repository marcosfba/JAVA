/**
 * 
 */
package br.edu.unitri.bean;

import java.io.FileNotFoundException;
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

import net.sf.jasperreports.engine.JRException;
import br.edu.unitri.controler.ContatoControler;
import br.edu.unitri.controler.EnderecoControler;
import br.edu.unitri.controler.FornecedorControler;
import br.edu.unitri.controler.TelefoneControler;
import br.edu.unitri.dataSource.DadosFactory;
import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Contato;
import br.edu.unitri.model.Endereco;
import br.edu.unitri.model.Fornecedor;
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
public class FornecedorBean implements Serializable, Operacoes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FornecedorControler fornecedorControler = new FornecedorControler();
	private TelefoneControler telefoneControler = new TelefoneControler();
	private EnderecoControler enderecoControler = new EnderecoControler();
	private ContatoControler contatoControler = new ContatoControler();

	private Fornecedor fornecedor = new Fornecedor();
	private Fornecedor fornecedorSel;
	private List<Fornecedor> listaFornecedores = new ArrayList<Fornecedor>();

	private Telefone telefone = new Telefone();
	private Endereco endereco = new Endereco();
	private Contato contato = new Contato();
	private Usuario usuario = new Usuario();

	private List<Telefone> telefones = new ArrayList<Telefone>();
	private List<Endereco> enderecos = new ArrayList<Endereco>();
	private List<Contato> contatos = new ArrayList<Contato>();

	public FornecedorBean() {
		super();
	}

	@PostConstruct
	public void init() {
		listarTodos();
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Fornecedor getFornecedorSel() {
		return fornecedorSel;
	}

	public void setFornecedorSel(Fornecedor fornecedorSel) {
		this.fornecedorSel = fornecedorSel;
	}

	public List<Fornecedor> getListaFornecedores() {
		return listaFornecedores;
	}

	public void setListaFornecedores(List<Fornecedor> listaFornecedores) {
		this.listaFornecedores = listaFornecedores;
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
			listaFornecedores = fornecedorControler.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage("mesFornecedor",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Fornecedores " + e.getMessage()));
		}
	}

	@Override
	public void excluir() {
		if (getFornecedorSel() != null) {
			try {
				fornecedorControler.delete(getFornecedorSel());
				FacesContext.getCurrentInstance().addMessage("mesFornecedor",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Informação", "Fornecedor excluido com sucesso "));
				listarTodos();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("mesFornecedor",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir Fornecedor "
										+ e.getMessage()));
			}
		}
	}

	@Override
	public void editar() {

		enderecos.clear();
		contatos.clear();
		telefones.clear();
		
		setFornecedor(getFornecedorSel());
		
		for (Endereco enderecoInt : getFornecedor().getListaEnderecos()) {
			enderecos.add(enderecoInt);
		}
		for (Contato contatoInt : getFornecedor().getListaContatos()) {
			contatos.add(contatoInt);
		}
		for (Telefone telefoneInt : getFornecedor().getListaTelefones()) {
			telefones.add(telefoneInt);
		}
	}

	@Override
	public void limpar() {
		fornecedor = new Fornecedor();
		limparObjetos();
		listarTodos();
	}

	@Override
	public void alterar() {
		try {
			if (fornecedorControler.update(getFornecedor())) {
				
				Pessoa pessoa = getFornecedor();				
				for (int i = 0; i < enderecos.size(); i++) {
					if (enderecos.get(i).getPessoa() != null) {
						enderecoControler.update(enderecos.get(i));
					} else {
						enderecos.get(i).setPessoa(pessoa);
						enderecoControler.save(enderecos.get(i));
					}
				}				
				for (int i = 0; i < telefones.size(); i++) {
					if (telefones.get(i).getPessoa() != null) {
						telefoneControler.update(telefones.get(i));
					} else {
						telefones.get(i).setPessoa(pessoa);
						telefoneControler.save(telefones.get(i));
					}
				}				
				for (int i = 0; i < contatos.size(); i++) {
					if (contatos.get(i).getPessoa() != null) {
						contatoControler.update(contatos.get(i));
					} else {
						contatos.get(i).setPessoa(pessoa);
						contatoControler.save(contatos.get(i));
					}
				}
				FacesContext.getCurrentInstance().addMessage("mesFornecedor",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
				limparObjetos();
				limpar();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("mesFornecedor",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao editar fornecedor " + e.getMessage()));
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
			   FacesContext.getCurrentInstance().addMessage("mesFornecedor",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",
								"Favor preencha o usuario do fornecedor"));
			}
			else {
				getFornecedor().setUsuario(getUsuario());	
				
				Fornecedor fornecedorInt = new Fornecedor();
				fornecedorInt = fornecedorControler.save(getFornecedor());
				
				Pessoa pessoa = fornecedorInt;  
	
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
				ok = fornecedorControler.update(fornecedorInt);
			    if (ok) {
				   FacesContext.getCurrentInstance().addMessage("mesFornecedor",
							new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
									"Operação realizada com sucesso"));
				   limparObjetos();
				   limpar();
				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("mesFornecedor",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao incluir Fornecedor " + e.getMessage()));
		}
	}

	@Override
	public void buscar() {

		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Fornecedor> query = cb.createQuery(Fornecedor.class);
		Root<Fornecedor> root = query.from(Fornecedor.class);
		EntityType<Pessoa> typeFornecedor = JpaUtil.getManager().getMetamodel().entity(Pessoa.class);
		
		Predicate where1 = null, where2 = null, where3 = null, where4 = null;
		if (getFornecedor().getId() != null) {
			where1 = cb.equal(root.get("id"), getFornecedor().getId());
		}
		if (getFornecedor().getNome() != null) {
			where2 = cb.like(root.get(typeFornecedor.getDeclaredSingularAttribute("nome", String.class)),
					getFornecedor().getNome());
		}
		if (getFornecedor().getTipoPessoa() != null) {
			where3 = cb.equal(root.get("tipoPessoa"), getFornecedor().getTipoPessoa());
		}
		if (getFornecedor().getCnpj() != null) {
			where4 = cb.equal(root.get("cnpj"), getFornecedor().getCnpj());
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
			listaFornecedores.clear();
			listaFornecedores = JpaUtil.getManager().createQuery(query).getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar fornecedor " + e.getMessage()));
		} finally {
			if (listaFornecedores.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
								"Não há fornecedores a serem exibidas com argumentos passados"));
			}
		}
	}

	public void gerarRelatorio(){
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Fornecedor> query = cb.createQuery(Fornecedor.class);
		Root<Fornecedor> root = query.from(Fornecedor.class);
		
		Predicate where1 = null;
		if (getFornecedor().getTipoPessoa() != null) {
			if (!getFornecedor().getTipoPessoa().isEmpty()){
				where1 = cb.equal(root.get("tipoPessoa"), getFornecedor().getTipoPessoa());
			}
		}
		if (where1 != null)	{ 
			query.where(where1);
		}
		
		List<Fornecedor> listRelatorios = JpaUtil.getManager().createQuery(query).getResultList();
		
		if (listRelatorios.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
							"Não há fornecedores com parametros passados"));
		} else {
			Report relatorio = new Report(2);
			try {
				relatorio.getArquivoRelatorio(new DadosFactory().createDataSourceFornecedor(listRelatorios));
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
}
