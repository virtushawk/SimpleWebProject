package edu.epam.swp.util;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;

/**
 * PasswordGenerator class is used to generate password for password recovery function.
 * @author romab
 */
public class PasswordGenerator {

    public static final int NUMBER_LOWER_CASE_CHARACTERS = 2;
    public static final int NUMBER_UPPER_CASE_CHARACTERS = 2;
    public static final int NUMBER_DIGITS = 4;
    public static final int PASSWORD_LENGHT = 8;

    private PasswordGenerator() {}

    /**
     * Generates password with rules.
     * @return String containing the password.
     */
    public static String generatePassword() {
        org.passay.PasswordGenerator generator = new org.passay.PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(NUMBER_LOWER_CASE_CHARACTERS);
        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(NUMBER_UPPER_CASE_CHARACTERS);
        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(NUMBER_DIGITS);
        String password = generator.generatePassword(PASSWORD_LENGHT,lowerCaseRule,upperCaseRule,digitRule);
        return password;
    }
}
