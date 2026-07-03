package com.ecomarket.supplierservice.service;

import com.ecomarket.supplierservice.entity.Supplier;
import com.ecomarket.supplierservice.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    @Mock
    private SupplierRepository repository;

    @InjectMocks
    private SupplierService service;

    @Test
    void givenSuppliers_whenGetAllSuppliers_thenReturnSupplierList() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        when(repository.findAll()).thenReturn(Collections.singletonList(supplier));

        // When
        List<Supplier> result = service.getAllSuppliers();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void givenSupplier_whenCreateSupplier_thenReturnSavedSupplier() {
        // Given
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        when(repository.save(any(Supplier.class))).thenReturn(supplier);

        // When
        Supplier result = service.createSupplier(supplier);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(Supplier.class));
    }
}
