package com.example.chat_appication.repository;

import com.example.chat_appication.domain.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ConversationRepository extends JpaRepository<Conversation, String> {
}
