package com.ecomarket.inventoryservice.controller;

import com.ecomarket.inventoryservice.dto.ProductDTO;
import com.ecomarket.inventoryservice.dto.StockReductionDTO;
import com.ecomarket.inventoryservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService service;

    @InjectMocks
    private ProductController controller;

    @Test
    void givenProducts_whenGetAll_thenReturnProductDTOList() {
        // Given
        ProductDTO productDTO = ProductDTO.builder().id(1L).name("Test Product").build();
        when(service.getAllProducts()).thenReturn(Collections.singletonList(productDTO));

        // When
        ResponseEntity<List<ProductDTO>> response = controller.getAll();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).getAllProducts();
    }

    @Test
    void givenProductId_whenGetById_thenReturnProductDTO() {
        // Given
        Long id = 1L;
        ProductDTO productDTO = ProductDTO.builder().id(id).name("Test Product").build();
        when(service.getProductById(id)).thenReturn(productDTO);

        // When
        ResponseEntity<ProductDTO> response = controller.getById(id);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(service, times(1)).getProductById(id);
    }

    @Test
    void givenReductions_whenReduceStock_thenReturnOk() {
        // Given
        List<StockReductionDTO> reductions = Collections.singletonList(new StockReductionDTO(1L, 2));

        // When
        ResponseEntity<Void> response = controller.reduceStock(reductions);

        // Then
        assertEquals(200, response.getStatusCode().value());
        verify(service, times(1)).reduceStock(reductions);
    }
}
