package com.example.springRedis;

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
    private final RedisTemplate<String, Object> redisTemplate;

    // StringRedisTemplate extends RedisTemplate<String, String>
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
}