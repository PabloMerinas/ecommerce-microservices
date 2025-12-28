package com.ecommerce.users_service.service.impl;

import org.springframework.stereotype.Service;

import com.ecommerce.users_service.entity.UserEntity;
import com.ecommerce.users_service.repository.UserRepository;
import com.ecommerce.users_service.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity createUser(String email, String name) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("User with email already exists");
        }

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setName(name);

        return userRepository.save(user);    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }

    @Override
    public UserEntity updateUser(Long id, String name) {
        UserEntity user = getUserById(id);
        user.setName(name);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = getUserById(id);
        userRepository.delete(user);
    }


    
}
