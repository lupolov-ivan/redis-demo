package com.example.redisdemo;

import org.testcontainers.containers.GenericContainer;

public class AbstractIntegrationTest {
    static {
        GenericContainer redis = new GenericContainer<>("redis")
                .withExposedPorts(6379);
        redis.start();

        System.setProperty("spring.redis.hostname", redis.getContainerIpAddress());
        System.setProperty("spring.redis.port", redis.getFirstMappedPort() + "");
    }
}
