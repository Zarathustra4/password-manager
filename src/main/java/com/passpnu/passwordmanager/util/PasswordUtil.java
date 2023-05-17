package com.passpnu.passwordmanager.util;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordUtil {

    private final List<Pattern> patterns;
    private final List<String> descriptions;

    public PasswordUtil(){
        patterns = new ArrayList<>();
        patterns.add(Pattern.compile(".{8,}"));
        patterns.add(Pattern.compile("\\w*[A-Z]"));
        patterns.add(Pattern.compile("\\w*[a-z]"));
        patterns.add(Pattern.compile("\\w*\\d"));
        patterns.add(Pattern.compile("\\w*[^A-Za-z0-9\\s]"));

        descriptions = new ArrayList<>();
        descriptions.add("Must contain at least 8 characters");
        descriptions.add("Must contain at least one upper case character");
        descriptions.add("Must contain at least one lower case character");
        descriptions.add("Must contain at least one digit");
        descriptions.add("Must contain at least one special character");
    }

    public PasswordTestAnswer isPasswordStrong(String password){
        PasswordTestAnswer passwordTestAnswer = new PasswordTestAnswer(true);

        Pattern pattern;
        Matcher matcher;
        for(int i = 0; i < patterns.size(); i++){
            pattern = patterns.get(i);
            matcher = pattern.matcher(password);
            if(!matcher.find()){
                passwordTestAnswer.setStrong(false);
                passwordTestAnswer.addDescription(descriptions.get(i));
            }
        }

        return passwordTestAnswer;
    }
}
