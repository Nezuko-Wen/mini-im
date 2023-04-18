package com.wen.open.miniim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Wen
 * @date 2023/4/7 14:54
 */
@SpringBootApplication
public class StartApplication {
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(StartApplication.class, args);
    }

    public static void close() {
        context.close();
    }
}
