package com.example.springRedis.service;

import com.example.springRedis.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
// 메시지 리스너 구현한 서비스 클래스
public class RedisSubService implements MessageListener {
    // 레디스는 메시지 저장 x -> 리스트에 추가하고 출력하는 형태. 추후 db 연결로 바꾸는 것 고려해보기
    public static List<String> messageList = new ArrayList<>();
    private final ObjectMapper mapper = new ObjectMapper();

    // 메시지 subscribe 했을 때 수행할 메서드
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // ObjectMapper 사용해 json 파싱해서 자바 객체(ChatMessage.class)로 변환
            ChatMessage chatMessage = mapper.readValue(message.getBody(), ChatMessage.class);
            messageList.add(message.toString());

            System.out.println("받은 메시지 : " + message.toString());
            System.out.println("chatMessage.getSender() : " + chatMessage.getSender());
            System.out.println("chatMessage.getContext() : " + chatMessage.getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
