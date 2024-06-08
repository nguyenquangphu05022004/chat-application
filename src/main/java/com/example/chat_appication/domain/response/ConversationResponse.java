package com.example.chat_appication.domain.response;

import com.example.chat_appication.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@ToString
public class ConversationResponse {
    private String id;
    private String conversationName;
    private List<ChatMessageResponse> chatMessageResponses = new ArrayList<>();
}
