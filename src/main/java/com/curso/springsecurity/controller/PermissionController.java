package com.curso.springsecurity.controller;

import com.curso.springsecurity.model.Permission;
import com.curso.springsecurity.repository.IPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private IPermissionRepository permissionRepository;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Permission>> getAllPermissions(){
        List<Permission> permissions = permissionRepository.findAll();

        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Permission> getPermissionsById(@PathVariable Long id){
        Optional<Permission> permission = permissionRepository.findById(id);

        return permission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission){
        return ResponseEntity.ok(permissionRepository.save(permission));
    }
}


