package com.passpnu.passwordmanager.controller;

import com.passpnu.passwordmanager.dto.user.AuthUserDetailsDto;
import com.passpnu.passwordmanager.dto.user.UserDto;
import com.passpnu.passwordmanager.service.UserEntityService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
@AllArgsConstructor
public class AuthController {
    private final UserEntityService userEntityService;

    @GetMapping("/sign-up")
    public String signUpPage(){
        return "signup";
    }

    @PostMapping("/sign-up")
    public String signUp(UserDto signUpUser){
        if(userEntityService.existsByUsername(signUpUser.getUsername())){
            return "redirect:/sign-up";
        }

        userEntityService.saveUser(signUpUser);
        return "redirect:/view/login";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/login";
    }


    @GetMapping("/menu")
    public String menuPage(@AuthenticationPrincipal AuthUserDetailsDto user){
        return "menu";
    }

}
