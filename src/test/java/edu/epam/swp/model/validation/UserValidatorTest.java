package edu.epam.swp.model.validation;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class UserValidatorTest {

    @DataProvider
    public Object[][] usernameValidationData() {
        return new Object[][] {
                {"user",true},
                {"a",true},
                {"nicknameer",true},
                {"ник",false},
                {"",false},
                {"longestNick",false},
                {"nick name",false}
        };
    }

    @DataProvider
    public Object[][] passwordValidationData() {
        return new Object[][] {
                {"qwerty123",true},
                {"qwerty123adasdadasda",true},
                {"qwerty1",false},
                {"кверти",false},
                {"1234567",false}
        };
    }

    @DataProvider
    public Object[][] emailValidationData() {
        return new Object[][] {
                {"example@gmail.com",true},
                {"firstname.lastname@example.com",true},
                {"email@123.123.123.123",false},
                {"plainaddress",false},
                {"#@%^%#$@#$@#.com",false},
                {"@example.com",false},
                {"email.@example.com",false}
        };
    }

    @DataProvider
    public Object[][] nameValidationData() {
        return new Object[][] {
                {"Roman Bruhanchik",true},
                {"a",true},
                {"",false},
                {"Роман",false},
                {"Roman @#@$",false}
        };
    }

    @Test(dataProvider = "usernameValidationData")
    public void isUsernameTest(String username,boolean expected) {
        boolean actual = UserValidator.isUsername(username);
        Assert.assertEquals(actual,expected);
    }

    @Test(dataProvider = "passwordValidationData")
    public void isPasswordTest(String password,boolean expected) {
        boolean actual = UserValidator.isPassword(password);
        Assert.assertEquals(actual,expected);
    }

    @Test(dataProvider = "emailValidationData")
    public void isEmailTest(String email,boolean expected) {
        boolean actual = UserValidator.isEmail(email);
        Assert.assertEquals(actual,expected);
    }

    @Test(dataProvider = "nameValidationData")
    public void isNameTest(String name,boolean expected) {
        boolean actual = UserValidator.isName(name);
        Assert.assertEquals(actual,expected);
    }
}
