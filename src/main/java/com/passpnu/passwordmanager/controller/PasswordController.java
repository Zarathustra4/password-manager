package com.passpnu.passwordmanager.controller;

import com.passpnu.passwordmanager.entity.Password;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/password")
public class PasswordController {
    @GetMapping
    public static Password getPass(){
        return new Password(1L, 1L, "Pass", 1L);
    }

    @GetMapping("/generate")
    public static String generatePassword(){
        Random random = new Random();
        int length = 10;
        int asciiCode;
        StringBuilder password = new StringBuilder();
        for(int i = 0; i < 10; i++){
            asciiCode = random.nextInt(126) + 33;
            password.append((char) asciiCode);
        }
        return password.toString();
    }
}
