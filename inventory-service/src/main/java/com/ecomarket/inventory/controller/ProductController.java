package com.ecomarket.inventory.controller;

import com.ecomarket.inventory.dto.ProductCreateDTO;
import com.ecomarket.inventory.dto.ProductDTO;
import com.ecomarket.inventory.dto.StockReductionDTO;
import com.ecomarket.inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.info("REST request to get all products");
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        log.info("REST request to get product: {}", id);
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        log.info("REST request to get products of category: {}", categoryId);
        List<ProductDTO> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductCreateDTO productCreateDTO) {
        log.info("REST request to create product: {}", productCreateDTO.getName());
        ProductDTO created = productService.createProduct(productCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductCreateDTO productCreateDTO) {
        log.info("REST request to update product: {}", id);
        ProductDTO updated = productService.updateProduct(id, productCreateDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("REST request to delete product: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/reduce-stock")
    public ResponseEntity<Void> reduceStock(@RequestBody List<@Valid StockReductionDTO> reductions) {
        log.info("REST request to reduce stock for {} items", reductions.size());
        productService.reduceStock(reductions);
        return ResponseEntity.ok().build();
    }
}
