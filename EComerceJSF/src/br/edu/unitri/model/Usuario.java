/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.edu.unitri.enumerators.TipoUsuario;

/**
 * @author MARCOS FERNANDO
 *
 */
@Entity
@Table(name = "tbUsuario")
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USUARIO", length = 175, nullable = true)
	private String userName;

	@Column(name = "SENHA", length = 60, nullable = true)
	private String password;

	@Column(name = "EMAIL", length = 175, nullable = true)
	private String email;

	@Column(name = "TPUSUARIO")
	@Enumerated(EnumType.STRING)
	private TipoUsuario tipoUsuario;

	public Usuario() {
		super();
	}

	public Usuario(Long id, String userName, String password, String email,
			TipoUsuario tipoUsuario) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.tipoUsuario = tipoUsuario;
	}

	public Usuario(String userName, String password, String email,
			TipoUsuario tipoUsuario) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.tipoUsuario = tipoUsuario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", userName=" + userName + ", password="
				+ password + ", email=" + email + ", tipoUsuario="
				+ tipoUsuario + "]";
	}
	
	

}
