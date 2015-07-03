/**
 * 
 */
package br.edu.unitri.bean;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.unitri.controler.MarcaControler;
import br.edu.unitri.controler.ProdutoControler;
import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Marca;
import br.edu.unitri.model.Produto;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class ProdutoBean implements Serializable, Operacoes {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Produto produto = new Produto();
	private Produto prodSel;
	private List<Produto> listaProdutos = new ArrayList<Produto>();
	private List<Produto> listaProdutosSel = new ArrayList<Produto>();
	private List<Marca> listaMarcas = new ArrayList<Marca>();
	private ProdutoControler pdr = new ProdutoControler();
	private MarcaControler mdr = new MarcaControler();

	public ProdutoBean() {
		super();
	}
	
	@PostConstruct
	public void init(){
		try {
			listaProdutos = pdr.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar produtos " + e.getMessage()));
		}
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}
	
	public Produto getProdSel() {
		return prodSel;
	}

	public void setProdSel(Produto prodSel) {
		this.prodSel = prodSel;
	}
	
	public List<Marca> getListaMarcas() {
		try {
			listaMarcas = mdr.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar marcas " + e.getMessage()));
		}
		return listaMarcas;
	}

	public void setListaMarcas(List<Marca> listaMarcas) {
		this.listaMarcas = listaMarcas;
	}

	public List<Produto> getListaProdutosSel() {
		return listaProdutosSel;
	}

	public void setListaProdutosSel(List<Produto> listaProdutosSel) {
		this.listaProdutosSel = listaProdutosSel;
	}

	public void visualizarSelecao(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("listProdutoSel.xhtml");
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao tentar editar produto " + e.getMessage()));
		}
	}

	public void selecionar(){
		if (getProdSel()!=null) {
			try {
				if (!listaProdutosSel.contains(getProdSel())) {
					listaProdutosSel.add(getProdSel());
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação",
									"Produto adicionado com sucesso "));
				} else {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
									"Produto ja adicionado anteriormente"));
				}
				
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir produto " + e.getMessage()));
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
							"Nenhum produto foi selecionado"));
		}
	}

	@Override
	public void salvar() {
		try {
			if (pdr.save(getProduto()) != null) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao incluir produto " + e.getMessage()));
		}
	}

	@Override
	public void buscar() {
		String sql = "select p.* from tbProduto p ";
		String param ="";
		if (!getProduto().getDescricao().isEmpty()) {
			param = " p.descricao like  '%"+ getProduto().getDescricao() + "%'";
		}
		if (!getProduto().getCodigo().isEmpty()) {
			if (param.isEmpty()) {
				param = " p.codigo = '"+ getProduto().getCodigo() +"'";
			} else {
				param += " and p.codigo = '"+ getProduto().getCodigo() +"'";
			}
		}
		
		if (!param.isEmpty()) {
			param = " where " + param;
		}
		
		try {
			listaProdutos.clear();
			listaProdutos = pdr.findAll(sql,param);
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar produtos " + e.getMessage()));
		}
	}

	@Override
	public void excluir() {
		if (getProdSel()!=null) {
			try {
				pdr.delete(getProdSel());
				buscar();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação",
								"Produto excluido com sucesso "));
				
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir produto " + e.getMessage()));
			}
		}
	}

	@Override
	public void editar() {
		this.produto = getProdSel();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("editproduto.xhtml");
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao tentar editar produto " + e.getMessage()));
		}
	}

	@Override
	public void limpar() {
		produto = new Produto();

	}

	@Override
	public void alterar() {
		try {
			if (pdr.update(getProduto())) {
				limpar();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao editar produto " + e.getMessage()));
		}
		
	}

}
