package com.ecomarket.order.service;

import com.ecomarket.order.dto.OrderCreateDTO;
import com.ecomarket.order.dto.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO getOrderById(Long id);
    OrderResponseDTO createOrder(OrderCreateDTO orderCreateDTO);
}
