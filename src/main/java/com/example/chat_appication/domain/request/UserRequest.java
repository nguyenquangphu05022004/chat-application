package com.example.chat_appication.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String fullName;
    private String username;
    private String password;
}
