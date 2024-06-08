package com.example.chat_appication.service.impl;

import com.example.chat_appication.domain.Conversation;
import com.example.chat_appication.domain.User;
import com.example.chat_appication.domain.response.ConversationResponse;
import com.example.chat_appication.map.ConversationMapper;
import com.example.chat_appication.repository.ConversationRepository;
import com.example.chat_appication.repository.UserRepository;
import com.example.chat_appication.service.IConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements IConversationService {
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    @Override
    public ConversationResponse createConversation(List<String> usernames) {
        List<User> users = usernames
                .stream()
                .map(username -> userRepository.findByUsername(username).get())
                .collect(Collectors.toList());
        Conversation conversation = Conversation.builder()
                .id("4")
                .users(users)
                .build();
        return ConversationMapper.mapperToConversationResponse(
                conversationRepository.save(conversation)
        );
    }
}
