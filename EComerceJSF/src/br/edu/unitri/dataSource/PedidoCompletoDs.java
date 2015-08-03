/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.edu.unitri.dto.PedidoComplDTO;

/**
 * @author Marcos
 *
 */
public class PedidoCompletoDs implements JRDataSource {
	
	private Iterator<PedidoComplDTO> iterador;
	private PedidoComplDTO pedidoComplDTO;
	
	public PedidoCompletoDs() {
		super();
	}
	
	public PedidoCompletoDs(List<PedidoComplDTO> listaPedidoCompleto) {
		super();
		iterador = listaPedidoCompleto.iterator();
	}

	@Override
	public boolean next() throws JRException {
		boolean ok = iterador.hasNext();
		if (ok) {
			pedidoComplDTO = iterador.next();
		}
		return ok;
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		PedidoComplDTO  pedInt = pedidoComplDTO;
		
		if (field.getName().equals("idPedido")) {
			return  pedInt.getIdPedido();
		}
		if (field.getName().equals("nomeCliente")) {
			return  pedInt.getNomeCliente();
		}
		if (field.getName().equals("cpfCliente")) {
			return  pedInt.getCpfCliente();
		}
		if (field.getName().equals("identidade")) {
			return  pedInt.getIdentidade();
		}
		if (field.getName().equals("tipoPessoa")) {
			return  pedInt.getTipoPessoa();
		}
		if (field.getName().equals("tipoEndereco")) {
			return  pedInt.getTipoEndereco();
		}
		if (field.getName().equals("endereco")) {
			return  pedInt.getEndereco();
		}
		if (field.getName().equals("numero")) {
			return  pedInt.getNumero();
		}
		if (field.getName().equals("bairro")) {
			return  pedInt.getBairro();
		}
		if (field.getName().equals("cidade")) {
			return  pedInt.getCidade();
		}
		if (field.getName().equals("estado")) {
			return  pedInt.getEstado();
		}
		if (field.getName().equals("tipoContato")) {
			return  pedInt.getTipoContato();
		}
		if (field.getName().equals("emailContato")) {
			return  pedInt.getEmailContato();
		}
		if (field.getName().equals("tipoTelefone")) {
			return  pedInt.getTipoTelefone();
		}
		if (field.getName().equals("numTelefone")) {
			return  pedInt.getNumTelefone();
		}
		if (field.getName().equals("dtPedido")) {
			return  pedInt.getDtPedido();
		}
		if (field.getName().equals("tipoOperacao")) {
			return  pedInt.getTipoOperacao();
		}
		if (field.getName().equals("tipoPagamento")) {
			return  pedInt.getTipoPagamento();
		}
		if (field.getName().equals("tipoStatus")) {
			return  pedInt.getTipoStatus();
		}
		if (field.getName().equals("vlrPedido")) {
			return  pedInt.getVlrPedido();
		}
		if (field.getName().equals("codProduto")) {
			return  pedInt.getCodProduto();
		}
		if (field.getName().equals("codBarras")) {
			return  pedInt.getCodBarras();
		}
		if (field.getName().equals("descProduto")) {
			return  pedInt.getDescProduto();
		}
		if (field.getName().equals("unidadeProd")) {
			return  pedInt.getUnidadeProd();
		}
		if (field.getName().equals("quantidade")) {
			return  pedInt.getQuantidade();
		}
		if (field.getName().equals("vlrItem")) {
			return  pedInt.getVlrItem();
		}
		if (field.getName().equals("vlrDesconto")) {
			return  pedInt.getVlrDesconto();
		}

		if (field.getName().equals("nomeMarca")) {
			return  pedInt.getNomeMarca();
		}
		if (field.getName().equals("nomeCategoria")) {
			return  pedInt.getNomeCategoria();
		}
		return null;
	}

}
