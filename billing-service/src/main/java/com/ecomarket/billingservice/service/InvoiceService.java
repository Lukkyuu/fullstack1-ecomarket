package com.ecomarket.billingservice.service;

import com.ecomarket.billingservice.entity.Invoice;
import com.ecomarket.billingservice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository repository;

    public List<Invoice> getAllInvoices() {
        return repository.findAll();
    }

    public Invoice createInvoice(Map<String, Object> req) {
        Long orderId = Long.valueOf(req.get("orderId").toString());
        BigDecimal total = new BigDecimal(req.get("totalAmount").toString());
        BigDecimal tax = total.multiply(BigDecimal.valueOf(0.19)); // 19% IVA en Chile
        
        Invoice invoice = Invoice.builder()
            .orderId(orderId)
            .invoiceNumber("FAC-" + System.currentTimeMillis() + "-" + new Random().nextInt(100))
            .taxAmount(tax)
            .totalAmount(total)
            .issuedDate(LocalDateTime.now())
            .build();
            
        return repository.save(invoice);
    }
}
