package com.ecomarket.reviewservice.service;

import com.ecomarket.reviewservice.entity.Review;
import com.ecomarket.reviewservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;

    public List<Review> getAllReviews() {
        return repository.findAll();
    }

    public List<Review> getReviewsByProductId(Long productId) {
        return repository.findByProductId(productId);
    }

    public Review createReview(Review r) {
        return repository.save(r);
    }
}
