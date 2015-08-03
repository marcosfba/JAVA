/**
 * 
 */
package br.edu.unitri.bean;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.model.chart.PieChartModel;

import br.edu.unitri.controler.ItensOperacaoControler;
import br.edu.unitri.controler.OperacaoControler;
import br.edu.unitri.controler.PessoaControler;
import br.edu.unitri.controler.ProdutoControler;
import br.edu.unitri.dataSource.DadosFactory;
import br.edu.unitri.dto.GraficoClienteDTO;
import br.edu.unitri.dto.PedidoComplDTO;
import br.edu.unitri.dto.PedidoDTO;
import br.edu.unitri.enumerators.TipoContato;
import br.edu.unitri.enumerators.TipoEndereco;
import br.edu.unitri.enumerators.TipoTelefone;
import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Categoria;
import br.edu.unitri.model.Contato;
import br.edu.unitri.model.Endereco;
import br.edu.unitri.model.ItensOperacao;
import br.edu.unitri.model.Marca;
import br.edu.unitri.model.Operacao;
import br.edu.unitri.model.Pessoa;
import br.edu.unitri.model.Produto;
import br.edu.unitri.model.Telefone;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class PedidoBean implements Serializable, Operacoes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Operacao operacao = new Operacao();
	private Operacao operacaoSel;
	
	private ItensOperacao item = new ItensOperacao();
	private ItensOperacao itemSel;
	private String tipoGrafico;

	private OperacaoControler operacaoControler = new OperacaoControler();
	private PessoaControler pessoaControler = new PessoaControler();
	private ProdutoControler produtoControler = new ProdutoControler();
	private ItensOperacaoControler itensOperacaoControler = new ItensOperacaoControler();

	private List<ItensOperacao> listaItens = new ArrayList<ItensOperacao>();
	private List<Operacao> listOperacoes = getListaOperacoes();
	private List<Pessoa> listPessoas = getListaPessoas();
	private List<Produto> listProdutos = getListaProdutos();
	
	@ManagedProperty("#{usuarioBean}")
	public UsuarioBean usuarioBean;

	private PieChartModel grafico;
	private List<GraficoClienteDTO> listGrafico = new ArrayList<GraficoClienteDTO>();

	public PedidoBean() {
		super();
	}
	
	@PostConstruct
	public void init() {
		listarTodos();
		grafico = new PieChartModel();
	}

	public Operacao getOperacao() {
		return operacao;
	}

	public void setOperacao(Operacao operacao) {
		this.operacao = operacao;
	}

	public Operacao getOperacaoSel() {
		return operacaoSel;
	}

	public void setOperacaoSel(Operacao operacaoSel) {
		this.operacaoSel = operacaoSel;
	}

	public List<Operacao> getListOperacoes() {
		return listOperacoes;
	}

	public void setListOperacoes(List<Operacao> listOperacoes) {
		this.listOperacoes = listOperacoes;
	}

	public List<Pessoa> getListPessoas() {
		return listPessoas;
	}

	public void setListPessoas(List<Pessoa> listPessoas) {
		this.listPessoas = listPessoas;
	}

	public List<ItensOperacao> getListaItens() {
		return listaItens;
	}

	public void setListaItens(List<ItensOperacao> listaItens) {
		this.listaItens = listaItens;
	}

	public List<Produto> getListProdutos() {
		return listProdutos;
	}

	public void setListProdutos(List<Produto> listProdutos) {
		this.listProdutos = listProdutos;
	}

	public ItensOperacao getItem() {
		return item;
	}

	public void setItem(ItensOperacao item) {
		this.item = item;
	}

	public ItensOperacao getItemSel() {
		return itemSel;
	}

	public void setItemSel(ItensOperacao itemSel) {
		this.itemSel = itemSel;
	}

	public String getTipoGrafico() {
		return tipoGrafico;
	}

	public void setTipoGrafico(String tipoGrafico) {
		this.tipoGrafico = tipoGrafico;
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

	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}

	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}

	private List<Pessoa> getListaPessoas() {
		List<Pessoa> listaInt = new ArrayList<Pessoa>();
		try {
			listaInt = pessoaControler.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage("mesPedido",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Pessoas " + e.getMessage()));
		}
		return listaInt;
	}

	private List<Operacao> getListaOperacoes() {
		List<Operacao> listaInt = new ArrayList<Operacao>();
		try {
			listaInt = operacaoControler.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage("mesPedido",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Operações " + e.getMessage()));
		}
		return listaInt;
	}
	
	private List<Produto> getListaProdutos() {
		List<Produto> listaInt = new ArrayList<Produto>();
		try {
			listaInt = produtoControler.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage("mesPedido",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Produtos " + e.getMessage()));
		}
		return listaInt;
	}

	public void listarTodos(){
		try {
			listOperacoes = getListaOperacoes();
			listPessoas = getListaPessoas();
			listProdutos = getListaProdutos();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("mesPedido",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar operações " + e.getMessage()));
		}
	}
	
	public void adicionarItem(){
		
		if (listaItens.contains(item)) {
			for (int i = 0; i < listaItens.size(); i++) {
				if (listaItens.get(i).getProduto().equals(item.getProduto())){
					listaItens.get(i).setQuantidade(listaItens.get(i).getQuantidade()+ item.getQuantidade());
					listaItens.get(i).setVlrItem(new BigDecimal(listaItens.get(i).getVlrItem().doubleValue()+ item.getVlrItem().doubleValue()));
					listaItens.get(i).setVlrdesc(new BigDecimal(listaItens.get(i).getVlrdesc().doubleValue() + item.getVlrdesc().doubleValue()));
				}
			}				
		} else {
			listaItens.add(item);
		}
		limparItem();
		FacesContext.getCurrentInstance().addMessage("mesItens",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
							"Operação realizada com sucesso"));

	}
	
	public void limparItem(){
		item = new ItensOperacao();
	}
	
	public void excluirItem(){
		try {
		  if (itensOperacaoControler.delete(getItemSel())) {
				FacesContext.getCurrentInstance().addMessage("mesItenPedido",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
		  }
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("mesItenPedido",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO",
							"Erro ao exlcuir item " +  e.getMessage()));
		}
	}
	
	@Override
	public void limpar() {
		operacao = new Operacao();
		listaItens.clear();
		listarTodos();
	}

	@Override
	public void salvar() {
		try {
			if (operacaoControler.save(getOperacao()) != null) {
				for (int i = 0; i < listaItens.size(); i++) {
					if (listaItens.get(i).getOperacao() == null) {
						listaItens.get(i).setOperacao(getOperacao());					
					}
				}	
				BigDecimal vrlPedido = BigDecimal.ZERO;
				double vrlSoma = 0D;
				for (int i = 0; i < listaItens.size(); i++) {
					itensOperacaoControler.save(listaItens.get(i));
					vrlSoma = vrlSoma +((listaItens.get(i).getVlrItem().doubleValue() * listaItens.get(i).getQuantidade()) - 
							            listaItens.get(i).getVlrdesc().doubleValue());
					vrlPedido = vrlPedido.add(new BigDecimal(vrlSoma));
				}
				getOperacao().setVlrPedido(vrlPedido);
				operacaoControler.update(getOperacao());
				FacesContext.getCurrentInstance().addMessage("mesPedido",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
			   limpar();
			   listarTodos();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("mesPedido",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao incluir Operacao " + e.getMessage()));
		}
	}

	@Override
	public void excluir() {
		if (getOperacaoSel()!=null) {
			try {
				operacaoControler.delete(getOperacaoSel());
				FacesContext.getCurrentInstance().addMessage("mesPedido",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação",
								"Operacao excluido com sucesso "));
				listarTodos();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("mesPedido",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir Operacao " + e.getMessage()));
			}
		}
	}

	@Override
	public void editar() {
		listaItens.clear();
		setOperacao(getOperacaoSel());
		
		for (ItensOperacao itensInt : getOperacao().getItens()) {
			listaItens.add(itensInt);
		}
	}

	@Override
	public void alterar() {
		try {
			if (operacaoControler.update(getOperacao())) {
				
				BigDecimal vrlPedido = BigDecimal.ZERO;
				double vrlSoma = 0D;
				for (int i = 0; i < listaItens.size(); i++) {
					if (listaItens.get(i).getOperacao() != null) {
						itensOperacaoControler.update(listaItens.get(i));
					} else {
						listaItens.get(i).setOperacao(getOperacao());
						itensOperacaoControler.save(listaItens.get(i));
					}
					vrlSoma = vrlSoma +((listaItens.get(i).getVlrItem().doubleValue() * listaItens.get(i).getQuantidade()) - 
				            listaItens.get(i).getVlrdesc().doubleValue());
					vrlPedido = vrlPedido.add(new BigDecimal(vrlSoma));
				}	
				getOperacao().setVlrPedido(vrlPedido);
				operacaoControler.update(getOperacao());
				
				FacesContext.getCurrentInstance().addMessage("mesPedido",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
				limpar();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("mesPedido",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao editar operacao " + e.getMessage()));
		}
	}
	
	@Override
	public void buscar() {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Operacao> query = cb.createQuery(Operacao.class);
		Root<Operacao> root = query.from(Operacao.class);
		
		Predicate where1 = null, where2 = null, where3 = null, where4 = null, where5 = null, where6 = null;
		if (getOperacao().getId() != null) {
			where1 = cb.equal(root.get("id"), getOperacao().getId());
		}
		if (getOperacao().getDtOperacao() != null) {
			where2 = cb.equal(root.get("dtOperacao"), getOperacao().getDtOperacao());
		}
		if (getOperacao().getPessoa() != null) {
			where3 = cb.equal(root.get("pessoa"), getOperacao().getPessoa());
		}
		if (getOperacao().getTipoOperacao() != null) {
			where4 = cb.equal(root.get("tipoOperacao"), getOperacao().getTipoOperacao());
		}
		if (getOperacao().getTipoStatus() != null) {
			where5 = cb.equal(root.get("tipoStatus"), getOperacao().getTipoStatus());
		}
		if (getOperacao().getTipoPagamento() != null) {
			where6 = cb.equal(root.get("tipoPagamento"), getOperacao().getTipoPagamento());
		}
		List<Predicate> predicados = new ArrayList<Predicate>(); 
		if (where1 != null) { predicados.add(where1) ; }
		if (where2 != null) { predicados.add(where2) ; }
		if (where3 != null) { predicados.add(where3) ; }
		if (where4 != null) { predicados.add(where4) ; }
		if (where5 != null) { predicados.add(where5) ; }
		if (where6 != null) { predicados.add(where6) ; }
		
		if (predicados.size() > 0) {
			query.where(cb.or(predicados.toArray(new Predicate[]{})));
		}

		try {
			listOperacoes.clear();
			listOperacoes = JpaUtil.getManager().createQuery(query).getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar operações " + e.getMessage()));
		} finally {
			if (listOperacoes.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
								"Não há operações a serem exibidas com argumentos passados"));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public void gerarRelatorio(){
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<PedidoDTO> query = cb.createQuery(PedidoDTO.class);
		
		Root<Operacao> root = query.from(Operacao.class);
		Join<Operacao, Pessoa> join = root.join("pessoa", JoinType.INNER);
		
		Predicate where1 = null, where2 = null, where3 = null, where4 = null;
		
		if (getOperacao().getPessoa() != null) {
			where1 = cb.equal(root.get("pessoa"), getOperacao().getPessoa());
		}
		if (getOperacao().getTipoOperacao() != null) {
			where2 = cb.equal(root.get("tipoOperacao"), getOperacao().getTipoOperacao());
		}
		if (getOperacao().getTipoStatus() != null) {
			where3 = cb.equal(root.get("tipoStatus"), getOperacao().getTipoStatus());
		}
		if (getOperacao().getTipoPagamento() != null) {
			where4 = cb.equal(root.get("tipoPagamento"), getOperacao().getTipoPagamento());
		}

		List<Predicate> predicados = new ArrayList<Predicate>(); 
		if (where1 != null) { predicados.add(where1) ; }
		if (where2 != null) { predicados.add(where2) ; }
		if (where3 != null) { predicados.add(where3) ; }
		if (where4 != null) { predicados.add(where4) ; }
		
		query.multiselect(join.get("id").alias("idPessoa"), root.get("id").alias("idOperacao"),
				          root.get("dtOperacao").alias("dtOperacao"), join.get("nome").alias("nomePessoa"),
				          root.get("tipoOperacao").alias("tipoOperacao"), root.get("tipoPagamento").alias("tipoPagamento"),
				          root.get("tipoStatus").alias("tipoStatus"), 
				          cb.coalesce(root.get("vlrPedido"),BigDecimal.ZERO).alias("vlrPedido"), 
				          cb.size(root.<Collection>get("itens")).alias("qtdItens"));
		
		if (predicados.size() > 0) {
			query.where(cb.or(predicados.toArray(new Predicate[]{})));
		}
		
		query.groupBy(join.get("id"),root.get("id"));
		query.orderBy(cb.asc(join.get("id")));
		query.distinct(true);
		
		List<PedidoDTO> listRelatorio = new ArrayList<PedidoDTO>();
		try {
			listRelatorio = JpaUtil.getManager().createQuery(query).getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",	
							"Erro critéria query " + e.getMessage()));
		}

		if (listOperacoes.size() == 0) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
							"Não há operações a serem exibidas com argumentos passados"));
		} else {
			Report relatorio = new Report(3);
			try {
				relatorio.getArquivoRelatorio(new DadosFactory().createDataSourceOperacoes(listRelatorio));
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
	
	@SuppressWarnings("unchecked")	
	public void gerarGraficos(){
		
		listGrafico.clear();
		String sql ="";
		if (getTipoGrafico().equals("0")) {
			//Quantidade de operações por mês
			sql = "select count(*) as qtd, month(p.dtoperacao) as mes, 'Mes' as descricao"
					+ " from tbOperacao p group by month(p.dtoperacao)";
		} 
		else if (getTipoGrafico().equals("1")) {
			//Quantidade de operações por ano
			sql = "select count(*) as qtd, year(p.dtoperacao) as mes, 'Ano' as descricao"
					+ " from tbOperacao p group by year(p.dtoperacao)";
		}

		List<Object[]> lista = JpaUtil.getManager().createNativeQuery(sql).getResultList();			
		for (Object[] objects : lista) {	
			int qtd = Integer.valueOf(objects[0].toString());
			String descricao = objects[1].toString() + " " + objects[2].toString();
			GraficoClienteDTO graficoClienteDTO = new GraficoClienteDTO(qtd, descricao);
			listGrafico.add(graficoClienteDTO);
		}
		
		if (listGrafico.size() > 0) {
			grafico = new PieChartModel();
			for (GraficoClienteDTO graficoClienteDTO : listGrafico) {
				grafico.set(graficoClienteDTO.getDescricao(), graficoClienteDTO.getQuantidade());
			}
	         
	        if (getTipoGrafico().equals("0")){
	        	grafico.setTitle("Quantidade de operações por mês");
	        } else if (getTipoGrafico().equals("1")) {
	        	grafico.setTitle("Quantidade de operações por ano");
	        }
	        grafico.setLegendPosition("e");
	        grafico.setFill(false);
	        grafico.setShowDataLabels(true);
		}
		
	}

	public void buscar(String idUsuario) {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Operacao> query = cb.createQuery(Operacao.class);
		Root<Operacao> root = query.from(Operacao.class);
		
		Predicate where1 = null, where2 = null, where3 = null;
		Predicate where4 = null, where5 = null, where6 = null;
		Predicate whereP = cb.equal(root.get("pessoa"), usuarioBean.auxListBean.getCliente());
		
		if (getOperacao().getId() != null) {
			where1 = cb.equal(root.get("id"), getOperacao().getId());
		}
		if (getOperacao().getDtOperacao() != null) {
			where2 = cb.equal(root.get("dtOperacao"), getOperacao().getDtOperacao());
		}
		if (getOperacao().getPessoa() != null) {
			where3 = cb.equal(root.get("pessoa"), getOperacao().getPessoa());
		}
		if (getOperacao().getTipoOperacao() != null) {
			where4 = cb.equal(root.get("tipoOperacao"), getOperacao().getTipoOperacao());
		}
		if (getOperacao().getTipoStatus() != null) {
			where5 = cb.equal(root.get("tipoStatus"), getOperacao().getTipoStatus());
		}
		if (getOperacao().getTipoPagamento() != null) {
			where6 = cb.equal(root.get("tipoPagamento"), getOperacao().getTipoPagamento());
		}
		List<Predicate> predicados = new ArrayList<Predicate>();
		predicados.add(whereP);
		if (where1 != null) { predicados.add(where1) ; }
		if (where2 != null) { predicados.add(where2) ; }
		if (where3 != null) { predicados.add(where3) ; }
		if (where4 != null) { predicados.add(where4) ; }
		if (where5 != null) { predicados.add(where5) ; }
		if (where6 != null) { predicados.add(where6) ; }
		
		if (predicados.size() > 0) {
			query.where(cb.or(predicados.toArray(new Predicate[]{})));
		}

		try {
			usuarioBean.auxListBean.getPedidos().clear();
			usuarioBean.auxListBean.setPedidos(JpaUtil.getManager().createQuery(query).getResultList());
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar operações " + e.getMessage()));
		} finally {
			if (listOperacoes.size() == 0) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
								"Não há operações a serem exibidas com argumentos passados"));
			}
		}
	}

	public void imprimirPedido(){
		
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<PedidoComplDTO> query = cb.createQuery(PedidoComplDTO.class);
		
		Root<Operacao> root = query.from(Operacao.class);
		Join<Operacao,Pessoa> joinPessoa = root.join("pessoa", JoinType.LEFT);
		
		Join<Pessoa,Endereco> joinEndereco = joinPessoa.<Pessoa,Endereco>join("listaEnderecos");
		Join<Pessoa,Telefone> joinTelefone = joinPessoa.<Pessoa,Telefone>join("listaTelefones");
		Join<Pessoa,Contato> joinContato = joinPessoa.<Pessoa,Contato>join("listaContatos");
		
		Join<Operacao,ItensOperacao> joinItens = root.<Operacao,ItensOperacao>join("itens");
		Join<ItensOperacao,Produto> joinProd =  joinItens.join("produto", JoinType.LEFT);
		Join<Produto,Categoria> joinCateg = joinProd.join("categoria", JoinType.LEFT);
		Join<Produto,Marca> joinMarca = joinProd.join("marca", JoinType.LEFT);
		
		Expression<String> cpf = cb.literal(usuarioBean.auxListBean.getCliente().getCpf());
		Expression<String> identidade = cb.literal(usuarioBean.auxListBean.getCliente().getIdentidade());
		
		Predicate where1= null, where2 = null, where3 = null, where4 = null;
		where1 = cb.equal(joinEndereco.get("tipoEndereco"),TipoEndereco.RESIDENCIAL);
		where2 = cb.equal(joinContato.get("tipoContato"),TipoContato.EMAIL_PRINCIPAL);
		where3 = cb.equal(joinTelefone.get("tipoTelefone"), TipoTelefone.RESIDENCIAL);
		where4 = cb.equal(root.get("id"), getOperacaoSel().getId());
		
		List<Predicate> predicados = new ArrayList<Predicate>();
		predicados.add(where1);
		predicados.add(where2);
		predicados.add(where3);
		predicados.add(where4);
		
		query.multiselect(root.get("id").alias("idPedido"), joinPessoa.get("nome").alias("nomeCliente"), cpf, identidade, joinPessoa.get("tipoPessoa").alias("tipoPessoa"),joinEndereco.get("tipoEndereco").alias("tipoEndereco"),
				joinEndereco.get("logradouro").alias("endereco"),joinEndereco.get("numero").alias("numero"),joinEndereco.get("bairro").alias("bairro"),
				joinEndereco.get("cidade").alias("cidade"),joinEndereco.get("estado").alias("estado"), joinContato.get("tipoContato").alias("tipoContato"),
				joinContato.get("email").alias("emailContato"), joinTelefone.get("tipoTelefone").alias("tipoTelefone"), joinTelefone.get("numeroTelefone").alias("numTelefone"),
				root.get("dtOperacao").alias("dtPedido"), root.get("tipoOperacao").alias("tipoOperacao"),root.get("tipoPagamento").alias("tipoPagamento"),
				root.get("tipoStatus").alias("tipoStatus"),root.get("vlrPedido").alias("vlrPedido"),joinProd.get("codigo").alias("codProduto"),
				joinProd.get("codBarras").alias("codBarras"),joinProd.get("descricao").alias("descProduto"),joinProd.get("unidade").alias("unidadeProd"),
				joinItens.get("quantidade").alias("quantidade"),joinItens.get("vlrItem").alias("vlrItem"),joinItens.get("vlrdesc").alias("vlrDesconto"),
				joinMarca.get("descricao").alias("nomeMarca"), joinCateg.get("descricao").alias("nomeCategoria"));
		
		query.where(cb.and(predicados.toArray(new Predicate[]{})));
		query.distinct(true);
		
		List<PedidoComplDTO> pedidoComplDTOs = new ArrayList<PedidoComplDTO>();
		pedidoComplDTOs.clear();
		pedidoComplDTOs = JpaUtil.getManager().createQuery(query).getResultList();
		
		Report relatorio = new Report(7);
		try {
			relatorio.getArquivoRelatorio(new DadosFactory().createDataSourcePedidoCompleto(pedidoComplDTOs));
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
