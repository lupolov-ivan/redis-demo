package com.example.redisdemo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash
public class CustomerAccount implements Serializable {

    private String id;
    private String firstName;
    private String lastName;
    private Integer age;
}
