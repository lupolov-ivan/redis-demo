package com.example.redisdemo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash
public class CustomerAccount implements Serializable {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private Integer age;

    public CustomerAccount(String firstName, String lastName, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
