package com.example.chat_appication.service;

import com.example.chat_appication.domain.request.UserRequest;
import com.example.chat_appication.domain.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    void saveUser(UserRequest userRequest);
    UserResponse findById(String id);
}
