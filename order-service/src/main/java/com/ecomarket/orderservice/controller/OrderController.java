package com.ecomarket.orderservice.controller;
import com.ecomarket.orderservice.dto.*;
import com.ecomarket.orderservice.entity.*;
import com.ecomarket.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import java.util.*;
import java.util.stream.Collectors;
@Tag(name = "Order", description = "Endpoints para la gestion de Order")
@RestController @RequestMapping("/api/orders") @RequiredArgsConstructor @Slf4j
public class OrderController {
    private final OrderService service;

    @Operation(summary = "Obtener todos los registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping 
    public ResponseEntity<List<Order>> getAll() { 
        return ResponseEntity.ok(service.getAllOrders()); 
    }

    @Operation(summary = "Crear nuevo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta o stock insuficiente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping 
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderCreateDTO dto) {
        return ResponseEntity.status(201).body(service.createOrder(dto));
    }
}
