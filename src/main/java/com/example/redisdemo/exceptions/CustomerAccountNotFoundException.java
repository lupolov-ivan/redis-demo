package com.example.redisdemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such a customer")
public class CustomerAccountNotFoundException extends RuntimeException {
}
