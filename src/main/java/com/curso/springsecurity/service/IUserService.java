package com.curso.springsecurity.service;

import com.curso.springsecurity.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserEntity> findAll();
    Optional<UserEntity> findById(Long id);
    UserEntity save(UserEntity user);
    void deleteById(Long id);
    UserEntity update(UserEntity user);
    //public String encriptPassword(String password);
}
