package com.ecomarket.supplierservice.controller;

import com.ecomarket.supplierservice.entity.Supplier;
import com.ecomarket.supplierservice.service.SupplierService;
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
class SupplierControllerTest {

    @Mock
    private SupplierService service;

    @InjectMocks
    private SupplierController controller;

    @Test
    void givenSuppliers_whenGetAll_thenReturnSupplierList() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        when(service.getAllSuppliers()).thenReturn(Collections.singletonList(supplier));

        // When
        ResponseEntity<List<Supplier>> response = controller.getAll();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).getAllSuppliers();
    }

    @Test
    void givenSupplier_whenCreate_thenReturnCreatedSupplier() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        when(service.createSupplier(any(Supplier.class))).thenReturn(supplier);

        // When
        ResponseEntity<Supplier> response = controller.create(supplier);

        // Then
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(service, times(1)).createSupplier(any(Supplier.class));
    }
}
