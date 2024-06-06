package com.example.chat_appication.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "user_sender_id")
    private User userSender;
    @ManyToOne
    @JoinColumn(name = "sender_conversation")
    private Conversation senderToConversation;
}
