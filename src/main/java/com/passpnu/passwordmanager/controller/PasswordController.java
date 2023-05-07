package com.passpnu.passwordmanager.controller;


import com.passpnu.passwordmanager.dto.GuestPasswordDto;
import com.passpnu.passwordmanager.service.PasswordEntityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/passwords")
@AllArgsConstructor
public class PasswordController {

    private final PasswordEntityService passwordEntityService;

    @GetMapping("/generate")
    public GuestPasswordDto generatePassword(){
        return passwordEntityService.generatePassword();
    }
}