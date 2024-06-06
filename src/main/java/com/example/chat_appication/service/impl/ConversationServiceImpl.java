package com.example.chat_appication.service.impl;

import com.example.chat_appication.domain.Conversation;
import com.example.chat_appication.repository.ConversationRepository;
import com.example.chat_appication.service.IConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements IConversationService {
    private final ConversationRepository conversationRepository;
    @Override
    public Conversation createConversation(String conversationId) {
        Optional<Conversation> byId = conversationRepository
                .findById(conversationId);
        if(byId.isEmpty()) {

        }
        return byId.get();
    }
}
