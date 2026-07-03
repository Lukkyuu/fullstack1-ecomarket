package com.ecomarket.orderservice.controller;

import com.ecomarket.orderservice.dto.OrderCreateDTO;
import com.ecomarket.orderservice.dto.OrderResponseDTO;
import com.ecomarket.orderservice.entity.Order;
import com.ecomarket.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService service;

    @InjectMocks
    private OrderController controller;

    @Test
    void givenOrders_whenGetAll_thenReturnOrderList() {
        // Given
        Order order = new Order();
        order.setId(1L);
        when(service.getAllOrders()).thenReturn(Collections.singletonList(order));

        // When
        ResponseEntity<List<Order>> response = controller.getAll();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).getAllOrders();
    }

    @Test
    void givenValidOrderRequest_whenCreateOrder_thenReturnOrderResponse() {
        // Given
        OrderCreateDTO dto = new OrderCreateDTO();
        dto.setCustomerName("John Doe");

        OrderResponseDTO responseDTO = OrderResponseDTO.builder()
                .id(100L)
                .customerName("John Doe")
                .totalAmount(new BigDecimal("500.00"))
                .build();

        when(service.createOrder(dto)).thenReturn(responseDTO);

        // When
        ResponseEntity<OrderResponseDTO> response = controller.createOrder(dto);

        // Then
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(100L, response.getBody().getId());
        assertEquals("John Doe", response.getBody().getCustomerName());
        verify(service, times(1)).createOrder(dto);
    }
}
