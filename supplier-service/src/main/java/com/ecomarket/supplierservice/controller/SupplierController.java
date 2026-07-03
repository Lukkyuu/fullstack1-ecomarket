package com.ecomarket.supplierservice.controller;
import com.ecomarket.supplierservice.entity.Supplier;
import com.ecomarket.supplierservice.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
@Tag(name = "Supplier", description = "Endpoints para la gestion de Supplier")
@RestController @RequestMapping("/api/suppliers") @RequiredArgsConstructor
public class SupplierController {
    private final SupplierService service;
    
    @Operation(summary = "Obtener todos los registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping 
    public ResponseEntity<List<Supplier>> getAll() { 
        return ResponseEntity.ok(service.getAllSuppliers()); 
    }
    
    @Operation(summary = "Crear nuevo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping 
    public ResponseEntity<Supplier> create(@RequestBody Supplier s) { 
        return ResponseEntity.status(201).body(service.createSupplier(s)); 
    }
}
