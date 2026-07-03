package com.ecomarket.inventoryservice.service;

import com.ecomarket.inventoryservice.dto.ProductDTO;
import com.ecomarket.inventoryservice.dto.StockReductionDTO;
import com.ecomarket.inventoryservice.entity.Category;
import com.ecomarket.inventoryservice.entity.Product;
import com.ecomarket.inventoryservice.exception.ResourceNotFoundException;
import com.ecomarket.inventoryservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService service;

    private Product createMockProduct(Long id, int stock) {
        Product p = new Product();
        p.setId(id);
        p.setName("Product " + id);
        p.setStock(stock);
        p.setPrice(BigDecimal.TEN);
        
        Category c = new Category();
        c.setId(1L);
        c.setName("Category");
        p.setCategory(c);
        
        return p;
    }

    @Test
    void givenProducts_whenGetAllProducts_thenReturnProductDTOList() {
        // Given
        Product product = createMockProduct(1L, 10);
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        // When
        List<ProductDTO> result = service.getAllProducts();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void givenValidId_whenGetProductById_thenReturnProductDTO() {
        // Given
        Long id = 1L;
        Product product = createMockProduct(id, 10);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        // When
        ProductDTO result = service.getProductById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(productRepository, times(1)).findById(id);
    }
    
    @Test
    void givenInvalidId_whenGetProductById_thenThrowException() {
        // Given
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> service.getProductById(id));
    }

    @Test
    void givenValidReductions_whenReduceStock_thenUpdateStock() {
        // Given
        Long id = 1L;
        Product product = createMockProduct(id, 10);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        List<StockReductionDTO> reductions = Collections.singletonList(new StockReductionDTO(id, 2));

        // When
        service.reduceStock(reductions);

        // Then
        assertEquals(8, product.getStock());
        verify(productRepository, times(1)).save(product);
    }
    
    @Test
    void givenInsufficientStock_whenReduceStock_thenThrowException() {
        // Given
        Long id = 1L;
        Product product = createMockProduct(id, 1);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        List<StockReductionDTO> reductions = Collections.singletonList(new StockReductionDTO(id, 2));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> service.reduceStock(reductions));
    }
}
