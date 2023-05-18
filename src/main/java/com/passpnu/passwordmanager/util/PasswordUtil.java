package com.passpnu.passwordmanager.util;

import org.flywaydb.core.internal.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PasswordUtil {

    private List<Pair<Pattern, String>> patternDescriptionList;
    public PasswordUtil(){
        patternDescriptionList = List.of(
            Pair.of(Pattern.compile(".{8,}"), "Must contain at least 8 characters"),
            Pair.of(Pattern.compile("\\w*[A-Z]"), "Must contain at least one upper case character"),
            Pair.of(Pattern.compile( "\\w*[a-z]"), "Must contain at least one lower case character"),
            Pair.of(Pattern.compile( "\\w*\\d"), "Must contain at least one digit"),
            Pair.of(Pattern.compile( "\\w*[^A-Za-z0-9\\s]"), "Must contain at least one special character")
        );
    }

    public PasswordTestAnswer isPasswordStrong(String password){
        PasswordTestAnswer passwordTestAnswer = new PasswordTestAnswer(true);

        Pattern pattern;
        Matcher matcher;

        for(Pair<Pattern, String> pair : patternDescriptionList){
            pattern = pair.getLeft();
            matcher = pattern.matcher(password);
            if(!matcher.find()){
                passwordTestAnswer.setStrong(false);
                passwordTestAnswer.addDescription(pair.getRight());
            }
        }

        return passwordTestAnswer;
    }


}
