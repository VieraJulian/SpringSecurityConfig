package com.curso.springsecurity.service;
import com.curso.springsecurity.model.Permission;
import java.util.List;
import java.util.Optional;

public interface IPermissionService {

    List<Permission> findAll();
    Optional<Permission> findById(Long id);
    Permission save(Permission permission);
    void deleteById(Long id);
    Permission update(Permission permission);
}
