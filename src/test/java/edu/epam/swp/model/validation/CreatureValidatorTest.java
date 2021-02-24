package edu.epam.swp.model.validation;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreatureValidatorTest {

    @DataProvider
    public Object[][] nameValidationData() {
        return new Object[][] {
                {"Test name",true},
                {"",false},
                {"looooooooooooooooong test name",true},
                {"тестовое имя",false},
                {"!@#$%^&*{}",false},
                {"Test name must be in range of one to thirty characters",false}
        };
    }

    @DataProvider
    public Object[][] descriptionValidationData() {
        return new Object[][] {
                {"Test description",true},
                {"",false},
                {"space \r",true},
                {"тестовое описание",false},
                {"Wow,; this sentence has all \n \r - (really!) allowed {0-9} characters?.",true}
        };
    }

    @Test(dataProvider = "nameValidationData")
    public void isNameTest(String name,boolean expected) {
        boolean actual = CreatureValidator.isName(name);
        Assert.assertEquals(actual,expected);
    }

    @Test(dataProvider = "descriptionValidationData")
    public void isDescriptionTest(String description,boolean expected) {
        boolean actual = CreatureValidator.isDescription(description);
        Assert.assertEquals(actual,expected);
    }
}
