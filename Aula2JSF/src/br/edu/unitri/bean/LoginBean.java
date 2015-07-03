/**
 * 
 */
package br.edu.unitri.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.unitri.model.Login;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Login login = new Login();

	public LoginBean() {
		super();
	}

	public LoginBean(Login login) {
		super();
		this.login = login;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
	
	public void validaUsuario() {
		try {
			if (getLogin().validLogin()){
				FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
			} else {
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
								"Usuário e senha incorretos"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO",
							"Erro de execução na operação " + e.getMessage()));
		}
	}
}
