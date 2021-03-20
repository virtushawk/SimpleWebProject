package edu.epam.swp.tag;

import edu.epam.swp.model.entity.Review;

import java.util.Comparator;
import java.util.List;

/**
 * ReviewSortFunction is used to sort reviews.
 */
public class ReviewSortFunction {

    private ReviewSortFunction() {}

    /**
     * Sorts list by users.
     * @param reviews List of reviews.
     * @return List of sorted reviews.
     */
    public static List<Review> sortByUser(List<Review> reviews) {
        Comparator<Review> byUser = (o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.getAccountName(),o2.getAccountName());
        reviews.sort(byUser);
        return reviews;
    }

    /**
     * Sorts list by review's rating.
     * @param reviews List of reviews.
     * @return List of sorted reviews.
     */
    public static List<Review> sortByRating(List<Review> reviews) {
        Comparator<Review> byUser = Comparator.comparingInt(Review::getRating).reversed();
        reviews.sort(byUser);
        return reviews;
    }
}
