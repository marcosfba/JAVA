/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;

/**
 * @author marcos.fernando
 *
 */
public class Login implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nomeUsuario;
	private String senhaUsuario;
	private String pg;
	private final static String user = "MARCOS";
	private final static String pass = "123456";

	public Login() {
		super();
	}

	public Login(String nomeUsuario, String senhaUsuario) {
		super();
		this.nomeUsuario = nomeUsuario;
		this.senhaUsuario = senhaUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}
	
	public String getPg() {
		return pg;
	}

	public void setPg(String pg) {
		this.pg = pg;
	}

	public boolean validLogin(){
		return (getNomeUsuario().equals(user) && getSenhaUsuario().equals(pass));
	}

}
