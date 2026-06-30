package com.ecomarket.shippingservice.controller;
import com.ecomarket.shippingservice.repository.*;

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
class ShipmentControllerTest {

    @Mock
    private ShipmentRepository repository;

    @InjectMocks
    private ShipmentController controller;

    @Test
    void contextLoads() {
        assertNotNull(controller);
    }
}


