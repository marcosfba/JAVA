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

import br.edu.unitri.enumerators.TipoPagamento;

/**
 * @author Marcos
 *
 */
@FacesConverter(value ="tipoPagamentoConverter")
public class TipoPagamentoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		if (arg0 == null) {
			throw new NullPointerException("context");
		}
		if (arg1 == null) {
			throw new NullPointerException("component");
		}

		TipoPagamento tipoPagamento = null;
		if (arg2 != null && !arg2.equalsIgnoreCase("")
				&& arg2.trim().length() > 0) {
			for (TipoPagamento type : TipoPagamento.values()) {
				String desc = type.getDescricao();
				if (desc.equalsIgnoreCase(arg2)) {
					tipoPagamento = type;
					break;				
				}
			}
			if (tipoPagamento == null) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "Valor n�o encontrado",
						"Tipo de Pagamento inv�lido!");
				throw new ConverterException(message);
			}
		}
		return tipoPagamento;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		if (arg0 == null) {
			throw new NullPointerException("context");
		}
		if (arg1 == null) {
			throw new NullPointerException("component");
		}
		if (arg2 instanceof TipoPagamento) {
			return ((TipoPagamento)arg2).toString();
		} else {
			return "";
		}
	}

}
