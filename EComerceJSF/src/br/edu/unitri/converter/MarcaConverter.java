/**
 * 
 */
package br.edu.unitri.converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.unitri.controler.MarcaControler;
import br.edu.unitri.model.Marca;

/**
 * @author marcos.fernando
 *
 */
@FacesConverter(forClass=Marca.class)
public class MarcaConverter implements Converter {
	
	private MarcaControler ctr = new MarcaControler();
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Integer idMarca = Integer.parseInt(arg2);
		Marca marca = null;
		try {
			marca = ctr.getById(idMarca);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return marca;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ((Marca) arg2).getId().toString();
	}

}
