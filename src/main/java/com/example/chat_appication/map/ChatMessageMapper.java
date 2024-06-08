package com.example.chat_appication.map;

import com.example.chat_appication.domain.ChatMessage;
import com.example.chat_appication.domain.response.ChatMessageResponse;

public class ChatMessageMapper {

    public static ChatMessageResponse mappertoChatMessageResponse(
            ChatMessage chatMessage,
            String username
    ) {
        return ChatMessageResponse.builder()
                .id(chatMessage.getId())
                .content(chatMessage.getContent())
                .conversationName(chatMessage.getSenderToConversation().getConversationName(username))
                .userSenderFullName(chatMessage.getUserSender().getFullName())
                .usernameSender(username)
                .conversationId(chatMessage.getSenderToConversation().getId())
                .messageType(
                        chatMessage.getUserSender()
                                .getUsername()
                                .compareTo(username) != 0
                        ? ChatMessageResponse.MessageType.RECEIVE
                        : ChatMessageResponse.MessageType.SEND
                )
                .build();
    }
}
