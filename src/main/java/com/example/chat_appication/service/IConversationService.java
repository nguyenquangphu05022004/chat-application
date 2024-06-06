package com.example.chat_appication.service;

import com.example.chat_appication.domain.Conversation;

public interface IConversationService {

    Conversation createConversation(String conversationId);
}
