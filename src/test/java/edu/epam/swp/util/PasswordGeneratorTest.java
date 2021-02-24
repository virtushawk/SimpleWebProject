package edu.epam.swp.util;

import org.passay.*;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PasswordGeneratorTest {

    @Test
    public void generatePasswordTest() {
        String password = PasswordGenerator.generatePassword();
        PasswordData passwordData = new PasswordData(password);
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);
        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);
        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(4);
        LengthRule lengthRule = new LengthRule(8);
        PasswordValidator passwordValidator = new PasswordValidator(lengthRule,lowerCaseRule,upperCaseRule,digitRule);
        RuleResult validate = passwordValidator.validate(passwordData);
        boolean actual = validate.isValid();
        Assert.assertTrue(actual);
    }
}
