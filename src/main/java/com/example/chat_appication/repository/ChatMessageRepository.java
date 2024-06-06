package com.example.chat_appication.repository;

import com.example.chat_appication.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {
    List<ChatMessage> findAllBySenderToConversationId(String conversationId);
}
