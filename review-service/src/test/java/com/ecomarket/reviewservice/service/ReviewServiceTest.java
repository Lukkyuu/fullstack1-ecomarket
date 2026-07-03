package com.ecomarket.reviewservice.service;

import com.ecomarket.reviewservice.entity.Review;
import com.ecomarket.reviewservice.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository repository;

    @InjectMocks
    private ReviewService service;

    @Test
    void givenReviews_whenGetAllReviews_thenReturnReviewList() {
        // Given
        Review review = new Review();
        review.setId(1L);
        when(repository.findAll()).thenReturn(Collections.singletonList(review));

        // When
        List<Review> result = service.getAllReviews();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }
    
    @Test
    void givenProductId_whenGetReviewsByProductId_thenReturnReviewList() {
        // Given
        Long productId = 10L;
        Review review = new Review();
        review.setProductId(productId);
        when(repository.findByProductId(productId)).thenReturn(Collections.singletonList(review));

        // When
        List<Review> result = service.getReviewsByProductId(productId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findByProductId(productId);
    }

    @Test
    void givenReview_whenCreateReview_thenReturnSavedReview() {
        // Given
        Review review = new Review();
        review.setId(1L);
        when(repository.save(any(Review.class))).thenReturn(review);

        // When
        Review result = service.createReview(review);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(Review.class));
    }
}
