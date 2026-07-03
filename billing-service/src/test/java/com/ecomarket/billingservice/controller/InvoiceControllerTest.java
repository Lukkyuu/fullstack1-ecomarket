package com.ecomarket.billingservice.controller;

import com.ecomarket.billingservice.entity.Invoice;
import com.ecomarket.billingservice.service.InvoiceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceControllerTest {

    @Mock
    private InvoiceService service;

    @InjectMocks
    private InvoiceController controller;

    @Test
    void givenInvoices_whenGetAll_thenReturnInvoiceList() {
        // Given
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        when(service.getAllInvoices()).thenReturn(Collections.singletonList(invoice));

        // When
        ResponseEntity<List<Invoice>> response = controller.getAll();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).getAllInvoices();
    }

    @Test
    void givenRequest_whenCreate_thenReturnCreatedInvoice() {
        // Given
        Map<String, Object> req = new HashMap<>();
        req.put("orderId", 1L);
        req.put("totalAmount", 1000.0);

        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setOrderId(1L);
        when(service.createInvoice(req)).thenReturn(invoice);

        // When
        ResponseEntity<Invoice> response = controller.create(req);

        // Then
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(service, times(1)).createInvoice(req);
    }
}
