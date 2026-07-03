package com.ecomarket.orderservice.service;

import com.ecomarket.orderservice.dto.*;
import com.ecomarket.orderservice.entity.*;
import com.ecomarket.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient inventoryWebClient;
    private final WebClient couponWebClient;
    private final WebClient billingWebClient;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public OrderResponseDTO createOrder(OrderCreateDTO dto) {
        log.info("Creando orden para {}", dto.getCustomerName());
        List<StockReductionDTO> reductions = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> items = new ArrayList<>();

        for (OrderItemCreateDTO itemDto : dto.getItems()) {
            ProductDTO p;
            try {
                p = inventoryWebClient.get()
                        .uri("/api/products/{id}", itemDto.getProductId())
                        .retrieve()
                        .bodyToMono(ProductDTO.class)
                        .timeout(Duration.ofSeconds(5))
                        .block();
            } catch (Exception e) {
                log.error("Error al obtener producto del inventario: {}", e.getMessage());
                throw new RuntimeException("Error comunicando con el inventario para el producto: " + itemDto.getProductId());
            }

            if (p == null || p.getStock() < itemDto.getQuantity()) {
                throw new IllegalArgumentException("Stock insuficiente para producto ID: " + itemDto.getProductId());
            }

            BigDecimal linePrice = p.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            total = total.add(linePrice);

            items.add(OrderItem.builder().productId(p.getId()).quantity(itemDto.getQuantity()).unitPrice(p.getPrice()).build());
            reductions.add(new StockReductionDTO(p.getId(), itemDto.getQuantity()));
        }

        // Aplicar cupón si existe llamando a coupon-service
        if (dto.getCouponCode() != null && !dto.getCouponCode().isBlank()) {
            try {
                Double pct = couponWebClient.get()
                        .uri("/api/coupons/validate/{code}", dto.getCouponCode())
                        .retrieve()
                        .bodyToMono(Double.class)
                        .timeout(Duration.ofSeconds(5))
                        .block();
                if (pct != null && pct > 0) {
                    BigDecimal discount = total.multiply(BigDecimal.valueOf(pct / 100.0));
                    total = total.subtract(discount);
                    log.info("Cupón {} aplicado. Descuento del {}%.", dto.getCouponCode(), pct);
                }
            } catch (Exception ex) {
                log.warn("Error al validar cupón: {}", ex.getMessage());
            }
        }

        // Reducir stock en inventory-service
        try {
            inventoryWebClient.put().uri("/api/products/reduce-stock").bodyValue(reductions)
                    .retrieve().toBodilessEntity().timeout(Duration.ofSeconds(5)).block();
        } catch (Exception e) {
            log.error("Error al reducir stock: {}", e.getMessage());
            throw new RuntimeException("No se pudo reducir el stock en el inventario.");
        }

        Order order = Order.builder()
                .customerName(dto.getCustomerName())
                .customerEmail(dto.getCustomerEmail())
                .shippingAddress(dto.getShippingAddress())
                .status("CONFIRMED")
                .orderDate(LocalDateTime.now())
                .totalAmount(total)
                .build();

        for (OrderItem i : items) { i.setOrder(order); }
        order.setItems(items);

        Order saved = orderRepository.save(order);

        // Emitir factura en billing-service
        try {
            Map<String, Object> invoiceReq = new HashMap<>();
            invoiceReq.put("orderId", saved.getId());
            invoiceReq.put("totalAmount", saved.getTotalAmount());
            billingWebClient.post().uri("/api/invoices").bodyValue(invoiceReq)
                    .retrieve().toBodilessEntity().timeout(Duration.ofSeconds(5)).block();
            log.info("Factura generada en billing-service para orden ID {}", saved.getId());
        } catch (Exception ex) {
            log.warn("Error al emitir factura, la orden fue creada pero la factura falló: {}", ex.getMessage());
            // No hacemos rollback porque la orden ya está confirmada y pagada (simuladamente).
        }

        return OrderResponseDTO.builder()
                .id(saved.getId())
                .customerName(saved.getCustomerName())
                .customerEmail(saved.getCustomerEmail())
                .shippingAddress(saved.getShippingAddress())
                .status(saved.getStatus())
                .orderDate(saved.getOrderDate())
                .totalAmount(saved.getTotalAmount())
                .build();
    }
}
