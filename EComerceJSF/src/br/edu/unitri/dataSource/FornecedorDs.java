/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import br.edu.unitri.model.Fornecedor;

/**
 * @author Marcos
 *
 */
public class FornecedorDs implements JRDataSource {
	
	private Iterator<Fornecedor> iterador;
	private Fornecedor fornecedor;
	
	public FornecedorDs() {
		super();
	}
	
	public FornecedorDs(List<Fornecedor> listaFornecedores) {
		super();
		iterador = listaFornecedores.iterator();
	}

	@Override
	public boolean next() throws JRException {
		boolean ok = iterador.hasNext();
		if (ok) {
			fornecedor = iterador.next();
		}
		return ok;
	}

	@Override
	public Object getFieldValue(JRField field) throws JRException {
		Fornecedor fornInt = fornecedor;
		if (field.getName().equals("cnpj")) {
			return  fornInt.getCnpj();
		}
		if (field.getName().equals("inscMunicipal")) {
			return  fornInt.getInscMunicipal();
		}
		if (field.getName().equals("insEstadual")) {
			return fornInt.getInsEstadual();
		}
		if (field.getName().equals("nome")) {
			return fornInt.getNome();
		}
		if (field.getName().equals("tipoPessoa")) {
			return fornInt.getTipoPessoa();
		}
		return null;
	}

	
	

}
