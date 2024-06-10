package com.curso.springsecurity.controller;

import com.curso.springsecurity.model.Permission;
import com.curso.springsecurity.model.Role;
import com.curso.springsecurity.service.IPermissionService;
import com.curso.springsecurity.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Set<Permission> permissions = new HashSet<>();
        Permission readPermission;

        for (Permission per : role.getPermissionList()) {
            readPermission = permissionService.findById(per.getId()).orElse(null);

            if (readPermission != null) {
                permissions.add(readPermission);
            }
        }

        role.setPermissionList(permissions);
        Role newRole = roleService.save(role);
        return ResponseEntity.ok(newRole);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> updateRole(@RequestBody Role role, @PathVariable Long id) {
        Role roleFound = roleService.findById(id).orElse(null);

        if (roleFound == null) {
            return ResponseEntity.notFound().build();
        }

        Set<Permission> permissions = new HashSet<>();
        Permission readPermission;

        for (Permission per : role.getPermissionList()) {
            readPermission = permissionService.findById(per.getId()).orElse(null);

            if (readPermission != null) {
                permissions.add(readPermission);
            }
        }

        roleFound.setPermissionList(permissions);
        Role roleUpdated = roleService.save(roleFound);
        return ResponseEntity.ok(roleUpdated);
    }

}
