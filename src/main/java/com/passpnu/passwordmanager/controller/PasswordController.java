package com.passpnu.passwordmanager.controller;

import com.passpnu.passwordmanager.entity.Password;
import com.passpnu.passwordmanager.entity.User;
import com.passpnu.passwordmanager.repos.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/password")
public class PasswordController {
    private final UserRepository userRepository;

    public PasswordController(UserRepository repository) {
        this.userRepository = repository;
    }

    @GetMapping
    public static Password getPass(){
        return Password.builder().user(null).password("1234").service(null).id(1L).build();
    }

//    @GetMapping("/test")
//    public User SaveTest(){
//        User user = User.builder().name("Max").password("1234").encryptionKey("12345").build();
//        return this.userRepository.save(user);
//    }

    @PostMapping("/user")
    public User saveUser(@RequestBody User user){
        return this.userRepository.save(user);
    }


    @GetMapping("/generate")
    public String generatePassword(){
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
