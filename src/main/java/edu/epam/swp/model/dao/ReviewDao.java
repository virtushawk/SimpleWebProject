package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.Review;
import edu.epam.swp.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * ReviewDao interface contains methods for working with Review object.
 * Extends {@link BaseDao}.
 * @see Review
 * @author romab
 */
public interface ReviewDao extends BaseDao<Review>{

    /**
     * Finds Creature's reviews.
     * @param creatureId Creature's id.
     * @return List of reviews.
     * @throws DaoException if SQLException was thrown.
     */
    List<Review> findReviewsByCreatureId(long creatureId) throws DaoException;

    /**
     * Finds user's reviews.
     * @param accountId User's id.
     * @return List of reviews.
     * @throws DaoException if SQLException was thrown.
     */
    List<Review> findReviewsByAccountId(long accountId) throws DaoException;

    /**
     * Finds User's specific review.
     * @param accountId User's id.
     * @param creatureId Creature's id.
     * @return Optional of review.
     * @throws DaoException if SQLException was thrown.
     */
    Optional<Review> findReviewByAccountIdCreatureId(long accountId, long creatureId) throws DaoException;

    /**
     * Deletes User's review.
     * @param reviewId Review's id.
     * @param accountId User's id.
     * @return true if review was deleted successfully, otherwise false.
     * @throws DaoException if SQLException was thrown.
     */
    boolean delete(long reviewId,long accountId) throws DaoException;

    /**
     * Updates user's review.
     * @param accountId User's id.
     * @param review The new review.
     * @return true if review was updated successfully, otherwise false.
     * @throws DaoException if SQLException was thrown.
     */
    boolean update(long accountId,Review review) throws DaoException;
}
