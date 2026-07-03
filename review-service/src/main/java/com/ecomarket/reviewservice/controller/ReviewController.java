package com.ecomarket.reviewservice.controller;
import com.ecomarket.reviewservice.entity.Review;
import com.ecomarket.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
@Tag(name = "Review", description = "Endpoints para la gestion de Review")
@RestController @RequestMapping("/api/reviews") @RequiredArgsConstructor
public class ReviewController {
    private final ReviewService service;
    
    @Operation(summary = "Obtener todos los registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping 
    public ResponseEntity<List<Review>> getAll() { 
        return ResponseEntity.ok(service.getAllReviews()); 
    }
    
    @Operation(summary = "Obtener registro por ID u otro criterio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping("/product/{productId}") 
    public ResponseEntity<List<Review>> getByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(service.getReviewsByProductId(productId));
    }
    
    @Operation(summary = "Crear nuevo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping 
    public ResponseEntity<Review> create(@RequestBody Review r) { 
        return ResponseEntity.status(201).body(service.createReview(r)); 
    }
}
