package com.passpnu.passwordmanager.controller;

import com.passpnu.passwordmanager.entity.PasswordEntity;
import com.passpnu.passwordmanager.entity.ServiceEntity;
import com.passpnu.passwordmanager.entity.UserEntity;
import com.passpnu.passwordmanager.repos.PasswordRepository;
import com.passpnu.passwordmanager.repos.ServiceRepository;
import com.passpnu.passwordmanager.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
@RequestMapping("/passwords")
@AllArgsConstructor
public class PasswordController {
    

    @GetMapping("/generate")
    public String generatePassword(){
        SecureRandom random = new SecureRandom();
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
