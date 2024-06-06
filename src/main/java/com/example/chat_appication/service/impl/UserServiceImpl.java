package com.example.chat_appication.service.impl;

import com.example.chat_appication.domain.User;
import com.example.chat_appication.domain.request.UserRequest;
import com.example.chat_appication.domain.response.UserResponse;
import com.example.chat_appication.map.UserMapper;
import com.example.chat_appication.repository.UserRepository;
import com.example.chat_appication.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    @Override
    public void saveUser(UserRequest userRequest) {

    }

    @Override
    public UserResponse findById(String id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException(
                        "Not Found User have Id: " + id
                )
        );
        return UserMapper.maptoUserResponse(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                                "Not Found User have username: " + username
                        )
                );
        return user;
    }
}
