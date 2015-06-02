package br.edu.unitri.model;

import java.io.Serializable;

/**
 * @author marcos.fernando
 *
 */
public class Fibonacci implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long numero;
	private long resultado;

	public Fibonacci() {
		super();
	}

	public Fibonacci(long numero, long resultado) {
		super();
		this.numero = numero;
		this.resultado = resultado;
	}

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public void setResultado(long resultado) {
		this.resultado = resultado;
	}
	
	public long getResultado() {
		return resultado;
	}

	static long fibo(long n) {
		int f = 0;
		int ant = 0;
		for (int i=1; i <=n; i++) {
			if (i==1) {
				f = 1;
				ant = 0;
			} else {
				f += ant;
				ant = f-ant;
			}
		}
		return f;
	}

	public void calcularFibonacci() {
		setResultado(fibo(this.numero));
	}
}
