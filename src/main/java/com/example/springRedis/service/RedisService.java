package com.example.springRedis.service;

import com.example.springRedis.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
    // 레디스는 key-value 형태
    // get/set 위한 객체
    private final RedisTemplate<String, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final ObjectMapper objectMapper;

    public void setRedisStringValue(ChatMessage chatMessage) {
        // public interface ValueOperations<Key, Value>
        // key로 sender, value로 입력한 값 저장됨.
        // get sender -> tom
        // get context -> hello
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.set("sender", chatMessage.getSender());
        stringValueOperations.set("context", chatMessage.getContext());
    }

    public void getRedisStringValue(String key) {
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        // key와 value 출력
        System.out.println(key + " : " + stringValueOperations.get(key));
    }

    // 직접 만든 redisTemplate 사용
    // redisTemplate과 stringRedisTemplate는 직렬화에 차이 있음
    // s는 문자열 특화 템플릿 제공
    // r은 자바 객체를 redis에 저장할 때 사용
    public void setRedisValue(ChatMessage chatMessage) throws JsonProcessingException {
        String key = chatMessage.getSender();
        // value에 자바 객체를 String으로 변환해 저장
        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(chatMessage));
    }

    // 저장된 객체 조회
    public <T> T getRedisValue(String key, Class<T> classType) throws JsonProcessingException {

        // value가 String으로 저장되었기 때문에 넘겨준 classType으로 변환
        String redisValue = (String)redisTemplate.opsForValue().get(key);

        return objectMapper.readValue(redisValue, classType);
    }
}