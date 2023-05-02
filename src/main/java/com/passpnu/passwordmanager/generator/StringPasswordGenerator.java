package com.passpnu.passwordmanager.generator;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class StringPasswordGenerator {
    private StringPasswordGenerator(){}

    public static String generate(){
        SecureRandom random = new SecureRandom();

        int lowerCaseCount = random.nextInt(3) + 2;
        int upperCaseCount = random.nextInt(3) + 2;
        int digitCount = random.nextInt(3) + 2;
        int length = lowerCaseCount + upperCaseCount + digitCount;

        CharacterRule lowerCaseRule = new CharacterRule(EnglishCharacterData.LowerCase);
        lowerCaseRule.setNumberOfCharacters(lowerCaseCount);

        CharacterRule upperCaseRule = new CharacterRule(EnglishCharacterData.UpperCase);
        upperCaseRule.setNumberOfCharacters(upperCaseCount);

        CharacterRule digitRule = new CharacterRule(EnglishCharacterData.Digit);
        digitRule.setNumberOfCharacters(digitCount);

        PasswordGenerator passGen = new PasswordGenerator();

        return passGen.generatePassword(length, lowerCaseRule, upperCaseRule, digitRule);
    }
}
