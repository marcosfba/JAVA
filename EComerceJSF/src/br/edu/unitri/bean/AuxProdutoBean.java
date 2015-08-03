/**
 * 
 */
package br.edu.unitri.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import br.edu.unitri.controler.ClienteControler;
import br.edu.unitri.controler.ContatoControler;
import br.edu.unitri.controler.EnderecoControler;
import br.edu.unitri.controler.ItensOperacaoControler;
import br.edu.unitri.controler.OperacaoControler;
import br.edu.unitri.controler.ProdutoControler;
import br.edu.unitri.controler.TelefoneControler;
import br.edu.unitri.dto.ProdutoDTO;
import br.edu.unitri.enumerators.TipoFoto;
import br.edu.unitri.enumerators.TipoOperacao;
import br.edu.unitri.enumerators.TipoStatus;
import br.edu.unitri.enumerators.TipoUsuario;
import br.edu.unitri.model.Cliente;
import br.edu.unitri.model.Contato;
import br.edu.unitri.model.Endereco;
import br.edu.unitri.model.Foto;
import br.edu.unitri.model.ItensOperacao;
import br.edu.unitri.model.Operacao;
import br.edu.unitri.model.Pessoa;
import br.edu.unitri.model.Produto;
import br.edu.unitri.model.Telefone;
import br.edu.unitri.model.Usuario;
import br.edu.unitri.util.JpaUtil;
import br.edu.unitri.util.Path;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class AuxProdutoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ProdutoDTO produtoDTO;
	private Produto produto = new Produto();
	
	private Cliente cliente = new Cliente();
	private Endereco endereco = new Endereco();
	private Contato contato = new Contato();
	private Telefone telefone = new Telefone();
	private Usuario usuarioPag = new Usuario();
	private ItensOperacao itemSel;
	private Operacao pedido = new Operacao();

	private List<Foto> listaFotos = new ArrayList<Foto>();
	private List<ProdutoDTO> listProdutosFotos = new ArrayList<ProdutoDTO>();
	private ProdutoControler produtoControler = new ProdutoControler();
	private String diretorio = Path.PATH_FOTOS;
	private List<ItensOperacao> listProdutoCarrinho = new ArrayList<ItensOperacao>();
	private OperacaoControler operacaoControler = new OperacaoControler();
	
	@ManagedProperty("#{usuarioBean}")
	private UsuarioBean usuarioBean;


	public AuxProdutoBean() {
		super();
	}

	@PostConstruct
	public void init() {
		listarTodos();
	}

	public ProdutoDTO getProdutoDTO() {
		return produtoDTO;
	}

	public void setProdutoDTO(ProdutoDTO produtoDTO) {
		this.produtoDTO = produtoDTO;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Foto> getListaFotos() {
		return listaFotos;
	}

	public void setListaFotos(List<Foto> listaFotos) {
		this.listaFotos = listaFotos;
	}

	public List<ProdutoDTO> getListProdutosFotos() {
		return listProdutosFotos;
	}

	public void setListProdutosFotos(List<ProdutoDTO> listProdutosFotos) {
		this.listProdutosFotos = listProdutosFotos;
	}

	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

	public List<ItensOperacao> getListProdutoCarrinho() {
		return listProdutoCarrinho;
	}

	public void setListProdutoCarrinho(List<ItensOperacao> listProdutoCarrinho) {
		this.listProdutoCarrinho = listProdutoCarrinho;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public ItensOperacao getItemSel() {
		return itemSel;
	}

	public void setItemSel(ItensOperacao itemSel) {
		this.itemSel = itemSel;
	}

	public Usuario getUsuarioPag() {
		return usuarioPag;
	}

	public void setUsuarioPag(Usuario usuarioPag) {
		this.usuarioPag = usuarioPag;
	}

	public Operacao getPedido() {
		return pedido;
	}

	public void setPedido(Operacao pedido) {
		this.pedido = pedido;
	}

	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	public void listarTodos() {
		try {
			listProdutosFotos = produtoControler.findProdutoFotos();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage("mesCarrinho",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Produtos " + e.getMessage()));
		}
	}

	public void adicionaProdutoCarrinho(String id) {
		Produto prodCarrinho = null;
		try {
			prodCarrinho = produtoControler.getById(Integer.valueOf(id));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boolean ok = false;
		if (prodCarrinho != null) {
			
			ItensOperacao item = new ItensOperacao();
			item.setProduto(prodCarrinho);
			item.setQuantidade(1);
			item.setVlrdesc(BigDecimal.ZERO);
			item.setVlrItem(prodCarrinho.getVlrVenda());
			
			
			if (!listProdutoCarrinho.contains(item)) {
				listProdutoCarrinho.add(item);
				ok = true;
			} else {
				for (ItensOperacao itensOperacao : listProdutoCarrinho) {
					if (itensOperacao.getProduto().equals(item.getProduto())) {
						itensOperacao.setQuantidade(itensOperacao.getQuantidade() + item.getQuantidade());
						ok = true;
						break;
					}
				}
			}
		}
		if (ok) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
							"Operação realizada com sucesso"));
		}
	}

	public void visualizaGaleria(String id) {
		Produto prodCarrinho = null;
		try {
			prodCarrinho = produtoControler.getById(Integer.valueOf(id));
			listaFotos.clear();
			listaFotos = produtoControler.findAllFotos(prodCarrinho);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void buscarProdutos() {
		
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<ProdutoDTO> query = cb.createQuery(ProdutoDTO.class);
		Root<Produto> root = query.from(Produto.class);
		EntityType<Produto> typeProduto = JpaUtil.getManager().getMetamodel().entity(Produto.class);		
		Join<Produto,Foto> joinFoto = root.<Produto,Foto>join("listaFotos");

		Predicate where1 = null, where2 = null, where3 = null, where4 = null, where5 = null ;
		
		if (getProduto().getCodBarras() != null) {
			where1 = cb.equal(root.get("codBarras"), getProduto()
					.getCodBarras());
		}
		if (getProduto().getDescricao() != null) {
			where2 = cb.like(root.get(typeProduto.getDeclaredSingularAttribute(
					"descricao", String.class)), getProduto().getDescricao());
		}
		if (getProduto().getCategoria() != null) {
			where3 = cb.equal(root.get("categoria"), getProduto()
					.getCategoria());
		}
		if (getProduto().getMarca() != null) {
			where4 = cb.equal(root.get("marca"), getProduto().getMarca());
		}
		where5 = cb.equal(joinFoto.get("tipoFoto"),TipoFoto.PRINCIPAL);
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		if (where1 != null) { predicados.add(where1); }
		if (where2 != null) { predicados.add(where2); }
		if (where3 != null) { predicados.add(where3); }
		if (where4 != null) { predicados.add(where4); }
		predicados.add(where5);
		
		if (predicados.size() > 0) {
			query.where(cb.or(predicados.toArray(new Predicate[] {})));
		}
		
		query.multiselect(root.get("id").alias("idProduto"),root.get("codigo").alias("codigo"),
				          root.get("codBarras").alias("codBarras"),root.get("descricao").alias("descricao"), 
				          root.get("unidade").alias("unidade"), root.get("complemento").alias("complemento"),
				          root.get("categoria").alias("categoria"), root.get("marca").alias("marca"), 
				          root.get("vlrCusto").alias("vlrCusto"),root.get("vlrVenda").alias("vlrVenda"), 
				          root.get("qtdEstoque").alias("qtdEstoque"), root.get("docFabricante").alias("docFabricante"),
				          joinFoto.get("nomeImagem").alias("nomeImagem"),
				          joinFoto.get("extensao").alias("extensao"), 
				          joinFoto.get("fotoOriginal").alias("fotoOriginal"),
				          joinFoto.get("fotoTumb").alias("fotoTumb"));
		query.distinct(true);

		try {
			listProdutosFotos.clear();
			listProdutosFotos = JpaUtil.getManager().createQuery(query).getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("mesCarrinho",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar produtos tente novamente mais tarde " + e.getMessage()));
		} finally {
			if (listProdutosFotos.size() == 0) {
				FacesContext.getCurrentInstance().addMessage("mesCarrinho",
								new FacesMessage(FacesMessage.SEVERITY_WARN,
										"WARNING",
										"Não há produtos a serem exibidos com argumentos passados"));
			}
		}
	}

	public void removerProduto() {

		for (int i = 0; i < listProdutoCarrinho.size(); i++) {
			ItensOperacao itensOperacao = listProdutoCarrinho.get(i);
			if (itensOperacao.equals(getItemSel())) {
				listProdutoCarrinho.remove(i);
			    break;
			}
		}
		FacesContext.getCurrentInstance().addMessage("mesCarrinho",
				new FacesMessage(FacesMessage.SEVERITY_INFO,
						"INFO","Operação realizada com sucesso"));
	}
	
	public void limpar() {
		produto = new Produto();
	}
	
	public void salvarCliente(){
		
		ClienteControler clControler = new ClienteControler();
		TelefoneControler teControler = new TelefoneControler();
		ContatoControler ctControler = new ContatoControler();
		EnderecoControler endControler = new EnderecoControler();
		
		try {
			getUsuarioPag().setTipoUsuario(TipoUsuario.USERSITE);
			
			getCliente().setUsuario(getUsuarioPag());
			clControler.save(getCliente());
			
			Pessoa pessoa = getCliente();
			
			getEndereco().setPessoa(pessoa);
			getContato().setPessoa(pessoa);
			getTelefone().setPessoa(pessoa);
			
			teControler.save(getTelefone());
			ctControler.save(getContato());
			endControler.save(getEndereco());
			
			usuarioBean.auxListBean.loginUser(getCliente().getUsuario());
			
		    FacesContext.getCurrentInstance().addMessage("mesCliente",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
					"Operação realizada com sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("mesCliente",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao incluir Cliente " + e.getMessage()));
		}
	}
	
	public String visualizarUsuario(String idUsuario) {
		return "/pages/user/userUsuario.xhtml?faces-redirect=true";
	}

	public String visualizarContatosUsuario(String idUsuario) {
		return "/pages/user/userContato.xhtml?faces-redirect=true";
	}

	public String visualizarTelefonesUsuario(String idUsuario) {
		return "/pages/user/userTelefone.xhtml?faces-redirect=true";
	}

	public String visualizarEnderecosUsuario(String idUsuario) {
		return "/pages/user/userEndereco.xhtml?faces-redirect=true";
	}

	public String visualizarCadastroUsuario(String idUsuario) {
		return "/pages/user/userCliente.xhtml?faces-redirect=true";
	}

	public String visualizarPedidosUsuario(String idUsuario) {
		return "/pages/user/userPedido.xhtml?faces-redirect=true";
	}

	public String salvarPedido() {
		
		try {
			setCliente(new ClienteControler().getbyUser(usuarioBean.getUsuarioSite()));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		boolean ok = false;
		try {
			getPedido().setDtOperacao(new Date());
			getPedido().setPessoa(getCliente());
			getPedido().setTipoStatus(TipoStatus.ABERTO);
			getPedido().setTipoOperacao(TipoOperacao.VENDAS);
			
			if (operacaoControler.save(getPedido()) != null){
				
				BigDecimal vrlPedido = BigDecimal.ZERO;
				double vrlSoma = 0D;
				ItensOperacaoControler itensControler = new ItensOperacaoControler();
				for (ItensOperacao itensOperacao : listProdutoCarrinho) {
					itensOperacao.setOperacao(getPedido());
					itensControler.save(itensOperacao);

					vrlSoma = vrlSoma +((itensOperacao.getVlrItem().doubleValue() *  itensOperacao.getQuantidade()) - 
				             itensOperacao.getVlrdesc().doubleValue());
					vrlPedido = vrlPedido.add(new BigDecimal(vrlSoma));
				}
				
				getPedido().setVlrPedido(vrlPedido);
				operacaoControler.update(getPedido());
			    pedido = new Operacao();
			    listProdutoCarrinho.clear();
			    ok = true;			    
			}
		} catch (Exception e) {
		    FacesContext.getCurrentInstance().addMessage("mesPedido",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO",
								"Erro ao salvar pedido" + e.getMessage()));
		}
		
		if (ok) {
			usuarioBean.auxListBean.loginUser(getCliente().getUsuario());
			return "/pages/user/userPedido.xhtml?faces-redirect=true";
		} else {
			return null;
		}
	}
}
