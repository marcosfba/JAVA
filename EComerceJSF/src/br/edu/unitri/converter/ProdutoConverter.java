/**
 * 
 */
package br.edu.unitri.converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.unitri.controler.ProdutoControler;
import br.edu.unitri.model.Produto;

/**
 * @author marcos.fernando
 *
 */
@FacesConverter(forClass=Produto.class)
public class ProdutoConverter implements Converter {
	
	private ProdutoControler pdr = new ProdutoControler();
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Integer idProduto = Integer.parseInt(arg2);
		Produto produto = null;
		try {
			produto = pdr.getById(idProduto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produto;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ((Produto) arg2).getId().toString();
	}

}
