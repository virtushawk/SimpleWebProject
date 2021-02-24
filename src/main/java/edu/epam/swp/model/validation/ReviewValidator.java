package edu.epam.swp.model.validation;

public class ReviewValidator {

    private static final String REVIEW_REGEX = "(^[ ,.!?\\-{}0-9()a-zA-Z]{1,350}$)";
    private static final String RATING_REGEX = "(^[1-5]$)";

    private ReviewValidator() {}

    public static boolean isReview(String review) {
        return review.matches(REVIEW_REGEX);
    }

    public static boolean isRating(Integer rating) {
        return rating.toString().matches(RATING_REGEX);
    }
}
