/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import br.edu.unitri.model.PedidoDTO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * @author Marcos
 *
 */
public class PedidoDTOJRDataSource implements JRDataSource {
	
	private Iterator<PedidoDTO> iterador;
	private PedidoDTO pedidoDTO;
	
	public PedidoDTOJRDataSource() {
		super();
	}
	
	public PedidoDTOJRDataSource(List<PedidoDTO> pedidoDTOs) {
		super();
		iterador = pedidoDTOs.iterator();
	}	

	@Override
	public boolean next() throws JRException {
		boolean ok = iterador.hasNext();
		if (ok) {
			pedidoDTO = iterador.next();
		}
		return ok;
	}
	
	@Override
	public Object getFieldValue(JRField field) throws JRException {
		PedidoDTO cl = pedidoDTO;
		if (field.getName().equals("codPedido")) {
			return cl.getCodPedido();
		}
		if (field.getName().equals("dtPedido")) {
			return cl.getDtPedido();
		}
		if (field.getName().equals("nomeCliente")) {
			return cl.getNomeCliente();
		}
		if (field.getName().equals("codCliente")) {
			return cl.getCodCliente();
		}
		if (field.getName().equals("qtdItens")) {
			return cl.getQtdItens();
		}
		if (field.getName().equals("vlrPedido")) {
			return cl.getVlrPedido();
		}
		return null;
	}

}
