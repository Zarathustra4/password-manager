package com.passpnu.passwordmanager.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordUtil {
    public PasswordTestAnswer isPasswordStrong(String password){
        PasswordTestAnswer passwordTestAnswer = new PasswordTestAnswer(true);


        if(password.length() < 8){
            passwordTestAnswer.addDescription("Password must contain at least 8 characters");
            passwordTestAnswer.setStrong(false);
        }



        return passwordTestAnswer;
        //todo complete the function
    }
}
