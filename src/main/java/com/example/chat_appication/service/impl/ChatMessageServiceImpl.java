package com.example.chat_appication.service.impl;

import com.example.chat_appication.domain.ChatMessage;
import com.example.chat_appication.domain.Conversation;
import com.example.chat_appication.domain.request.ChatMessageRequest;
import com.example.chat_appication.domain.response.ChatMessageResponse;
import com.example.chat_appication.map.ChatMessageMapper;
import com.example.chat_appication.repository.ChatMessageRepository;
import com.example.chat_appication.repository.ConversationRepository;
import com.example.chat_appication.repository.UserRepository;
import com.example.chat_appication.service.IChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements IChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    @Override
        public ChatMessageResponse createMessage(ChatMessageRequest request,
                String sendToUserId,
                String conversationId) {
        Optional<Conversation> conversationById = conversationRepository.findById(conversationId);
        if(conversationById.isEmpty()) {

        }
        return null;
    }

    @Override
    public List<ChatMessageResponse> getListMessageByConversationId(
            String conversationId, String username
    ) {
        List<ChatMessage> chatMessages = chatMessageRepository
                .findAllBySenderToConversationId(
                        conversationId
                );
        return chatMessages
                .stream()
                .map(chatMessage -> {
                    return ChatMessageMapper.mappertoChatMessageResponse(
                            chatMessage,
                            username
                    );
                })
                .collect(Collectors.toList());
    }
}
