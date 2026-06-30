package com.ecomarket.supplierservice.controller;
import com.ecomarket.supplierservice.repository.*;

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
    private SupplierRepository repository;

    @InjectMocks
    private SupplierController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }
}


