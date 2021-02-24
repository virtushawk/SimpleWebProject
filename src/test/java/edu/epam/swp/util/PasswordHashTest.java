package edu.epam.swp.util;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class PasswordHashTest {

    @DataProvider
    public Object[][] hashData() {
        return new Object[][] {
                {"123456","e10adc3949ba59abbe56e057f20f883e",true},
                {"123456","dsdasdasfwgt2rewd1e12r3r32rfwefc",false},
        };
    }

    @Test(dataProvider = "hashData")
    public void testCreateHash(String source,String expectedHash,boolean expected) {
        String actualHash = PasswordHash.createHash(source);
        boolean actual = actualHash.equals(expectedHash);
        Assert.assertEquals(actual,expected);
    }
}
