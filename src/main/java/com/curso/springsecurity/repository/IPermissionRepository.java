package com.curso.springsecurity.repository;

import com.curso.springsecurity.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPermissionRepository extends JpaRepository<Permission, Long> {
}
