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
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.model.chart.PieChartModel;

import br.edu.unitri.controler.ClienteControler;
import br.edu.unitri.controler.ContatoControler;
import br.edu.unitri.controler.EnderecoControler;
import br.edu.unitri.controler.TelefoneControler;
import br.edu.unitri.dataSource.DadosFactory;
import br.edu.unitri.dto.GraficoClienteDTO;
import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Cliente;
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
public class ClienteBean implements Serializable, Operacoes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClienteControler clienteControler = new ClienteControler();
	private TelefoneControler telefoneControler = new TelefoneControler();
	private EnderecoControler enderecoControler = new EnderecoControler();
	private ContatoControler contatoControler = new ContatoControler();

	private Cliente cliente = new Cliente();
	private Cliente clienteSel;
	private String tipoGrafico;
	private List<Cliente> listaCliente = new ArrayList<Cliente>();

	private Telefone telefone = new Telefone();
	private Endereco endereco = new Endereco();
	private Contato contato = new Contato();
	private Usuario usuario = new Usuario();

	private List<Telefone> telefones = new ArrayList<Telefone>();
	private List<Endereco> enderecos = new ArrayList<Endereco>();
	private List<Contato> contatos = new ArrayList<Contato>();
	
	@ManagedProperty("#{usuarioBean}")
	public UsuarioBean usuarioBean;
	
	private List<GraficoClienteDTO> listGrafico = new ArrayList<GraficoClienteDTO>();
	private PieChartModel grafico;


	public ClienteBean() {
		super();
	}

	@PostConstruct
	public void init() {
		listarTodos();
		grafico = new PieChartModel();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getClienteSel() {
		return clienteSel;
	}

	public void setClienteSel(Cliente clienteSel) {
		this.clienteSel = clienteSel;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public PieChartModel getGrafico() {
		return grafico;
	}

	public void setGrafico(PieChartModel grafico) {
		this.grafico = grafico;
	}

	public List<GraficoClienteDTO> getListGrafico() {
		return listGrafico;
	}

	public void setListGrafico(List<GraficoClienteDTO> listGrafico) {
		this.listGrafico = listGrafico;
	}

	public String getTipoGrafico() {
		return tipoGrafico;
	}

	public void setTipoGrafico(String tipoGrafico) {
		this.tipoGrafico = tipoGrafico;
	}

	public void listarTodos() {
		try {
			listaCliente = clienteControler.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage("mesCliente",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Clientes " + e.getMessage()));
		}
	}

	@Override
	public void excluir() {
		if (getClienteSel() != null) {
			try {
				clienteControler.delete(getClienteSel());
				FacesContext.getCurrentInstance().addMessage("mesCliente",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Informação", "Cliente excluido com sucesso "));
				listarTodos();
				limparObjetos();
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage("mesCliente",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir Cliente "
										+ e.getMessage()));
			}
		}
	}

	@Override
	public void editar() {
		
		enderecos.clear();
		contatos.clear();
		telefones.clear();
		
		setCliente(getClienteSel());
		
		for (Endereco enderecoInt : getCliente().getListaEnderecos()) {
			enderecos.add(enderecoInt);
		}
		for (Contato contatoInt : getCliente().getListaContatos()) {
			contatos.add(contatoInt);
		}
		for (Telefone telefoneInt : getCliente().getListaTelefones()) {
			telefones.add(telefoneInt);
		}
	}

	@Override
	public void limpar() {
		cliente = new Cliente();
		limparObjetos();
		listarTodos();
	}
	
	@Override
	public void alterar() {
		try {
			if (clienteControler.update(getCliente())) {
				
				Pessoa pessoa = getCliente();				
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
				FacesContext.getCurrentInstance().addMessage("mesCliente",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
				limparObjetos();
				limpar();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("mesCliente",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao editar cliente " + e.getMessage()));
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
			   FacesContext.getCurrentInstance().addMessage("mesCliente",
						new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",
								"Favor preencha o usuario do cliente"));
			}
			else {
				getCliente().setUsuario(getUsuario());	
				Cliente clienteInt = new Cliente();
				clienteInt = clienteControler.save(getCliente());
				
				Pessoa pessoa = clienteInt;  
	
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
				
				ok = clienteControler.update(clienteInt);
			    if (ok) {
				   FacesContext.getCurrentInstance().addMessage("mesCliente",
							new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
									"Operação realizada com sucesso"));
				   limparObjetos();
				   limpar();
				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("mesCliente",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao incluir Cliente " + e.getMessage()));
		}
	}

	@Override
	public void buscar() {

		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);
		Root<Cliente> root = query.from(Cliente.class);
		EntityType<Pessoa> typeCliente = JpaUtil.getManager().getMetamodel().entity(Pessoa.class);
		
		Predicate where1 = null, where2 = null, where3 = null, where4 = null, where5 = null;
		if (getCliente().getId() != null) {
			where1 = cb.equal(root.get("id"), getCliente().getId());
		}
		if (getCliente().getNome() != null) {
			where2 = cb.like(root.get(typeCliente.getDeclaredSingularAttribute("nome", String.class)),
					getCliente().getNome());
		}
		if (getCliente().getTipoPessoa() != null) {
			where3 = cb.equal(root.get("tipoPessoa"), getCliente().getTipoPessoa());
		}
		if (getCliente().getDtNascimento() != null) {
			where4 = cb.equal(root.get("dtNascimento"), getCliente().getDtNascimento());
		}
		if (getCliente().getCpf() != null) {
			where5 = cb.equal(root.get("cpf"), getCliente().getCpf());
		}
		List<Predicate> predicados = new ArrayList<Predicate>(); 
		if (where1 != null) { predicados.add(where1) ; }
		if (where2 != null) { predicados.add(where2) ; }
		if (where3 != null) { predicados.add(where3) ; }
		if (where4 != null) { predicados.add(where4) ; }
		if (where5 != null) { predicados.add(where5) ; }
		
		if (predicados.size() > 0) {
			query.where(cb.or(predicados.toArray(new Predicate[]{})));
		}

		try {
			listaCliente.clear();
			listaCliente = JpaUtil.getManager().createQuery(query).getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar clientes " + e.getMessage()));
		} finally {
			if (listaCliente.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
								"Não há clientes a serem exibidas com argumentos passados"));
			}
		}
	}

	public void gerarRelatorio(){
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);
		Root<Cliente> root = query.from(Cliente.class);
		
		Predicate where1 = null;
		if (getCliente().getTipoPessoa() != null) {
			if (!getCliente().getTipoPessoa().isEmpty()){
				where1 = cb.equal(root.get("tipoPessoa"), getCliente().getTipoPessoa());
			}
		}
		if (where1 != null)	{ 
			query.where(where1);
		}
		
		List<Cliente> listRelatorios = JpaUtil.getManager().createQuery(query).getResultList();
		
		if (listRelatorios.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
							"Não há usuários com parametros passados"));
		} else {
			Report relatorio = new Report(1);
			try {
				relatorio.getArquivoRelatorio(new DadosFactory().createDataSourceCliente(listRelatorios));
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
	
	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	public void alterar(String idUsuario){
		try {
			if (clienteControler.update(usuarioBean.auxListBean.getCliente())) {
				usuarioBean.auxListBean.loginUser(getCliente().getUsuario());
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao editar dados do cliente " + e.getMessage()));
		}
	}
	
	@SuppressWarnings("unchecked")
	public void gerarGraficos(){
		
		listGrafico.clear();
		if (getTipoGrafico().equals("0")) {
			//clientes por idade
			String sql = "SELECT count(*) as qtd, year(p.DTNASCIMENTO) as Ano, 'Ano' as Descricao"
					+ " FROM tbPessoa p group by year(p.DTNASCIMENTO)";
			List<Object[]> lista = JpaUtil.getManager().createNativeQuery(sql).getResultList();			
			for (Object[] objects : lista) {	
				int qtd = Integer.valueOf(objects[0].toString());
				String descricao = objects[1].toString() + " " + objects[2].toString();
				GraficoClienteDTO graficoClienteDTO = new GraficoClienteDTO(qtd, descricao);
				listGrafico.add(graficoClienteDTO);
			}
		} //pedidos por cliente 
		
		else if (getTipoGrafico().equals("1")) {
			
			String sql = "select count(p.id) as qtd, p.nome from tbOperacao op"
					+ " join tbPessoa p on p.id = op.idpessoa group by p.id";
			List<Object[]> lista = JpaUtil.getManager().createNativeQuery(sql).getResultList();			
			for (Object[] objects : lista) {	
				int qtd = Integer.valueOf(objects[0].toString());
				String descricao = objects[1].toString();
				GraficoClienteDTO graficoClienteDTO = new GraficoClienteDTO(qtd, descricao);
				listGrafico.add(graficoClienteDTO);
			}
		}
		
		if (listGrafico.size() > 0) {
			grafico = new PieChartModel();
			for (GraficoClienteDTO graficoClienteDTO : listGrafico) {
				grafico.set(graficoClienteDTO.getDescricao(), graficoClienteDTO.getQuantidade());
			}
	         
	        if (getTipoGrafico().equals("0")){
	        	grafico.setTitle("Clientes por idade");
	        } else if (getTipoGrafico().equals("1")) {
	        	grafico.setTitle("Pedidos por cliente");
	        }
	        grafico.setLegendPosition("e");
	        grafico.setFill(false);
	        grafico.setShowDataLabels(true);
		}
	}
	
}
