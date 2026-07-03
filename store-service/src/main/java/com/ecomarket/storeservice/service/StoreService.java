package com.ecomarket.storeservice.service;

import com.ecomarket.storeservice.entity.Store;
import com.ecomarket.storeservice.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository repository;

    public List<Store> getAllStores() {
        return repository.findAll();
    }

    public Store createStore(Store store) {
        return repository.save(store);
    }
}
