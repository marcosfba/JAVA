package br.edu.unitri.model;

import java.io.Serializable;

/**
 * @author marcos.fernando
 *
 */
public class Calculator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double valor1;
	private Double valor2;
	private Double resultado;
	private int operacao;

	public Calculator() {
		super();
	}

	public Calculator(Double valor1, Double valor2, Double resultado, int operacao) {
		super();
		this.valor1 = valor1;
		this.valor2 = valor2;
		this.resultado = resultado;
		this.operacao = operacao;
	}

	public Double getValor1() {
		return valor1;
	}

	public void setValor1(Double valor1) {
		this.valor1 = valor1;
	}

	public Double getValor2() {
		return valor2;
	}

	public void setValor2(Double valor2) {
		this.valor2 = valor2;
	}

	public Double getResultado() {
		return resultado;
	}

	public void setResultado(Double resultado) {
		this.resultado = resultado;
	}

	public int getOperacao() {
		return operacao;
	}

	public void setOperacao(int operacao) {
		this.operacao = operacao;
	}
	
	public Double executar(){
		switch (getOperacao()) {
		case 1:
			setResultado(getValor1()+getValor2());
			break;
		case 2:			
			setResultado(getValor1()-getValor2());
			break;
		case 3:			
			setResultado(getValor1()*getValor2());
			break;
		case 4:
			setResultado(getValor1()/getValor2());
			break;
		}
		
		return getResultado();
	}

}
