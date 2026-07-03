package com.ecomarket.inventoryservice.controller;
import com.ecomarket.inventoryservice.dto.ProductDTO;
import com.ecomarket.inventoryservice.dto.StockReductionDTO;
import com.ecomarket.inventoryservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

@Tag(name = "Product", description = "Endpoints para la gestion de Product")
@RestController @RequestMapping("/api/products") @RequiredArgsConstructor @Slf4j
public class ProductController {
    private final ProductService service;
    
    @Operation(summary = "Obtener todos los registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping 
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(service.getAllProducts());
    }
    
    @Operation(summary = "Obtener registro por ID u otro criterio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}") 
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProductById(id));
    }
    
    @Operation(summary = "Actualizar registro por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock reducido exitosamente"),
            @ApiResponse(responseCode = "400", description = "Stock insuficiente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/reduce-stock")
    public ResponseEntity<Void> reduceStock(@RequestBody List<StockReductionDTO> reductions) {
        service.reduceStock(reductions);
        return ResponseEntity.ok().build();
    }
}
