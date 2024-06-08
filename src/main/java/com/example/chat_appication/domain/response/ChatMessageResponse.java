package com.example.chat_appication.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ChatMessageResponse {
    private String id;
    private String content;
    private String userSenderFullName;
    private String usernameSender;
    private String conversationId;
    private String conversationName;
    private MessageType messageType;
    public static enum MessageType {
        RECEIVE,
        SEND
    }
}
