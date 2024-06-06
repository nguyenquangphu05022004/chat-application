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
    private MessageType messageType;
    public static enum MessageType {
        RECEIVE,
        SEND
    }
}
