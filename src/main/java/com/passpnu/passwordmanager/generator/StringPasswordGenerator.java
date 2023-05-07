package com.passpnu.passwordmanager.generator;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class StringPasswordGenerator {
    private StringPasswordGenerator(){}

    public String generate(){
        SecureRandom random = new SecureRandom();

        int lowerCaseCount = random.nextInt(3) + 2;
        int upperCaseCount = random.nextInt(3) + 2;
        int digitCount = random.nextInt(3) + 2;
        int specialCharCount = random.nextInt(3);
        int length = lowerCaseCount + upperCaseCount + digitCount + specialCharCount;

        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
        lowerCaseRule.setNumberOfCharacters(lowerCaseCount);

        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase);
        upperCaseRule.setNumberOfCharacters(upperCaseCount);

        CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit);
        digitRule.setNumberOfCharacters(digitCount);

        CharacterRule specialCharRule = new CharacterRule(EnglishCharacterData.Special);
        specialCharRule.setNumberOfCharacters(specialCharCount);

        PasswordGenerator passGen = new PasswordGenerator();

        return passGen.generatePassword(length, specialCharRule, lowerCaseRule, upperCaseRule, digitRule);
    }
}
