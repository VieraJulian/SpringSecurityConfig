package com.curso.springsecurity.controller;

import com.curso.springsecurity.model.Role;
import com.curso.springsecurity.model.UserEntity;
import com.curso.springsecurity.service.IRoleService;
import com.curso.springsecurity.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        Optional<UserEntity> user = userService.findById(id);

        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        Set<Role> roleList = new HashSet<>();
        Role readRole;

        user.setPassword(userService.encriptPassword(user.getPassword()));

        for (Role role : user.getRolesList()) {
            readRole = roleService.findById(role.getId()).orElse(null);

            if (readRole != null) {
                roleList.add(readRole);
            }
        }

        if (!roleList.isEmpty()){
            user.setRolesList(roleList);
            UserEntity newUser = userService.save(user);

            return ResponseEntity.ok(newUser);
        }

        return null;

    }
}
