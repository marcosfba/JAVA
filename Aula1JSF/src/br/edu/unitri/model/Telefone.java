package br.edu.unitri.model;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author marcos.fernando
 *
 */
public class Telefone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String numTelefone;
	private String resultado;

	public Telefone() {
		super();
	}

	public Telefone(String numTelefone, String resultado) {
		super();
		this.numTelefone = numTelefone;
		this.resultado = resultado;
	}

	public String getNumTelefone() {
		return numTelefone;
	}

	public void setNumTelefone(String numTelefone) {
		this.numTelefone = numTelefone;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	public boolean valid(String telefone) {
		Pattern pattern = Pattern.compile("[0-9]{4,4}[-][0-9]{4,4}");
		Matcher matcher = pattern.matcher(telefone);
		return matcher.matches();
	}

}
