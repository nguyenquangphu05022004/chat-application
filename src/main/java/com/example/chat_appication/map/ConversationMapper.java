package com.example.chat_appication.map;

import com.example.chat_appication.domain.Conversation;
import com.example.chat_appication.domain.response.ConversationResponse;

import java.util.stream.Collectors;

public class ConversationMapper {


    public static ConversationResponse mapperToConversationResponse(
            Conversation conversation, String username
    ) {
        ConversationResponse conversationResponse =
                ConversationResponse.builder()
                        .id(conversation.getId())
                        .conversationName(conversation.getConversationName(username))
                        .chatMessageResponses(
                                conversation
                                        .getChatMessages()
                                        .stream()
                                        .map(message -> {
                                            return ChatMessageMapper.
                                                    mappertoChatMessageResponse(
                                                            message, username
                                                    );
                                        })
                                        .collect(Collectors.toList())
                        )
                        .build();
        return conversationResponse;
    }
    public static ConversationResponse mapperToConversationResponse(
            Conversation conversation
    ) {
        ConversationResponse conversationResponse =
                ConversationResponse.builder()
                        .id(conversation.getId())
                        .build();
        return conversationResponse;
    }
}
