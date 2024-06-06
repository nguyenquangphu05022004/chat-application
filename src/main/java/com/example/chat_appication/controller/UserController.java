package com.example.chat_appication.controller;

import com.example.chat_appication.domain.User;
import com.example.chat_appication.domain.request.UserRequest;
import com.example.chat_appication.domain.response.UserResponse;
import com.example.chat_appication.map.UserMapper;
import com.example.chat_appication.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final IUserService userService;
    @PostMapping("/users")
    public void createUser(@RequestBody UserRequest userRequest) {
        userService.saveUser(userRequest);
    }

    @MessageMapping("/chat.getListUserConversation/{username}")
    @SendTo("/topic/public/listUserMessage/{username}")
    public UserResponse getListUserConversation(@DestinationVariable("username") String username) {
        UserResponse userResponse = UserMapper.maptoUserResponse((User)userService.loadUserByUsername(username));
        log.info("User: {}", userResponse);
        return userResponse;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/home")
    public String home() {
        return "index";
    }

}
