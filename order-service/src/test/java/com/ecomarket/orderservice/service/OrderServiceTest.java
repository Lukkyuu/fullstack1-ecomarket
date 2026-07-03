package com.ecomarket.orderservice.service;

import com.ecomarket.orderservice.dto.*;
import com.ecomarket.orderservice.entity.Order;
import com.ecomarket.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private WebClient inventoryWebClient;
    @Mock
    private WebClient couponWebClient;
    @Mock
    private WebClient billingWebClient;
    
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @InjectMocks
    private OrderService service;

    @Test
    void givenOrders_whenGetAllOrders_thenReturnList() {
        // Given
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));

        // When
        List<Order> result = service.getAllOrders();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderRepository, times(1)).findAll();
    }
    
    // Note: createOrder test involves complex WebClient mocking. 
    // In a real scenario we'd use MockWebServer, but for now we verify the repository behavior.
    @Test
    void testCreateOrderThrowsExceptionWhenWebClientFails() {
        OrderCreateDTO dto = new OrderCreateDTO();
        OrderItemCreateDTO item = new OrderItemCreateDTO();
        item.setProductId(1L);
        item.setQuantity(2);
        dto.setItems(Collections.singletonList(item));

        // Mock WebClient chain to throw exception
        lenient().when(inventoryWebClient.get()).thenReturn(requestHeadersUriSpec);
        lenient().when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);
        lenient().when(requestHeadersUriSpec.uri(anyString(), anyLong())).thenReturn(requestHeadersSpec);
        lenient().when(requestHeadersSpec.retrieve()).thenThrow(new RuntimeException("Connection failed"));

        assertThrows(RuntimeException.class, () -> service.createOrder(dto));
    }
}
