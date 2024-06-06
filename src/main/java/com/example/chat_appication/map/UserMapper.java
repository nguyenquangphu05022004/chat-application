package com.example.chat_appication.map;


import com.example.chat_appication.domain.User;
import com.example.chat_appication.domain.response.UserResponse;

import java.util.stream.Collectors;

public class UserMapper {

    public static UserResponse maptoUserResponse(User user) {
        UserResponse userResponse = UserResponse
                .builder()
                .id(user.getId())
                .active(user.isActive())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .conversationResponses(
                        user.getConversations()
                                .stream()
                                .map(conversation -> {
                                    return ConversationMapper
                                            .mapperToConversationResponse(
                                                    conversation, user.getUsername()
                                            );
                                })
                                .collect(Collectors.toList())
                )
                .build();
        return userResponse;
    }

}
