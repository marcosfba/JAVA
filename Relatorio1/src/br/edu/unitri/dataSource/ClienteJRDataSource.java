/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import br.edu.unitri.model.Cliente;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * @author Marcos
 *
 */
public class ClienteJRDataSource implements JRDataSource {
	
	private Iterator<Cliente> iterador;
	private Cliente cliente;
	
	public ClienteJRDataSource() {
		super();
	}
	
	public ClienteJRDataSource(List<Cliente> clientes) {
		super();
		iterador = clientes.iterator();
	}	

	@Override
	public boolean next() throws JRException {
		boolean ok = iterador.hasNext();
		if (ok) {
			cliente = iterador.next();
		}
		return ok;
	}
	
	@Override
	public Object getFieldValue(JRField field) throws JRException {
		Cliente cl = cliente;
		if (field.getName().equals("dtNascimento")) {
			return cl.getDtNascimento();
		}
		if (field.getName().equals("email")) {
			return cl.getEmail();
		}
		if (field.getName().equals("sexo")) {
			return cl.getSexo();
		}
		if (field.getName().equals("nome")) {
			return cl.getNome();
		}
		return null;
	}

}
