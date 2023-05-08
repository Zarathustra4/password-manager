package com.passpnu.passwordmanager.controller;


import com.passpnu.passwordmanager.dto.AuthPasswordDto;
import com.passpnu.passwordmanager.dto.AuthUserDetailsDto;
import com.passpnu.passwordmanager.dto.GuestPasswordDto;
import com.passpnu.passwordmanager.service.PasswordEntityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.NameNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@RestController
@RequestMapping("/passwords")
@AllArgsConstructor
public class PasswordController {

    private final PasswordEntityService passwordEntityService;

    @GetMapping("/generate")
    public GuestPasswordDto generatePassword(){
        return passwordEntityService.generatePassword();
    }

    @PostMapping
    public ResponseEntity<String> postPassword(@RequestBody AuthPasswordDto passwordDto,
                                               @AuthenticationPrincipal AuthUserDetailsDto user)
            throws NameNotFoundException, NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        passwordEntityService.savePassword(passwordDto, user);

        return new ResponseEntity<>("The password was stored", HttpStatus.OK);
    }
}