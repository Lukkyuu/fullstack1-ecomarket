package com.ecomarket.shippingservice.service;

import com.ecomarket.shippingservice.entity.Shipment;
import com.ecomarket.shippingservice.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository repository;

    public List<Shipment> getAllShipments() {
        return repository.findAll();
    }

    public Shipment createShipment(Shipment s) {
        s.setTrackingCode("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        s.setStatus("PENDING");
        return repository.save(s);
    }
}
