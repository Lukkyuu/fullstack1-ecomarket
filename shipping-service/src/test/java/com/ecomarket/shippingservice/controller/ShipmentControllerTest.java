package com.ecomarket.shippingservice.controller;

import com.ecomarket.shippingservice.entity.Shipment;
import com.ecomarket.shippingservice.service.ShipmentService;
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
    private ShipmentService service;

    @InjectMocks
    private ShipmentController controller;

    @Test
    void givenShipments_whenGetAll_thenReturnShipmentList() {
        // Given
        Shipment shipment = new Shipment();
        shipment.setId(1L);
        when(service.getAllShipments()).thenReturn(Collections.singletonList(shipment));

        // When
        ResponseEntity<List<Shipment>> response = controller.getAll();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).getAllShipments();
    }

    @Test
    void givenShipment_whenCreate_thenReturnCreatedShipment() {
        // Given
        Shipment shipment = new Shipment();
        shipment.setId(1L);
        shipment.setTrackingCode("TRK-TEST");
        when(service.createShipment(any(Shipment.class))).thenReturn(shipment);

        // When
        ResponseEntity<Shipment> response = controller.create(shipment);

        // Then
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("TRK-TEST", response.getBody().getTrackingCode());
        verify(service, times(1)).createShipment(any(Shipment.class));
    }
}
