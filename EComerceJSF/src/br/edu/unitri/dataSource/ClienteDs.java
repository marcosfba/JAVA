/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.edu.unitri.model.Cliente;

/**
 * @author Marcos
 *
 */
public class ClienteDs implements JRDataSource {
	
	private Iterator<Cliente> iterador;
	private Cliente cliente;
	
	public ClienteDs() {
		super();
	}
	
	public ClienteDs(List<Cliente> listaClientes) {
		super();
		iterador = listaClientes.iterator();
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
		Cliente clientInt = cliente;
		if (field.getName().equals("nome")) {
			return  clientInt.getNome();
		}
		if (field.getName().equals("tipoPessoa")) {
			return  clientInt.getTipoPessoa();
		}
		if (field.getName().equals("cpf")) {
			return clientInt.getCpf();
		}
		if (field.getName().equals("cnh")) {
			return clientInt.getCnh();
		}
		if (field.getName().equals("identidade")) {
			return clientInt.getIdentidade();
		}
		if (field.getName().equals("dtNascimento")) {
			return clientInt.getDtNascimento();
		}
		if (field.getName().equals("titEleitor")) {
			return clientInt.getTitEleitor();
		}
		return null;
	}

}
