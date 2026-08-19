package org.mefobululu.arenahub.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class AiService {
    private final ChatModel chatModel;

    public AiService(ChatModel chatModel){
        this.chatModel=chatModel;
    }

    public String chat(String message){
        return chatModel.call(message);
    }


}
