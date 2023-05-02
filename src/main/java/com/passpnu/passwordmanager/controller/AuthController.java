package com.passpnu.passwordmanager.controller;

import com.passpnu.passwordmanager.dto.AuthRequestDto;
import com.passpnu.passwordmanager.dto.AuthResponseDto;
import com.passpnu.passwordmanager.dto.UserDto;
import com.passpnu.passwordmanager.jwt.JwtUtil;
import com.passpnu.passwordmanager.service.UserEntityService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@AllArgsConstructor
@RestController
public class AuthController {
    private final UserEntityService userEntityService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserDto signUpUser){
            if(userEntityService.existsByUsername(signUpUser.getUsername())){
                return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
            }

            userEntityService.saveUser(signUpUser);

            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/log-in")
    public AuthResponseDto logIn(@RequestBody AuthRequestDto authRequest){

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Incorrect password or username!", e);
        }

        final UserDetails user = userDetailsService.loadUserByUsername(authRequest.getUsername());

        //String jwt = jwtUtil.generateToken(user);

        String jwt = "token";

        return AuthResponseDto.builder().token(jwt).build();
    }
}
