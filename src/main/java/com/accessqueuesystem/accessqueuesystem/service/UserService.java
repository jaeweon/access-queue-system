package com.accessqueuesystem.accessqueuesystem.service;

import com.accessqueuesystem.accessqueuesystem.dto.RegisterRequest;
import com.accessqueuesystem.accessqueuesystem.entity.UserEntity;


public interface UserService {
    void registerUser(RegisterRequest request);
    UserEntity findByUsername(String username);
}