package com.passpnu.passwordmanager.controller;


import com.passpnu.passwordmanager.dto.AuthUserDetailsDto;
import com.passpnu.passwordmanager.dto.PasswordResponseDto;
import com.passpnu.passwordmanager.dto.PasswordRequestDto;
import com.passpnu.passwordmanager.exception.EncryptionException;
import com.passpnu.passwordmanager.service.PasswordEntityService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.naming.NameNotFoundException;



@RestController
@RequestMapping("/passwords")
@AllArgsConstructor
public class PasswordController {

    private final PasswordEntityService passwordEntityService;

    @GetMapping("/generate")
    public PasswordResponseDto generatePassword(){
        return passwordEntityService.generatePassword();
    }

    @PostMapping("/generate")
    public PasswordResponseDto generateAndStorePassword(
            @AuthenticationPrincipal AuthUserDetailsDto userDetailsDto,
            @RequestBody PasswordRequestDto passwordRequestDto
    ) throws NameNotFoundException, EncryptionException {
        return passwordEntityService.generateAndStorePassword(passwordRequestDto, userDetailsDto);
    }

    @PostMapping
    public ResponseEntity<String> storePassword(@AuthenticationPrincipal AuthUserDetailsDto userDetailsDto,
                                                        @RequestBody PasswordRequestDto passwordRequest)
            throws NameNotFoundException, EncryptionException {

        passwordEntityService.savePassword(passwordRequest, userDetailsDto);
        return new ResponseEntity<>("Password was stored", HttpStatus.OK);
    }

    @GetMapping("/{serviceId}")
    public PasswordResponseDto getPassword(@AuthenticationPrincipal AuthUserDetailsDto userDetailsDto, @PathVariable Long serviceId) throws EncryptionException {
        return passwordEntityService.getPassword(serviceId, userDetailsDto);
    }

    @PutMapping
    public ResponseEntity<String> changePassword(
            @AuthenticationPrincipal AuthUserDetailsDto userDetailsDto,
            @RequestBody PasswordRequestDto passwordRequestDto) throws NameNotFoundException, EncryptionException {
        passwordEntityService.changePassword(passwordRequestDto, userDetailsDto);

        return new ResponseEntity<>("Password was changed", HttpStatus.OK);
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<String> deletePassword(
            @AuthenticationPrincipal AuthUserDetailsDto userDetailsDto,
            @PathVariable Long serviceId
    ){
        passwordEntityService.deletePassword(serviceId, userDetailsDto);
        return new ResponseEntity<>("The password was deleted successfully", HttpStatus.OK);
    }
}