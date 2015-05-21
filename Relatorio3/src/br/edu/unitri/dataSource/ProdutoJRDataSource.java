/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import br.edu.unitri.model.Produto;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

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
		String fieldName = field.getName();
		if ("codProduto".equals(fieldName)) {
			return prod.getCodProduto();
		}
		if ("nomeProduto".equals(fieldName)) {
			return prod.getNomeProduto();
		}
		if ("valorProd".equals(fieldName)) {
			return prod.getValorProd();
		}
		if ("quantidade".equals(fieldName)) {
			return prod.getQuantidade();
		}
		if ("imagem".equals(fieldName)) {
			return prod.getImagem();
		}
		if ("descCategoria".equals(fieldName)) {
			return prod.getCategoria().getDescCategoria();
		}
		if ("CodCategoria".equals(fieldName)) {
			return prod.getCategoria().getCodCategoria();
		}
		if ("NomeCategoria".equals(fieldName)) {
			return prod.getCategoria().getNomeCategoria();
		}
		return null;
	}

}
