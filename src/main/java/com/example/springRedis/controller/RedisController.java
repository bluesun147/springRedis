package com.example.springRedis.controller;


import com.example.springRedis.ChatMessage;
import com.example.springRedis.service.RedisPubService;
import com.example.springRedis.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;
    private final RedisPubService redisPubService;

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

    // 보내려는 메시지를 pubservice를 통해 publish하면
    // SubService가 해당 채널 구독하고 있기 때문에 채팅 내용 출력됨
    @PostMapping("api/chat")
    public String pubSub(@RequestBody ChatMessage chatMessage) {
        //메시지 보내기
        redisPubService.sendMessage(chatMessage);

        return "success";
    }

    // Session Storage
    // Redis를 통해 HttpSession을 관리하기
    // HttpSession 객체를 사용해서 값을 저장해보고 Redis에 실제로 저장되었는지 확인
    // session의 ID를 return해 동일한 Session인지 확인
    @GetMapping("api/session")
    public String getSessionId(HttpSession session) {
        session.setAttribute("name" , "treesick");
        return session.getId();
    }
}