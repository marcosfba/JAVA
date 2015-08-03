/**
 * 
 */
package br.edu.unitri.converter;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.edu.unitri.enumerators.TipoUsuario;

/**
 * @author Marcos
 *
 */
@FacesConverter(value ="tipoUsuarioConverter")
public class TipoUsuarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg0 == null) {
			throw new NullPointerException("context");
		}
		if (arg1 == null) {
			throw new NullPointerException("component");
		}

		TipoUsuario tipoUsuario = null;
		if (arg2 != null && !arg2.equalsIgnoreCase("")
				&& arg2.trim().length() > 0) {
			System.out.println(arg2);
			for (TipoUsuario type : TipoUsuario.values()) {
				String desc = type.getDescricao().trim();
				if (desc.equalsIgnoreCase(arg2.trim())) {
					tipoUsuario = type;
					break;				
				}
			}
			if (tipoUsuario == null) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Valor não encontrado",
						"Tipo de Usuário inválido!");
				throw new ConverterException(message);
			}
		}
		return tipoUsuario;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg0 == null) {
			throw new NullPointerException("context");
		}
		if (arg1 == null) {
			throw new NullPointerException("component");
		}
		if (arg2 instanceof TipoUsuario) {
			return ((TipoUsuario)arg2).toString();
		} else {
			return "";
		}
	}

}
