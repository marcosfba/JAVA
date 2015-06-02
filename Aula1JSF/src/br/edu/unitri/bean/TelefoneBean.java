package br.edu.unitri.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.edu.unitri.model.Telefone;

/**
 * @author marcos.fernando
 *
 */

@ManagedBean
@RequestScoped
public class TelefoneBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Telefone telefone = new Telefone();

	public TelefoneBean() {
		super();
	}

	public TelefoneBean(Telefone telefone) {
		super();
		this.telefone = telefone;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}
	
	public String validaTelefone() {
		
		try {
			if ( getTelefone().valid(getTelefone().getNumTelefone())) 
			 { getTelefone().setResultado("true"); }
			else { getTelefone().setResultado("false"); }
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Operação realizada com sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Erro de execução na operação " +e.getMessage()));
		}
		return null;
	}
	
	public void limpar(){
		telefone = new Telefone();
	}


}
