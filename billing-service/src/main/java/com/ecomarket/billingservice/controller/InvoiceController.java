package com.ecomarket.billingservice.controller;
import com.ecomarket.billingservice.entity.Invoice;
import com.ecomarket.billingservice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
@Tag(name = "Invoice", description = "Endpoints para la gestion de Invoice")
@RestController @RequestMapping("/api/invoices") @RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService service;
    
    @Operation(summary = "Obtener todos los registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping 
    public ResponseEntity<List<Invoice>> getAll() { 
        return ResponseEntity.ok(service.getAllInvoices()); 
    }
    
    @Operation(summary = "Crear nuevo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping 
    public ResponseEntity<Invoice> create(@RequestBody Map<String, Object> req) {
        return ResponseEntity.status(201).body(service.createInvoice(req));
    }
}
