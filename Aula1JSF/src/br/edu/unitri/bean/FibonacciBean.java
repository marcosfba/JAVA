package br.edu.unitri.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.edu.unitri.model.Fibonacci;

/**
 * @author marcos.fernando
 *
 */

@ManagedBean
@RequestScoped
public class FibonacciBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Fibonacci fibonacci = new Fibonacci();

	public FibonacciBean() {
		super();
	}

	public FibonacciBean(Fibonacci fibonacci) {
		super();
		this.fibonacci = fibonacci;
	}
	
	public String executar() {
		try {
			getFibonacci().calcularFibonacci();		
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Operação realizada com sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Erro de execução na operação " +e.getMessage()));
		}
		return null;
	}
	
	public void limpar(){
		fibonacci = new Fibonacci();
	}

	public Fibonacci getFibonacci() {
		return fibonacci;
	}

	public void setFibonacci(Fibonacci fibonacci) {
		this.fibonacci = fibonacci;
	}
	
}
