/**
 * 
 */
package br.edu.unitri.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.edu.unitri.model.Bid;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@RequestScoped
public class BidBean1 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Bid bid = new Bid();
	private double numericBidAmount = 0;
	private int numericBidDuration = 0;

	public BidBean1() {
		super();
	}

	public BidBean1(Bid bid, double numericBidAmount, int numericBidDuration) {
		super();
		this.bid = bid;
		this.numericBidAmount = numericBidAmount;
		this.numericBidDuration = numericBidDuration;
	}

	public Bid getBid() {
		return bid;
	}

	public void setBid(Bid bid) {
		this.bid = bid;
	}

	public double getNumericBidAmount() {
		return numericBidAmount;
	}

	public void setNumericBidAmount(String numericBidAmount) {
		try {
			this.numericBidAmount = Double.parseDouble(numericBidAmount);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
	}

	public int getNumericBidDuration() {
		return numericBidDuration;
	}

	public void setNumericBidDuration(String numericBidDuration) {
		try {
			this.numericBidDuration = Integer.parseInt(numericBidDuration);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
	}

	public String doBid() {
		FacesContext context = FacesContext.getCurrentInstance();

		setNumericBidAmount(bid.getBidAmount());
		setNumericBidDuration(bid.getBidDuration());
		
		if (bid.getUserID().equals("")) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro de campo",
					"ID de usuário requerido!"));
		}
		if (bid.getKeyword().equals("")) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro de campo",
					"Palavra Chave requerida!"));
		}
		if (this.numericBidAmount <= 0.10) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro de campo",
					"Valor do lance deve ser maior ou igual a R$ 0.10!"));
		}
		if (this.numericBidDuration < 15) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro de campo",
					"Duração deve ser maior ou igual a 15 dias."));
		}
		if (context.getMessageList().size() > 0) {
			return null;
		} else {
			return "sucesso.xhtml";
		}
	}

}
