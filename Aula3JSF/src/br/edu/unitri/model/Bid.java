package br.edu.unitri.model;

import java.io.Serializable;

public class Bid implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userID;
	private String keyword;
	private String bidAmount;
	private String bidDuration;

	public Bid() {
		super();
	}

	public Bid(String userID, String keyword, String bidAmount,
			String bidDuration) {
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

	public String getBidAmount() {
		return bidAmount;
	}

	public void setBidAmount(String bidAmount) {
		this.bidAmount = bidAmount;
	}

	public String getBidDuration() {
		return bidDuration;
	}

	public void setBidDuration(String bidDuration) {
		this.bidDuration = bidDuration;
	}

}
