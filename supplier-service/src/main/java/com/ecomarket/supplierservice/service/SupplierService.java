package com.ecomarket.supplierservice.service;

import com.ecomarket.supplierservice.entity.Supplier;
import com.ecomarket.supplierservice.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository repository;

    public List<Supplier> getAllSuppliers() {
        return repository.findAll();
    }

    public Supplier createSupplier(Supplier s) {
        return repository.save(s);
    }
}
