package com.ecomarket.billingservice.service;

import com.ecomarket.billingservice.entity.Invoice;
import com.ecomarket.billingservice.repository.InvoiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock
    private InvoiceRepository repository;

    @InjectMocks
    private InvoiceService service;

    @Test
    void givenInvoices_whenGetAllInvoices_thenReturnInvoiceList() {
        // Given
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        when(repository.findAll()).thenReturn(Collections.singletonList(invoice));

        // When
        List<Invoice> result = service.getAllInvoices();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void givenRequest_whenCreateInvoice_thenReturnSavedInvoice() {
        // Given
        Map<String, Object> req = new HashMap<>();
        req.put("orderId", 1L);
        req.put("totalAmount", 1000.0);

        when(repository.save(any(Invoice.class))).thenAnswer(i -> {
            Invoice inv = i.getArgument(0);
            inv.setId(100L);
            return inv;
        });

        // When
        Invoice result = service.createInvoice(req);

        // Then
        assertNotNull(result);
        assertEquals(100L, result.getId());
        assertEquals(1L, result.getOrderId());
        assertNotNull(result.getTaxAmount());
        assertTrue(result.getInvoiceNumber().startsWith("FAC-"));
        verify(repository, times(1)).save(any(Invoice.class));
    }
}
