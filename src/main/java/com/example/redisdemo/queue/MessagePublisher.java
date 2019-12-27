package com.example.redisdemo.queue;

public interface MessagePublisher {
    void publish(final String message);
}
