package com.example.redisdemo.queue;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessagePublisherImpl implements MessagePublisher {

    private RedisTemplate<String, Object> redisTemplate;
    private ChannelTopic topic;


    @Override
    public void publish(String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
