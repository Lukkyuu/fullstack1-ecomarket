package com.ecomarket.order.service;

import com.ecomarket.order.client.InventoryClient;
import com.ecomarket.order.dto.*;
import com.ecomarket.order.entity.Order;
import com.ecomarket.order.entity.OrderItem;
import com.ecomarket.order.entity.OrderStatus;
import com.ecomarket.order.exception.InventoryServiceException;
import com.ecomarket.order.exception.ResourceNotFoundException;
import com.ecomarket.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getAllOrders() {
        log.info("Obteniendo lista de todas las órdenes");
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDTO getOrderById(Long id) {
        log.info("Obteniendo orden con ID: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Orden no encontrada con ID: {}", id);
                    return new ResourceNotFoundException("Orden no encontrada con id: " + id);
                });
        return convertToDTO(order);
    }

    @Override
    @Transactional
    public OrderResponseDTO createOrder(OrderCreateDTO dto) {
        log.info("Iniciando creación de orden para cliente: {}", dto.getCustomerName());

        List<StockReductionDTO> reductions = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // 1. Validar productos y stock remotamente
        for (OrderItemCreateDTO itemDto : dto.getItems()) {
            log.debug("Validando producto ID: {} con cantidad: {}", itemDto.getProductId(), itemDto.getQuantity());
            ProductDTO product;
            try {
                product = inventoryClient.getProductById(itemDto.getProductId());
            } catch (Exception ex) {
                log.error("Error al validar producto ID {}: {}", itemDto.getProductId(), ex.getMessage());
                throw new InventoryServiceException("No se pudo validar el producto ID " + itemDto.getProductId() + ": " + ex.getMessage());
            }

            if (product == null) {
                log.error("El producto con ID {} no existe en inventario", itemDto.getProductId());
                throw new ResourceNotFoundException("El producto con id " + itemDto.getProductId() + " no existe");
            }

            if (product.getStock() < itemDto.getQuantity()) {
                log.error("Stock insuficiente para producto '{}' (ID: {}). Disponible: {}, Requerido: {}",
                        product.getName(), product.getId(), product.getStock(), itemDto.getQuantity());
                throw new InventoryServiceException("Stock insuficiente para el producto '" + product.getName() +
                        "'. Stock disponible: " + product.getStock() + ", requerido: " + itemDto.getQuantity());
            }

            // Calcular montos
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);

            // Crear ítem de orden temporal
            OrderItem orderItem = OrderItem.builder()
                    .productId(itemDto.getProductId())
                    .quantity(itemDto.getQuantity())
                    .unitPrice(product.getPrice())
                    .build();
            orderItems.add(orderItem);

            // Registrar reducción para llamada en lote
            reductions.add(StockReductionDTO.builder()
                    .productId(itemDto.getProductId())
                    .quantity(itemDto.getQuantity())
                    .build());
        }

        // 2. Realizar reducción de stock en el microservicio de inventario
        log.info("Llamando a reducir stock para la orden de compra");
        inventoryClient.reduceStock(reductions);

        // 3. Crear y guardar orden en la base de datos local
        Order order = Order.builder()
                .customerName(dto.getCustomerName())
                .customerEmail(dto.getCustomerEmail())
                .shippingAddress(dto.getShippingAddress())
                .status(OrderStatus.CONFIRMED)
                .orderDate(LocalDateTime.now())
                .totalAmount(totalAmount)
                .build();

        // Relacionar ítems con la orden
        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        log.info("Orden guardada exitosamente con ID: {}, total: {}", savedOrder.getId(), savedOrder.getTotalAmount());

        return convertToDTO(savedOrder);
    }

    private OrderResponseDTO convertToDTO(Order order) {
        List<OrderItemResponseDTO> items = order.getItems().stream()
                .map(item -> OrderItemResponseDTO.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .build())
                .collect(Collectors.toList());

        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .customerEmail(order.getCustomerEmail())
                .shippingAddress(order.getShippingAddress())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .items(items)
                .build();
    }
}
