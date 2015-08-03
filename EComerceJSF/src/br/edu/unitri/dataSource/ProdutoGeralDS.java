/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.edu.unitri.dto.ProdutoGeralDTO;
import br.edu.unitri.util.Path;

/**
 * @author marcos.fernando
 *
 */
public class ProdutoGeralDS implements JRDataSource {

	private Iterator<ProdutoGeralDTO> iterador;
	private ProdutoGeralDTO produtoGeralDTO;
	private String diretorio = Path.PATH_FOTOS;
	
	public ProdutoGeralDS() {
		super();
	}

	public ProdutoGeralDS(List<ProdutoGeralDTO> listaProdutoGeralDTOs) {
		super();
		iterador = listaProdutoGeralDTOs.iterator();
	}

	@Override
	public boolean next() throws JRException {
		boolean ok = iterador.hasNext();
		if (ok) {
			produtoGeralDTO = iterador.next();
		}
		return ok;
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		ProdutoGeralDTO produtoGeral = produtoGeralDTO;
		
		if (field.getName().equals("idProduto")) {
			return produtoGeral.getIdProduto(); 
		}
		if (field.getName().equals("codInterno")) {
			return produtoGeral.getCodInterno(); 
		}
		if (field.getName().equals("codBarras")) {
			return produtoGeral.getCodBarras();
		}
		if (field.getName().equals("descProduto")) {
			return produtoGeral.getDescProduto();
		}
		if (field.getName().equals("unidade")) {
			return produtoGeral.getUnidade();
		}
		if (field.getName().equals("complemento")) {
			return produtoGeral.getComplemento();
		}
		if (field.getName().equals("nomeCategoria")) {
			return produtoGeral.getNomeCategoria();
		}
		if (field.getName().equals("compCategoria")) {
			return produtoGeral.getCompCategoria();
		}	
		if (field.getName().equals("nomeMarca")) {
			return produtoGeral.getNomeMarca();
		}
		if (field.getName().equals("compMarca")) {
			return produtoGeral.getCompMarca();
		}
		if (field.getName().equals("valorCusto")) {
			return produtoGeral.getValorCusto();
		}
		if (field.getName().equals("valorVenda")) {
			return produtoGeral.getValorVenda();
		}
		if (field.getName().equals("docFabricante")) {
			return produtoGeral.getDocFabricante();
		}
		if (field.getName().equals("fotoProduto")) {
			return  getDiretorio().concat(produtoGeral.getFotoProduto());
		}
		return null;
	}
	
	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(String diretorio) {
		this.diretorio = diretorio;
	}

}
