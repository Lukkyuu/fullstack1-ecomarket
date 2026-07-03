package com.ecomarket.userservice.controller;

import com.ecomarket.userservice.dto.UserDTO;
import com.ecomarket.userservice.service.UserService;
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
class UserControllerTest {

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    @Test
    void givenUsers_whenGetAll_thenReturnUserDTOList() {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        when(service.getAllUsers()).thenReturn(Collections.singletonList(userDTO));

        // When
        ResponseEntity<List<UserDTO>> response = controller.getAll();

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).getAllUsers();
    }

    @Test
    void givenUserId_whenGetById_thenReturnUserDTO() {
        // Given
        Long id = 1L;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        when(service.getUserById(id)).thenReturn(userDTO);

        // When
        ResponseEntity<UserDTO> response = controller.getById(id);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
        verify(service, times(1)).getUserById(id);
    }

    @Test
    void givenUserDTO_whenCreate_thenReturnCreatedUserDTO() {
        // Given
        UserDTO inputDTO = new UserDTO();
        inputDTO.setName("testuser");
        inputDTO.setEmail("test@test.com");
        
        UserDTO savedDTO = new UserDTO();
        savedDTO.setId(1L);
        savedDTO.setName("testuser");
        savedDTO.setEmail("test@test.com");
        when(service.createUser(any(UserDTO.class))).thenReturn(savedDTO);

        // When
        ResponseEntity<UserDTO> response = controller.create(inputDTO);

        // Then
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(service, times(1)).createUser(any(UserDTO.class));
    }
    
    @Test
    void givenUserId_whenDelete_thenReturnNoContent() {
        // Given
        Long id = 1L;
        doNothing().when(service).deleteUser(id);

        // When
        ResponseEntity<Void> response = controller.delete(id);

        // Then
        assertEquals(204, response.getStatusCode().value());
        verify(service, times(1)).deleteUser(id);
    }
}
