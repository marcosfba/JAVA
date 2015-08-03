/**
 * 
 */
package br.edu.unitri.converter;

import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.edu.unitri.controler.PessoaControler;
import br.edu.unitri.model.Pessoa;

/**
 * @author marcos.fernando
 *
 */
@FacesConverter(forClass=Pessoa.class)
public class PessoaConverter implements Converter {
	
	private PessoaControler ctr = new PessoaControler();
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		Integer idPessoa = Integer.parseInt(arg2);
		Pessoa pessoa = null;
		try {
			pessoa = ctr.getById(idPessoa);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pessoa;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return ((Pessoa) arg2).getId().toString();
	}

}
