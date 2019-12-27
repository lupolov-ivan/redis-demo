package com.example.redisdemo.model;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash
public class CustomerAccount {

    private String id;
    private String firstName;
    private String lastName;
    private Integer age;
}
