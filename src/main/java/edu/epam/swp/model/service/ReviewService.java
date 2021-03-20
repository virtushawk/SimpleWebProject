package edu.epam.swp.model.service;

import edu.epam.swp.model.entity.Review;
import edu.epam.swp.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * ReviewService interface contains method to work with Review object.
 * @see Review
 * @author romab
 */
public interface ReviewService {

    /**
     * Finds creature's reviews.
     * @param creatureId Creature's id.
     * @return List of reviews.
     * @throws ServiceException If DaoException was thrown.
     */
    List<Review> findCreatureReviews(long creatureId) throws ServiceException;

    /**
     * Finds user's reviews.
     * @param accountId User's id.
     * @return List of reviews.
     * @throws ServiceException If DaoException was thrown.
     */
    List<Review> findUserReviews(long accountId) throws ServiceException;

    /**
     * Finds all reviews.
     * @return List of reviews.
     * @throws ServiceException If DaoException was thrown.
     */
    List<Review> findAll() throws ServiceException;

    /**
     * Creates review.
     * @param review Review object.
     * @return True if object was created, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean createReview(Review review) throws ServiceException;

    /**
     * Edits review.
     * @param review Review object.
     * @return True if review was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean editReview(Review review) throws ServiceException;

    /**
     * Edits user's review.
     * @param accountId User's id.
     * @param review Review object.
     * @return True if review was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean editReview(long accountId,Review review) throws ServiceException;

    /**
     * Deletes review.
     * @param reviewId Review's id.
     * @return True if review was deleted, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean delete(long reviewId) throws ServiceException;

    /**
     * Deletes user's review.
     * @param reviewId Review's id.
     * @param accountId user's id.
     * @return True if review was deleted, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean delete(long reviewId,long accountId) throws ServiceException;

    /**
     * Finds user's review.
     * @param accountId User's id.
     * @param creatureId Creature's id.
     * @return Optional of review.
     * @throws ServiceException If DaoException was thrown.
     */
    Optional<Review> findUserReview(long accountId,long creatureId) throws ServiceException;
}
