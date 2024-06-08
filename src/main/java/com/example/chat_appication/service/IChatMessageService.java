package com.example.chat_appication.service;

import com.example.chat_appication.domain.request.ChatMessageRequest;
import com.example.chat_appication.domain.response.ChatMessageResponse;

import java.util.List;

public interface IChatMessageService {

    ChatMessageResponse createMessage(ChatMessageRequest request);

    List<ChatMessageResponse> getListMessageByConversationId(String conversationId, String username);
}
