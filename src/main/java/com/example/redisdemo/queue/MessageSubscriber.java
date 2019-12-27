package com.example.redisdemo.queue;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageSubscriber implements MessageListener {

    public static List<String> messages = new ArrayList<>();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        messages.add(message.toString());
        System.out.println("Message received: " + new String(message.getBody()));
    }
}
