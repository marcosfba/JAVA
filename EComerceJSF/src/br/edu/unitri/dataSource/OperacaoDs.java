/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import br.edu.unitri.dto.PedidoDTO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * @author Marcos
 *
 */
public class OperacaoDs implements JRDataSource {
	
	private Iterator<PedidoDTO> iterador;
	private PedidoDTO operacao;
	
	public OperacaoDs() {
		super();
	}
	
	public OperacaoDs(List<PedidoDTO> listaOperacoes) {
		super();
		iterador = listaOperacoes.iterator();
	}

	@Override
	public boolean next() throws JRException {
		boolean ok = iterador.hasNext();
		if (ok) {
			operacao = iterador.next();
		}
		return ok;
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		PedidoDTO operInt = operacao;

		if (field.getName().equals("idPessoa")) {
			return operInt.getIdPessoa();
		}
		if (field.getName().equals("idOperacao")) {
			return operInt.getIdOperacao();	
		}
		if (field.getName().equals("dtOperacao")) {
			return operInt.getDtOperacao();	
		}
		if (field.getName().equals("nomePessoa")) {
			return operInt.getNomePessoa();	
		}
		if (field.getName().equals("tipoOperacao")) {
			return operInt.getTipoOperacao();
		}
		if (field.getName().equals("tipoPagamento")) {
			return operInt.getTipoPagamento();
		}
		if (field.getName().equals("tipoStatus")) {
			return operInt.getTipoStatus();
		}
		if (field.getName().equals("vlrPedido")) {
			return operInt.getVlrPedido();
		}
		if (field.getName().equals("qtdItens")) {
			return operInt.getQtdItens();
		}
		return null;
	}

	
	

}
