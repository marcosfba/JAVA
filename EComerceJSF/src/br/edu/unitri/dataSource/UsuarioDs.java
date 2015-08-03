/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.edu.unitri.model.Usuario;

/**
 * @author Marcos
 *
 */
public class UsuarioDs implements JRDataSource {
	
	private Iterator<Usuario> iterador;
	private Usuario usuario;
	
	public UsuarioDs() {
		super();
	}
	
	public UsuarioDs(List<Usuario> listaUsuarios) {
		super();
		iterador = listaUsuarios.iterator();
	}

	@Override
	public boolean next() throws JRException {
		boolean ok = iterador.hasNext();
		if (ok) {
			usuario = iterador.next();
		}
		return ok;
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		Usuario user = usuario;
		if (field.getName().equals("userName")) {
			return user.getUserName();
		}
		if (field.getName().equals("email")) {
			return user.getEmail();
		}
		if (field.getName().equals("tipoUsuario")) {
			return user.getTipoUsuario();
		}
		return null;
	}

	
	

}
