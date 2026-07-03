package com.ecomarket.authservice.service;

import com.ecomarket.authservice.entity.RolePermission;
import com.ecomarket.authservice.repository.RolePermissionRepository;
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
class AuthServiceTest {

    @Mock
    private RolePermissionRepository repository;

    @InjectMocks
    private AuthService service;

    @Test
    void givenRoleName_whenGetPermissionsByRole_thenReturnPermissionKeys() {
        // Given
        String roleName = "ADMIN";
        RolePermission rp = new RolePermission();
        rp.setRoleName(roleName);
        rp.setPermissionKey("READ_ALL");
        when(repository.findByRoleName(roleName)).thenReturn(Collections.singletonList(rp));

        // When
        List<String> result = service.getPermissionsByRole(roleName);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("READ_ALL", result.get(0));
        verify(repository, times(1)).findByRoleName(roleName);
    }
}
