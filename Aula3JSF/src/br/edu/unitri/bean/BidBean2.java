/**
 * 
 */
package br.edu.unitri.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.edu.unitri.model.Bid2;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@RequestScoped
public class BidBean2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Bid2 bid = new Bid2();

	public BidBean2() {
		super();
	}

	public BidBean2(Bid2 bid) {
		super();
		this.bid = bid;
	}

	public Bid2 getBid() {
		return bid;
	}

	public void setBid(Bid2 bid) {
		this.bid = bid;
	}
	
	public String doBid() {
		return "sucesso2.xhtml";
	}
	
	private int currentBidDuration(){
        return 9;
    }

	
	public void validateBidDuration(FacesContext context, UIComponent comp, Object value) throws ValidatorException {
		int bidDuration = ((Integer) value).intValue();
		int previous = currentBidDuration();
		if (bidDuration <= previous){
			FacesMessage message = new FacesMessage("Deve ser um lance maior!");
			throw new ValidatorException(message);
		}
	}

}