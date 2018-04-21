package model.dao;

import java.time.LocalDateTime;
import java.util.TreeMap;

import model.Review;

public interface IReviewDAO {

	void addReview(Review review) throws Exception;
	void removeReviewByID(long review_id) throws Exception;
	Review getReviewByID(long review_id) throws Exception;
	TreeMap<LocalDateTime, Review> getAllReviewsForUser(long user_id) throws Exception;
	TreeMap<LocalDateTime, Review> getAllReviewsForProduct(long product_id) throws Exception;
}
