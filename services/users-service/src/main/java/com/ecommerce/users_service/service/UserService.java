package com.ecommerce.users_service.service;

import org.springframework.stereotype.Service;

import com.ecommerce.users_service.entity.UserEntity;

@Service
public interface UserService {

    UserEntity createUser(String email, String name);

    UserEntity getUserById(Long id);

    UserEntity getUserByEmail(String email);

    UserEntity updateUser(Long id, String name);

    void deleteUser(Long id);
}
