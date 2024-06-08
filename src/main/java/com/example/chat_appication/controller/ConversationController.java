package com.example.chat_appication.controller;

import com.example.chat_appication.domain.response.ConversationResponse;
import com.example.chat_appication.service.IConversationService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ConversationController {
    private final IConversationService conversationService;

    @GetMapping("/conversations")
    @ResponseBody
    public ConversationResponse createConversation(
            @RequestParam("username")List<String> usernames
            ) {
        return conversationService.createConversation(usernames);
    }
}
