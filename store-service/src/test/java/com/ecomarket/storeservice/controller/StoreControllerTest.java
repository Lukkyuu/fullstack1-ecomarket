package com.ecomarket.storeservice.controller;

import com.ecomarket.storeservice.entity.Store;
import com.ecomarket.storeservice.service.StoreService;
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
class StoreControllerTest {

    @Mock
    private StoreService service;

    @InjectMocks
    private StoreController controller;

    @Test
    void givenStores_whenGetAll_thenReturnStoreList() {
        // Given
        Store store = new Store();
        store.setId(1L);
        when(service.getAllStores()).thenReturn(Collections.singletonList(store));

        // When
        ResponseEntity<List<Store>> response = controller.getAll();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).getAllStores();
    }

    @Test
    void givenStore_whenCreate_thenReturnCreatedStore() {
        // Given
        Store store = new Store();
        store.setId(1L);
        when(service.createStore(any(Store.class))).thenReturn(store);

        // When
        ResponseEntity<Store> response = controller.create(store);

        // Then
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(service, times(1)).createStore(any(Store.class));
    }
}
