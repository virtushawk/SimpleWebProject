package edu.epam.swp.model.validation;

/**
 * ReviewValidator class is used to validate review's data. Used in services.
 * @author romab
 */
public class ReviewValidator {

    private static final String REVIEW_REGEX = "(^[ ,.!?\\-{}0-9()a-zA-Z]{1,350}$)";
    private static final String RATING_REGEX = "(^[1-5]$)";

    private ReviewValidator() {}

    /**
     * Validates review's text.
     * @param review String containing the review's text.
     * @return True if review's text is valid, otherwise false.
     */
    public static boolean isReview(String review) {
        return review.matches(REVIEW_REGEX);
    }

    /**
     * Validates review's rating.
     * @param rating review's rating.
     * @return True if rating is valid, otherwise false.
     */
    public static boolean isRating(Integer rating) {
        return rating.toString().matches(RATING_REGEX);
    }
}
