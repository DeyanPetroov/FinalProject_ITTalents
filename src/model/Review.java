package model;

import java.time.LocalDateTime;

public class Review {

	private long reviewID;
	private int raiting;
	private LocalDateTime date;
	private String reviewText;
	private long reviewerID;
	private long productID;
	
	//================CONSTRUCTORS================

	public Review(int raiting, String text, long reviewerID, long productID) {
		this.date = LocalDateTime.now(); 
		this.reviewerID = reviewerID;
		this.productID = productID;
		this.reviewText = text;
		this.raiting = raiting;
	}
	
	public Review(long reviewID, int raiting, String text, long reviewerID, long productID) {
		this(raiting, text, reviewerID, productID);
		this.reviewID = reviewID;
	}
	
	//================GETTERS================

	public int getRaiting() {
		return raiting;
	}

	public String getReviewText() {
		return reviewText;
	}

	public long getReviewID() {
		return reviewID;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public long getReviewerID() {
		return reviewerID;
	}

	public long getProductID() {
		return productID;
	}
	
	//================SETTERS================
	
	public void setRaiting(int raiting) {
		if (raiting >= 0) {
			this.raiting = raiting;
		}
	}

	public void setReviewText(String reviewText) {
		if (reviewText != null && !reviewText.isEmpty()) {
			this.reviewText = reviewText;
		}
	}	
}