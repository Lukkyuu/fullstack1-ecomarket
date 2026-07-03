package com.ecomarket.userservice.controller;
import com.ecomarket.userservice.dto.UserDTO;
import com.ecomarket.userservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
@Tag(name = "User", description = "Endpoints para la gestion de User")
@RestController @RequestMapping("/api/users") @RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    
    @Operation(summary = "Obtener todos los registros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping 
    public ResponseEntity<List<UserDTO>> getAll() { 
        return ResponseEntity.ok(userService.getAllUsers()); 
    }
    
    @Operation(summary = "Obtener registro por ID u otro criterio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}") 
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) { 
        return ResponseEntity.ok(userService.getUserById(id)); 
    }
    
    @Operation(summary = "Crear nuevo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping 
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO dto) { 
        return ResponseEntity.status(201).body(userService.createUser(dto)); 
    }
    
    @Operation(summary = "Eliminar registro por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Registro eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> delete(@PathVariable Long id) { 
        userService.deleteUser(id); 
        return ResponseEntity.noContent().build(); 
    }
}
