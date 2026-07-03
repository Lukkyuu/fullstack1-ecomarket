package com.ecomarket.authservice.controller;

import com.ecomarket.authservice.service.AuthService;
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
class AuthControllerTest {

    @Mock
    private AuthService service;

    @InjectMocks
    private AuthController controller;

    @Test
    void givenRoleName_whenGetPermissionsByRole_thenReturnPermissionsList() {
        // Given
        String roleName = "ADMIN";
        when(service.getPermissionsByRole(roleName)).thenReturn(Collections.singletonList("READ_ALL"));

        // When
        ResponseEntity<List<String>> response = controller.getPermissionsByRole(roleName);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("READ_ALL", response.getBody().get(0));
        verify(service, times(1)).getPermissionsByRole(roleName);
    }
}
