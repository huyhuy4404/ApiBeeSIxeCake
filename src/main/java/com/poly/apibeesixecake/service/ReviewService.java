package com.poly.apibeesixecake.service;

import com.poly.apibeesixecake.model.Review;
import com.poly.apibeesixecake.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(Integer idreview) {
        return reviewRepository.findById(idreview)
                .orElseThrow(() -> new IllegalArgumentException("Đánh giá không tồn tại."));
    }

    public List<Review> getReviewsByProductId(Integer idproduct) {
        return reviewRepository.findByProduct_Idproduct(idproduct);
    }

    public List<Review> getReviewsByAccountId(String idaccount) {
        return reviewRepository.findByAccount_Idaccount(idaccount);
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public Review updateReview(Integer idreview, Review reviewDetails) {
        Review review = reviewRepository.findById(idreview)
                .orElseThrow(() -> new IllegalArgumentException("Đánh giá không tồn tại."));

        review.setRating(reviewDetails.getRating());
        review.setReviewtext(reviewDetails.getReviewtext());
        review.setReviewdate(reviewDetails.getReviewdate());

        return reviewRepository.save(review);
    }

    public void deleteReview(Integer idreview) {
        if (!reviewRepository.existsById(idreview)) {
            throw new IllegalArgumentException("Đánh giá không tồn tại.");
        }
        reviewRepository.deleteById(idreview);
    }
}
