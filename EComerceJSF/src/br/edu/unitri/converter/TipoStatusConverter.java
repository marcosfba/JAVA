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

import br.edu.unitri.enumerators.TipoStatus;

/**
 * @author Marcos
 *
 */
@FacesConverter(value ="tipoStatusConverter")
public class TipoStatusConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg0 == null) {
			throw new NullPointerException("context");
		}
		if (arg1 == null) {
			throw new NullPointerException("component");
		}

		TipoStatus tipoStatus = null;
		if (arg2 != null && !arg2.equalsIgnoreCase("")
				&& arg2.trim().length() > 0) {
			for (TipoStatus type : TipoStatus.values()) {
				String desc = type.getDescricao();
				if (desc.equalsIgnoreCase(arg2)) {
					tipoStatus = type;
					break;				
				}
			}
			if (tipoStatus == null) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Valor não encontrado",
						"Tipo de Status inválido!");
				throw new ConverterException(message);
			}
		}
		return tipoStatus;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg0 == null) {
			throw new NullPointerException("context");
		}
		if (arg1 == null) {
			throw new NullPointerException("component");
		}
		if (arg2 instanceof TipoStatus) {
			return ((TipoStatus)arg2).toString();
		} else {
			return "";
		}
	}

}
