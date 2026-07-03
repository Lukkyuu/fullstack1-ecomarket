package com.ecomarket.userservice.service;

import com.ecomarket.userservice.dto.UserDTO;
import com.ecomarket.userservice.entity.User;
import com.ecomarket.userservice.exception.ResourceNotFoundException;
import com.ecomarket.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void givenUsers_whenGetAllUsers_thenReturnUserDTOList() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setName("test");
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        // When
        List<UserDTO> result = service.getAllUsers();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test", result.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void givenUserId_whenGetUserById_thenReturnUserDTO() {
        // Given
        Long id = 1L;
        User user = new User();
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // When
        UserDTO result = service.getUserById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(userRepository, times(1)).findById(id);
    }
    
    @Test
    void givenInvalidUserId_whenGetUserById_thenThrowException() {
        // Given
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> service.getUserById(id));
    }

    @Test
    void givenUserDTO_whenCreateUser_thenReturnCreatedUserDTO() {
        // Given
        UserDTO dto = new UserDTO();
        dto.setName("newuser");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("newuser");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        UserDTO result = service.createUser(dto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("newuser", result.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    void givenUserId_whenDeleteUser_thenVerifyDelete() {
        // Given
        Long id = 1L;
        User user = new User();
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        // When
        service.deleteUser(id);

        // Then
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).delete(user);
    }
}
