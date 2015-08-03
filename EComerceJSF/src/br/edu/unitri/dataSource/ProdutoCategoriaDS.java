/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.edu.unitri.dto.ProdutoCategoriaDTO;

/**
 * @author marcos.fernando
 *
 */
public class ProdutoCategoriaDS implements JRDataSource {
	
	private Iterator<ProdutoCategoriaDTO> iterador;
	private ProdutoCategoriaDTO produtoCategoriaDTO;
	
	public ProdutoCategoriaDS() {
		super();
	}

	public ProdutoCategoriaDS(List<ProdutoCategoriaDTO> listaprodCategoriaDTOs) {
		super();
		iterador = listaprodCategoriaDTOs.iterator();
	}

	@Override
	public boolean next() throws JRException {
		boolean ok = iterador.hasNext();
		if (ok) {
			produtoCategoriaDTO = iterador.next();
		}
		return ok;
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		ProdutoCategoriaDTO produtoCat = produtoCategoriaDTO;
		
		if (field.getName().equals("codProduto")) {
			return produtoCat.getCodProduto(); 
		}
		if (field.getName().equals("nomeProduto")) {
			return produtoCat.getNomeProduto(); 
		}
		if (field.getName().equals("valorProd")) {
			return produtoCat.getValorProd();
		}
		if (field.getName().equals("valorCusto")) {
			return produtoCat.getValorCusto();
		}
		if (field.getName().equals("descCategoria")) {
			return produtoCat.getDescCategoria();
		}
		if (field.getName().equals("compCategoria")) {
			return produtoCat.getCompCategoria();
		}
		if (field.getName().equals("idCategoria")) {
			return produtoCat.getIdCategoria();
		}
		if (field.getName().equals("unidade")) {
			return produtoCat.getUnidade();
		}
		return null;
		
	}
}
