package com.ecomarket.storeservice.controller;
import com.ecomarket.storeservice.entity.Store;
import com.ecomarket.storeservice.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
@Tag(name = "Store", description = "Endpoints para la gestion de Store")
@RestController @RequestMapping("/api/stores") @RequiredArgsConstructor
public class StoreController {
    private final StoreService service;
    
    @Operation(summary = "Obtener todos los registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping 
    public ResponseEntity<List<Store>> getAll() { 
        return ResponseEntity.ok(service.getAllStores()); 
    }
    
    @Operation(summary = "Crear nuevo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping 
    public ResponseEntity<Store> create(@RequestBody Store s) { 
        return ResponseEntity.status(201).body(service.createStore(s)); 
    }
}
