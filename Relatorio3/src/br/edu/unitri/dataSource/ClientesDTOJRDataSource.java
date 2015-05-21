/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import br.edu.unitri.model.ClientesDTO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * @author Marcos
 *
 */
public class ClientesDTOJRDataSource implements JRDataSource {
	
	private Iterator<ClientesDTO> iterador;
	private ClientesDTO clientesDTO;
	
	public ClientesDTOJRDataSource() {
		super();
	}
	
	public ClientesDTOJRDataSource(List<ClientesDTO> clientesDTO) {
		super();
		iterador = clientesDTO.iterator();
	}	

	@Override
	public boolean next() throws JRException {
		boolean ok = iterador.hasNext();
		if (ok) {
			clientesDTO = iterador.next();
		}
		return ok;
	}
	
	@Override
	public Object getFieldValue(JRField field) throws JRException {
		ClientesDTO cl = clientesDTO;
		if (field.getName().equals("codCliente")) {
			return cl.getCodCliente();
		}
		if (field.getName().equals("nomeCliente")) {
			return cl.getNomeCliente();
		}
		if (field.getName().equals("qtdPedidos")) {
			return cl.getQtdPedidos();
		}
		if (field.getName().equals("ano")) {
			return cl.getAno();
		}
		if (field.getName().equals("vlrTotal")) {
			return cl.getVlrTotal();
		}
		return null;
	}

}
