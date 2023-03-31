package com.passpnu.passwordmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PasswordManagerApplication {
    //TODO: DB cleans up after each run. FIX IT!
    public static void main(String[] args) {
        SpringApplication.run(PasswordManagerApplication.class, args);
    }

}
