package com.example.chat_appication.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequest {
    private String content;
    private String userSenderId;
    private String senderToConversationId;
}
