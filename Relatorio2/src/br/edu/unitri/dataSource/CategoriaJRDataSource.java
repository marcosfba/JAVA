/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.Iterator;
import java.util.List;

import br.edu.unitri.model.Categoria;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * @author Marcos
 *
 */
public class CategoriaJRDataSource implements JRDataSource {
	
	private Iterator<Categoria> iterador;
	private Categoria categoria;
	
	public CategoriaJRDataSource() {
		super();
	}
	
	public CategoriaJRDataSource(List<Categoria> clientes) {
		super();
		iterador = clientes.iterator();
	}	

	@Override
	public boolean next() throws JRException {
		boolean ok = iterador.hasNext();
		if (ok) {
			categoria = iterador.next();
		}
		return ok;
	}
	
	@Override
	public Object getFieldValue(JRField field) throws JRException {
		Categoria cl = categoria;
		if (field.getName().equals("codCategoria")) {
			return cl.getCodCategoria();
		}
		if (field.getName().equals("nomeCategoria")) {
			return cl.getNomeCategoria();
		}
		if (field.getName().equals("descCategoria")) {
			return cl.getDescCategoria();
		}
		return null;
	}

}
