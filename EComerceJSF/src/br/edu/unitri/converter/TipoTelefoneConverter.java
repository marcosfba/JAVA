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

import br.edu.unitri.enumerators.TipoTelefone;

/**
 * @author Marcos
 *
 */
@FacesConverter(value ="tipoTelefoneConverter")
public class TipoTelefoneConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg0 == null) {
			throw new NullPointerException("context");
		}
		if (arg1 == null) {
			throw new NullPointerException("component");
		}

		TipoTelefone tipoTelefone = null;
		if (arg2 != null && !arg2.equalsIgnoreCase("")
				&& arg2.trim().length() > 0) {
			for (TipoTelefone type : TipoTelefone.values()) {
				String desc = type.getDescricao();
				if (desc.equalsIgnoreCase(arg2)) {
					tipoTelefone = type;
					break;				
				}
			}
			if (tipoTelefone == null) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Valor não encontrado",
						"Tipo de Telefone inválido!");
				throw new ConverterException(message);
			}
		}
		return tipoTelefone;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg0 == null) {
			throw new NullPointerException("context");
		}
		if (arg1 == null) {
			throw new NullPointerException("component");
		}
		if (arg2 instanceof TipoTelefone) {
			return ((TipoTelefone)arg2).toString();
		} else {
			return "";
		}
	}

}
