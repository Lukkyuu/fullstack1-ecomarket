package com.ecomarket.authservice.controller;
import com.ecomarket.authservice.entity.RolePermission;
import com.ecomarket.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor @Slf4j
@Tag(name = "Authentication API", description = "Endpoints para gestionar la autenticaciÃ³n y permisos")
public class AuthController {
    private final AuthService service;

    @Operation(summary = "Obtener permisos por rol", description = "Devuelve una lista de permisos asignados a un rol especÃ­fico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OperaciÃ³n exitosa, devuelve lista de permisos"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado o sin permisos")
    })
    @GetMapping("/permissions/{roleName}")
    public ResponseEntity<List<String>> getPermissionsByRole(
            @Parameter(description = "Nombre del rol a consultar", required = true) @PathVariable String roleName) {
        return ResponseEntity.ok(service.getPermissionsByRole(roleName));
    }
}
