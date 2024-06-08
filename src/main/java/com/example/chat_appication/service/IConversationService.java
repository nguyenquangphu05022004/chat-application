package com.example.chat_appication.service;

import com.example.chat_appication.domain.Conversation;
import com.example.chat_appication.domain.response.ConversationResponse;

import java.util.List;

public interface IConversationService {

    ConversationResponse createConversation(List<String> usernames);

}
