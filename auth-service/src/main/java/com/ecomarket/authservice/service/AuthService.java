package com.ecomarket.authservice.service;

import com.ecomarket.authservice.entity.RolePermission;
import com.ecomarket.authservice.repository.RolePermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final RolePermissionRepository repository;

    public List<String> getPermissionsByRole(String roleName) {
        log.info("Consultando permisos para el rol: {}", roleName);
        return repository.findByRoleName(roleName).stream()
                .map(RolePermission::getPermissionKey)
                .collect(Collectors.toList());
    }
}
