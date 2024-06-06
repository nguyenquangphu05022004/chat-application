package com.example.chat_appication.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class UserResponse {
    private String id;
    private String username;
    private String fullName;
    private boolean active;
    private List<ConversationResponse> conversationResponses = new ArrayList<>();
}
