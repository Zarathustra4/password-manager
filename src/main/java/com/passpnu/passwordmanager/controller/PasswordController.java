package com.passpnu.passwordmanager.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

@RestController
@RequestMapping("/passwords")
public class PasswordController {

    @GetMapping("/generate")
    public String generatePassword(){
        SecureRandom random = new SecureRandom();
        int length = random.nextInt(14) + 8;
        int asciiCode;
        StringBuilder password = new StringBuilder();
        for(int i = 0; i < length; i++){
            asciiCode = random.nextInt(126) + 33;
            password.append((char) asciiCode);
        }
        return password.toString();
    }
}