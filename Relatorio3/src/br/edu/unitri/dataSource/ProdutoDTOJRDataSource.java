/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import br.edu.unitri.model.ProdutoDTO;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * @author Marcos
 *
 */
public class ProdutoDTOJRDataSource implements JRDataSource {

	private Iterator<ProdutoDTO> iterador;
	private ProdutoDTO produto;

	public ProdutoDTOJRDataSource() {
		super();
	}

	public ProdutoDTOJRDataSource(List<ProdutoDTO> produtos) {
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
		ProdutoDTO prod = produto;
		String fieldName = field.getName();
		if ("codProduto".equals(fieldName)) {
			return prod.getProduto().getCodProduto();
		}
		if ("nomeProduto".equals(fieldName)) {
			return prod.getProduto().getNomeProduto();
		}
		if ("descCategoria".equals(fieldName)) {
			return prod.getProduto().getCategoria().getDescCategoria();
		}
		if ("valor".equals(fieldName)) {
			return prod.getValor();
		}
		if ("itens".equals(fieldName)) {
			return prod.getItens();
		}
		if ("mes".equals(fieldName)) {
			return prod.getMes();
		}
		return null;
	}

}
