/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.edu.unitri.dto.ProdutoMarcaDTO;

/**
 * @author marcos.fernando
 *
 */
public class ProdutoMarcaDS implements JRDataSource {

	private Iterator<ProdutoMarcaDTO> iterador;
	private ProdutoMarcaDTO produtoMarcaDTO;
	
	public ProdutoMarcaDS() {
		super();
	}

	public ProdutoMarcaDS(List<ProdutoMarcaDTO> listaprodMarcaDTOs) {
		super();
		iterador = listaprodMarcaDTOs.iterator();
	}

	@Override
	public boolean next() throws JRException {
		boolean ok = iterador.hasNext();
		if (ok) {
			produtoMarcaDTO = iterador.next();
		}
		return ok;
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		ProdutoMarcaDTO produtoMar = produtoMarcaDTO;
		
		if (field.getName().equals("codProduto")) {
			return produtoMar.getCodProduto(); 
		}
		if (field.getName().equals("nomeProduto")) {
			return produtoMar.getNomeProduto(); 
		}
		if (field.getName().equals("valorProd")) {
			return produtoMar.getValorProd();
		}
		if (field.getName().equals("valorCusto")) {
			return produtoMar.getValorCusto();
		}
		if (field.getName().equals("descMarca")) {
			return produtoMar.getDescMarca();
		}
		if (field.getName().equals("compMarca")) {
			return produtoMar.getCompMarca();
		}
		if (field.getName().equals("idMarca")) {
			return produtoMar.getIdMarca();
		}
		if (field.getName().equals("unidade")) {
			return produtoMar.getUnidade();
		}
		return null;
	}

}
