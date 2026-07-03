package com.ecomarket.storeservice.service;

import com.ecomarket.storeservice.entity.Store;
import com.ecomarket.storeservice.repository.StoreRepository;
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
class StoreServiceTest {

    @Mock
    private StoreRepository repository;

    @InjectMocks
    private StoreService service;

    @Test
    void givenStores_whenGetAllStores_thenReturnStoreList() {
        // Given
        Store store = new Store();
        store.setId(1L);
        when(repository.findAll()).thenReturn(Collections.singletonList(store));

        // When
        List<Store> result = service.getAllStores();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void givenStore_whenCreateStore_thenReturnSavedStore() {
        // Given
        Store store = new Store();
        store.setId(1L);
        when(repository.save(any(Store.class))).thenReturn(store);

        // When
        Store result = service.createStore(store);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).save(any(Store.class));
    }
}
