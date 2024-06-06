package com.example.chat_appication.controller;

import com.example.chat_appication.domain.ChatMessage;
import com.example.chat_appication.domain.request.ChatMessageRequest;
import com.example.chat_appication.domain.response.ChatMessageResponse;
import com.example.chat_appication.service.IChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final IChatMessageService chatMessageService;

    @MessageMapping("/chat.sendMessage/{userId}/{conversationId}")
    @SendTo("/topic/public/conversation/{conversationId}")
    public ChatMessageResponse sendMessage(
            @Payload ChatMessageRequest chatMessage,
            @DestinationVariable("sendToUser") String sendToUserId,
            @DestinationVariable("conversationId") String conversationId
    ) {
        chatMessageService.createMessage(chatMessage, sendToUserId, conversationId);
        return null;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        return chatMessage;
    }

    @GetMapping("/message/user/{username}/conversation/{conversationId}")
    @ResponseBody
    public List<ChatMessageResponse> getListMessageByConversationId(
            @PathVariable("username") String username,
            @PathVariable("conversationId") String conversationId
    ) {
       return chatMessageService.getListMessageByConversationId(conversationId, username);
    }


}
