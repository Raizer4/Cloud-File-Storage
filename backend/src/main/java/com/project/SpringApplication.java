package com.project;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(SpringApplication.class, args);
    }

}
