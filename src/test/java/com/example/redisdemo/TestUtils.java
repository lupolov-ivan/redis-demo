package com.example.redisdemo;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

public class TestUtils {

    public static String readJsonFromResource(String path) {
        try {
            File file = ResourceUtils.getFile("classpath:" + path);
            return Files.readString(file.toPath());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
