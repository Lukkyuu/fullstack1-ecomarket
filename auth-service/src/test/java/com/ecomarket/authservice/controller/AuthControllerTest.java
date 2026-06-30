package com.ecomarket.authservice.controller;

import com.ecomarket.authservice.entity.RolePermission;
import com.ecomarket.authservice.repository.RolePermissionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private RolePermissionRepository repository;

    @InjectMocks
    private AuthController authController;

    @Test
    void getPermissionsByRole_ReturnsListOfPermissions() {
        // Given
        RolePermission perm1 = new RolePermission();
        perm1.setPermissionKey("READ_USER");
        RolePermission perm2 = new RolePermission();
        perm2.setPermissionKey("WRITE_USER");
        
        when(repository.findByRoleName("ADMIN")).thenReturn(Arrays.asList(perm1, perm2));

        // When
        ResponseEntity<List<String>> response = authController.getPermissionsByRole("ADMIN");

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("READ_USER", response.getBody().get(0));
        assertEquals("WRITE_USER", response.getBody().get(1));
    }
    
    @Test
    void getPermissionsByRole_ReturnsEmpty_WhenNoRole() {
        // Given
        when(repository.findByRoleName(anyString())).thenReturn(Arrays.asList());

        // When
        ResponseEntity<List<String>> response = authController.getPermissionsByRole("UNKNOWN");

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(0, response.getBody().size());
    }
}
