package com.example.springRedis.controller;


import com.example.springRedis.ChatMessage;
import com.example.springRedis.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @PostMapping("/api/redisStringTest")
    public String sendString(@RequestBody ChatMessage chatMessage) {
        redisService.setRedisStringValue(chatMessage);

        redisService.getRedisStringValue("sender");
        redisService.getRedisStringValue("context");

        return "success";
    }

    // get sender1
    // "{\"sender\":\"sender1\",\"context\":\"context1\"}"
    @PostMapping("/api/redisTest")
    public String send(@RequestBody ChatMessage chatMessage) throws JsonProcessingException {
        redisService.setRedisValue(chatMessage);

        String key = chatMessage.getSender();
        ChatMessage chatMessage1 = redisService.getRedisValue(key, ChatMessage.class);

        return chatMessage1.getContext();
    }
}