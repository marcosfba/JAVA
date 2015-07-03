package br.edu.unitri.model;

import java.io.Serializable;

public class Bid2 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userID;
	private String keyword;
	private Double bidAmount;
	private Integer bidDuration;

	public Bid2() {
		super();
	}

	public Bid2(String userID, String keyword, Double bidAmount,
			Integer bidDuration) {
		super();
		this.userID = userID;
		this.keyword = keyword;
		this.bidAmount = bidAmount;
		this.bidDuration = bidDuration;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Double getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(Double bidAmount) {
		this.bidAmount = bidAmount;
	}

	public Integer getBidDuration() {
		return bidDuration;
	}

	public void setBidDuration(Integer bidDuration) {
		this.bidDuration = 10;
	}

}
