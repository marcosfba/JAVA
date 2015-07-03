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
import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Marca;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class MarcaBean implements Serializable, Operacoes {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Marca marca = new Marca();
	private Marca marcaSel;
	private List<Marca> listaMarca = new ArrayList<Marca>();
	private MarcaControler mdr = new MarcaControler();
	
	public MarcaBean() {
		super();
	}
	
	@PostConstruct
	public void init() {
		try {
			listaMarca = mdr.findAll();
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar marcas " + e.getMessage()));
		}
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Marca getMarcaSel() {
		return marcaSel;
	}

	public void setMarcaSel(Marca marcaSel) {
		this.marcaSel = marcaSel;
	}

	public List<Marca> getListaMarca() {
		return listaMarca;
	}

	public void setListaMarca(List<Marca> listaMarca) {
		this.listaMarca = listaMarca;
	}

	@Override
	public void salvar() {
		try {
			if (mdr.save(getMarca()) != null) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao incluir marca " + e.getMessage()));
		}
	}

	@Override
	public void excluir() {
		if (getMarcaSel()!=null) {
			try {
				mdr.delete(getMarcaSel());
				buscar();
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação",
								"Marca excluido com sucesso "));
				
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir marca " + e.getMessage()));
			}
		}
	}

	@Override
	public void editar() {
		this.marca = getMarcaSel();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("editMarca.xhtml");
		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao tentar editar marca " + e.getMessage()));
		}
	}
	
	@Override
	public void limpar() {
		marca = new Marca();		
	}

	@Override
	public void buscar() {
		String sql = "select m.* from tbMarca m ";
		String param ="";
		if (!getMarca().getDescricao().isEmpty()) {
			param = " m.descricao like  '%"+ getMarca().getDescricao() + "%'";
		}
		
		if (!param.isEmpty()) {
			param = " where " + param;
		}
		
		try {
			listaMarca.clear();
			listaMarca = mdr.findAll(sql,param);
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar marcas " + e.getMessage()));
		}
	}

	@Override
	public void alterar() {
		try {
			if (mdr.update(getMarca())) {
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
