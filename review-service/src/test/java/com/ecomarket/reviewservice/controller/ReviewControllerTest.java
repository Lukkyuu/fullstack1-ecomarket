package com.ecomarket.reviewservice.controller;

import com.ecomarket.reviewservice.entity.Review;
import com.ecomarket.reviewservice.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @Mock
    private ReviewService service;

    @InjectMocks
    private ReviewController controller;

    @Test
    void givenReviews_whenGetAll_thenReturnReviewList() {
        // Given
        Review review = new Review();
        review.setId(1L);
        when(service.getAllReviews()).thenReturn(Collections.singletonList(review));

        // When
        ResponseEntity<List<Review>> response = controller.getAll();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).getAllReviews();
    }
    
    @Test
    void givenProductId_whenGetByProduct_thenReturnReviewList() {
        // Given
        Long productId = 10L;
        Review review = new Review();
        review.setProductId(productId);
        when(service.getReviewsByProductId(productId)).thenReturn(Collections.singletonList(review));

        // When
        ResponseEntity<List<Review>> response = controller.getByProduct(productId);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).getReviewsByProductId(productId);
    }

    @Test
    void givenReview_whenCreate_thenReturnCreatedReview() {
        // Given
        Review review = new Review();
        review.setId(1L);
        when(service.createReview(any(Review.class))).thenReturn(review);

        // When
        ResponseEntity<Review> response = controller.create(review);

        // Then
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(service, times(1)).createReview(any(Review.class));
    }
}
