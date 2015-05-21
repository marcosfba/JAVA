/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.edu.unitri.model.Produto;

/**
 * @author Marcos
 *
 */
public class ProdutoJRDataSource implements JRDataSource {

	private Iterator<Produto> iterador;
	private Produto produto;

	public ProdutoJRDataSource() {
		super();
	}

	public ProdutoJRDataSource(List<Produto> produtos) {
		super();
		iterador = produtos.iterator();
	}
	
	@Override
	public boolean next() throws JRException {
		boolean ok = iterador.hasNext();
		if (ok) {
			produto = iterador.next();
		}
		return ok;
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		Produto prod = produto;
		if (field.getName().equals("codBarras")) {
			return prod.getCodBarras();
		}
		if (field.getName().equals("nomeProduto")) {
			return prod.getNomeProduto();
		}
		if (field.getName().equals("valorProd")) {
			return prod.getValorProd();
		}
		return null;
	}

}
