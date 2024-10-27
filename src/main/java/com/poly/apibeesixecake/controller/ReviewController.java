package com.poly.apibeesixecake.controller;

import com.poly.apibeesixecake.model.Review;
import com.poly.apibeesixecake.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{idreview}")
    public ResponseEntity<?> getReviewById(@PathVariable Integer idreview) {
        try {
            Review review = reviewService.getReviewById(idreview);
            return ResponseEntity.ok(review);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @GetMapping("/product/{idproduct}")
    public ResponseEntity<?> getReviewsByProductId(@PathVariable Integer idproduct) {
        List<Review> reviews = reviewService.getReviewsByProductId(idproduct);
        if (reviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Không có đánh giá nào cho sản phẩm này.");
            }});
        }
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/account/{idaccount}")
    public ResponseEntity<?> getReviewsByAccountId(@PathVariable String idaccount) {
        List<Review> reviews = reviewService.getReviewsByAccountId(idaccount);
        if (reviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", "Không có đánh giá nào cho tài khoản này.");
            }});
        }
        return ResponseEntity.ok(reviews);
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Review review) {
        try {
            Review createdReview = reviewService.createReview(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @PutMapping("/{idreview}")
    public ResponseEntity<?> updateReview(@PathVariable Integer idreview, @RequestBody Review reviewDetails) {
        try {
            Review updatedReview = reviewService.updateReview(idreview, reviewDetails);
            return ResponseEntity.ok(updatedReview);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }

    @DeleteMapping("/{idreview}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer idreview) {
        try {
            reviewService.deleteReview(idreview);
            return ResponseEntity.ok(new HashMap<String, String>() {{
                put("message", "Xóa đánh giá thành công.");
            }});
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>() {{
                put("error", e.getMessage());
            }});
        }
    }
}
