/**
 * 
 */
package br.edu.unitri.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.edu.unitri.model.Calculator;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@RequestScoped
public class CalculatorBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Calculator calculator = new Calculator();
	private String operacao;
	private Double resultado;

	public CalculatorBean() {
		super();
	}

	public CalculatorBean(Calculator calculator, String operacao, Double resultado) {
		super();
		this.calculator = calculator;
		this.operacao = operacao;
		this.resultado = resultado;
	}

	public Calculator getCalculator() {
		return calculator;
	}

	public void setCalculator(Calculator calculator) {
		this.calculator = calculator;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public Double getResultado() {
		return resultado;
	}

	public void setResultado(Double resultado) {
		this.resultado = resultado;
	}
	
	private void execute(int operacao) {
		try {
			calculator.setOperacao(operacao);
			resultado = calculator.executar();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO", "Operação realizada com sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO", "Erro de execução na operação " +e.getMessage()));
		}
	}

	public String somar() {
		execute(1);		
		return null;
	}

	public String subtrair() {
		execute(2);		
		return null;
	}

	public String multiplicar() {
		execute(3);		
		return null;
	}

	public String dividir() {
		execute(4);		
		return null;
	}
	
	public void limpar(){
	   calculator = new Calculator();	
	   new CalculatorBean(calculator,"",0D);
	}
}
