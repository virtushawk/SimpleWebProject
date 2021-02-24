package edu.epam.swp.model.validation;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReviewValidatorTest {

    @DataProvider
    public Object[][] reviewValidationData() {
        return new Object[][] {
                {"Test review",true},
                {"Wow,this review has (really!) all {0-9} allowed characters?",true},
                {"1",true},
                {"",false},
                {"тестовый отзыв",false},
                {"<WOW>Very %#$@ review",false}
        };
    }

    @DataProvider
    public Object[][] ratingValidationData() {
        return new Object[][] {
                {4,true},
                {1,true},
                {5,true},
                {0,false}
        };
    }

    @Test(dataProvider = "reviewValidationData")
    public void isReviewTest(String review,boolean expected) {
        boolean actual = ReviewValidator.isReview(review);
        Assert.assertEquals(actual,expected);
    }

    @Test(dataProvider = "ratingValidationData")
    public void isRatingTest(int rating,boolean expected) {
        boolean actual = ReviewValidator.isRating(rating);
        Assert.assertEquals(actual,expected);
    }
}
