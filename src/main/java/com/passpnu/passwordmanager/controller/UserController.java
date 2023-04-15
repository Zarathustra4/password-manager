package com.passpnu.passwordmanager.controller;

import com.passpnu.passwordmanager.dto.UserDto;
import com.passpnu.passwordmanager.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
    private final UserEntityService userEntityService;

    private final AuthenticationManager authenticationManager;

    public UserController(@Qualifier("userService") UserEntityService userEntityService,
                          @Qualifier("authManager") AuthenticationManager authenticationManager) {
        this.userEntityService = userEntityService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserDto signUpUser){
            if(userEntityService.existsByUsername(signUpUser.getUsername())){
                return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
            }

            userEntityService.saveUser(signUpUser);

            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/log-in")
    public ResponseEntity<String> logIn(@RequestBody UserDto logInUser){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInUser.getUsername(), logInUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User logged in successfully!", HttpStatus.OK);
    }

}
