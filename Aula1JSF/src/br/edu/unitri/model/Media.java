/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;

/**
 * @author marcos.fernando
 *
 */
public class Media implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double valor;
	private int count;

	public Media() {
		super();
	}

	public Media(Double valor, int count) {
		super();
		this.valor = valor;
		this.count = count;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public Double calcularMedia(){
		return  (this.valor / this.count);
	}

}
