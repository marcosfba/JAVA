/**
 * 
 */
package br.edu.unitri.Converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.unitri.controler.CategoriaControler;
import br.edu.unitri.model.Categoria;

/**
 * @author marcos.fernando
 *
 */
@FacesConverter(forClass=Categoria.class)
public class CategoriaConverter implements Converter {
	
	private CategoriaControler ctr = new CategoriaControler();
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Integer idCategoria = Integer.parseInt(arg2);
		Categoria categoria = null;
		try {
			categoria = ctr.getById(idCategoria);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoria;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ((Categoria) arg2).getId().toString();
	}

}
