package com.passpnu.passwordmanager.restcontroller;

import com.passpnu.passwordmanager.dto.user.AuthResponseDto;
import com.passpnu.passwordmanager.dto.user.AuthUserDetailsDto;
import com.passpnu.passwordmanager.dto.user.UserDto;
import com.passpnu.passwordmanager.service.UserEntityService;
import com.passpnu.passwordmanager.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@AllArgsConstructor
@RestController
public class AuthRestController {
    private final UserEntityService userEntityService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
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
    public AuthResponseDto logIn(@RequestBody UserDto logInUser){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInUser.getUsername(), logInUser.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final AuthUserDetailsDto userDetails = (AuthUserDetailsDto) userDetailsService.loadUserByUsername(logInUser.getUsername());

        userDetails.setId(
                userEntityService.getIdByUsername(userDetails.getUsername())
        );

        String jwt = jwtUtil.generateToken(userDetails);

        return AuthResponseDto.builder().token(jwt).build();
    }

    @GetMapping("/user")
    public AuthUserDetailsDto getUser(@AuthenticationPrincipal AuthUserDetailsDto user){
        return user;
    }
}
