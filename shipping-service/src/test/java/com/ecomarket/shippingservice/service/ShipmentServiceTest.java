package com.ecomarket.shippingservice.service;

import com.ecomarket.shippingservice.entity.Shipment;
import com.ecomarket.shippingservice.repository.ShipmentRepository;
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
class ShipmentServiceTest {

    @Mock
    private ShipmentRepository repository;

    @InjectMocks
    private ShipmentService service;

    @Test
    void givenShipments_whenGetAllShipments_thenReturnShipmentList() {
        // Given
        Shipment shipment = new Shipment();
        shipment.setId(1L);
        when(repository.findAll()).thenReturn(Collections.singletonList(shipment));

        // When
        List<Shipment> result = service.getAllShipments();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void givenShipment_whenCreateShipment_thenReturnSavedShipmentWithTrackingAndPendingStatus() {
        // Given
        Shipment shipment = new Shipment();
        shipment.setOrderId(100L);
        
        when(repository.save(any(Shipment.class))).thenAnswer(i -> {
            Shipment s = i.getArgument(0);
            s.setId(1L);
            return s;
        });

        // When
        Shipment result = service.createShipment(shipment);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("PENDING", result.getStatus());
        assertTrue(result.getTrackingCode().startsWith("TRK-"));
        verify(repository, times(1)).save(any(Shipment.class));
    }
}
