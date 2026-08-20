package org.mefobululu.arenahub.service;

import org.mefobululu.arenahub.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiService {
    private final ChatModel chatModel;
    private static final Logger log = LoggerFactory.getLogger(AiService.class);

    public AiService(ChatModel chatModel){
        this.chatModel=chatModel;
    }

    public String chat(String message){

        SystemMessage systemMessage = new SystemMessage("你是 ArenaHub AI 游戏助手。");
        UserMessage userMessage = new UserMessage(message);
        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .temperature(0.7)
                .build();
        Prompt prompt = new Prompt(List.of(systemMessage,userMessage),options);

        ChatResponse chatResponse;
        try{
            chatResponse = chatModel.call(prompt);
        }catch (RuntimeException e){
            log.error("LLM call failed",e);
            throw new BusinessException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Ai 服务暂时不可用"
            );
        }

        Usage usage = chatResponse.getMetadata().getUsage();

        Integer promptTokens = usage.getPromptTokens();
        Integer completionTokens = usage.getCompletionTokens();
        Integer totalTokens = usage.getTotalTokens();

        log.info(
                "LLM call completed, promptTokens={}, completionTokens={}, totalTokens={}",
                promptTokens,
                completionTokens,
                totalTokens
        );

        return chatResponse.getResult().getOutput().getText();
    }


}
