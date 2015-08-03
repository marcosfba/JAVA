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

import br.edu.unitri.enumerators.TipoEndereco;

/**
 * @author Marcos
 *
 */
@FacesConverter(value ="tipoEnderecoConverter")
public class TipoEnderecoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg0 == null) {
			throw new NullPointerException("context");
		}
		if (arg1 == null) {
			throw new NullPointerException("component");
		}

		TipoEndereco tipoEndereco = null;
		if (arg2 != null && !arg2.equalsIgnoreCase("")
				&& arg2.trim().length() > 0) {
			for (TipoEndereco type : TipoEndereco.values()) {
				String desc = type.getDescricao();
				if (desc.equalsIgnoreCase(arg2)) {
					tipoEndereco = type;
					break;				
				}
			}
			if (tipoEndereco == null) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Valor não encontrado",
						"Tipo de Endereço inválido!");
				throw new ConverterException(message);
			}
		}
		return tipoEndereco;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg0 == null) {
			throw new NullPointerException("context");
		}
		if (arg1 == null) {
			throw new NullPointerException("component");
		}
		if (arg2 instanceof TipoEndereco) {
			return ((TipoEndereco)arg2).toString();
		} else {
			return "";
		}
	}

}
