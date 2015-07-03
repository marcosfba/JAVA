/**
 * 
 */
package br.edu.unitri.bean;

import java.io.Serializable;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@RequestScoped
public class BidExercicios implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Double valorJ;
	private Double valorD;
	private String regexText;
	private Date date;

	public BidExercicios() {
		super();
	}

	public BidExercicios(Double valorJ, Double valorD, String regexText,
			Date date) {
		super();
		this.valorJ = valorJ;
		this.valorD = valorD;
		this.regexText = regexText;
		this.date = date;
	}

	public Double getValorJ() {
		return valorJ;
	}

	public void setValorJ(Double valorJ) {
		this.valorJ = valorJ;
	}

	public Double getValorD() {
		return valorD;
	}

	public void setValorD(Double valorD) {
		this.valorD = valorD;
	}

	public String getRegexText() {
		return regexText;
	}

	public void setRegexText(String regexText) {
		this.regexText = regexText;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


}
