package com.ecomarket.orderservice.controller;

import com.ecomarket.orderservice.dto.*;
import com.ecomarket.orderservice.entity.Order;
import com.ecomarket.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock(answer = org.mockito.Answers.RETURNS_DEEP_STUBS)
    private WebClient inventoryWebClient;

    @Mock(answer = org.mockito.Answers.RETURNS_DEEP_STUBS)
    private WebClient couponWebClient;

    @Mock(answer = org.mockito.Answers.RETURNS_DEEP_STUBS)
    private WebClient billingWebClient;

    @InjectMocks
    private OrderController orderController;

    @Test
    void getAll_ReturnsOrders() {
        // Given
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));

        // When
        ResponseEntity<List<Order>> response = orderController.getAll();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }
}
