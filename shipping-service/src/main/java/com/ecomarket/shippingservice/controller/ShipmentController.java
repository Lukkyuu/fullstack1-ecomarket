package com.ecomarket.shippingservice.controller;
import com.ecomarket.shippingservice.entity.Shipment;
import com.ecomarket.shippingservice.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.*;
@Tag(name = "Shipment", description = "Endpoints para la gestion de Shipment")
@RestController @RequestMapping("/api/shipments") @RequiredArgsConstructor
public class ShipmentController {
    private final ShipmentService service;
    
    @Operation(summary = "Obtener todos los registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping 
    public ResponseEntity<List<Shipment>> getAll() { 
        return ResponseEntity.ok(service.getAllShipments()); 
    }
    
    @Operation(summary = "Crear nuevo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping 
    public ResponseEntity<Shipment> create(@RequestBody Shipment s) {
        return ResponseEntity.status(201).body(service.createShipment(s));
    }
}
