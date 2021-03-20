package edu.epam.swp.model.service.impl;

import edu.epam.swp.model.dao.ReviewDao;
import edu.epam.swp.model.dao.impl.ReviewDaoImpl;
import edu.epam.swp.model.entity.Review;
import edu.epam.swp.exception.DaoException;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.ReviewService;
import edu.epam.swp.model.validation.ReviewValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * The implementation of {@link ReviewService}. Contains methods to work with Review object.
 * @author romab
 * @see Review
 */
public class ReviewServiceImpl implements ReviewService {

    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);
    private static final ReviewService instance = new ReviewServiceImpl();
    private ReviewDao dao = ReviewDaoImpl.getInstance();

    private ReviewServiceImpl() {}

    /**
     * Gets instance.
     * @return the instance
     */
    public static ReviewService getInstance() {
        return instance;
    }

    /**
     * Creates review.
     * @param review Review object.
     * @return True if object was created, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean createReview(Review review) throws ServiceException {
        boolean flag;
        String text = review.getText();
        int rating = review.getRating();
        if((!ReviewValidator.isReview(text)) || (!ReviewValidator.isRating(rating))) {
            logger.info("Invalid credentials for review");
            return false;
        }
        try {
            flag = dao.create(review);
        } catch (DaoException e) {
            logger.error("Error occurred while creating review",e);
            throw new ServiceException("Error occurred while creating review",e);
        }
        return flag;
    }

    /**
     * Finds creature's reviews.
     * @param creatureId Creature's id.
     * @return List of reviews.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public List<Review> findCreatureReviews(long creatureId) throws ServiceException {
        List<Review> reviews;
        try {
            reviews = dao.findReviewsByCreatureId(creatureId);
        } catch (DaoException e) {
            logger.error("An error occurred while finding reviews",e);
            throw new ServiceException("An error occurred while finding reviews",e);
        }
        return reviews;
    }

    /**
     * Finds user's reviews.
     * @param accountId User's id.
     * @return List of reviews.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public List<Review> findUserReviews(long accountId) throws ServiceException {
        List<Review> reviews;
        try {
            reviews = dao.findReviewsByAccountId(accountId);
        } catch (DaoException e) {
            logger.error("An error occurred while finding user's reviews",e);
            throw new ServiceException("An error occurred while finding user's reviews",e);
        }
        return reviews;
    }

    /**
     * Finds all reviews.
     * @return List of reviews.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public List<Review> findAll() throws ServiceException {
        List<Review> reviews;
        try {
            reviews = dao.findAll();
        } catch (DaoException e) {
            logger.error("An error occurred while finding all reviews",e);
            throw new ServiceException("An error occurred while finding all reviews",e);
        }
        return reviews;
    }

    /**
     * Edits review.
     * @param review Review object.
     * @return True if review was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean editReview(Review review) throws ServiceException {
        boolean flag;
        String text = review.getText();
        int rating = review.getRating();
        if((!ReviewValidator.isReview(text)) || (!ReviewValidator.isRating(rating))) {
            logger.info("Invalid credentials for review");
            return false;
        }
        try {
            flag = dao.update(review);
        } catch (DaoException e) {
            logger.error("Error occurred while updating review",e);
            throw new ServiceException("Error occurred while updating review",e);
        }
        return flag;
    }

    /**
     * Edits user's review.
     * @param accountId User's id.
     * @param review Review object.
     * @return True if review was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean editReview(long accountId, Review review) throws ServiceException {
        boolean flag;
        String text = review.getText();
        int rating = review.getRating();
        if((!ReviewValidator.isReview(text)) || (!ReviewValidator.isRating(rating))) {
            logger.info("Invalid credentials for review");
            return false;
        }
        try {
            flag = dao.update(accountId,review);
        } catch (DaoException e) {
            logger.error("Error occurred while updating review",e);
            throw new ServiceException("Error occurred while updating review",e);
        }
        return flag;
    }

    /**
     * Deletes review.
     * @param reviewId Review's id.
     * @return True if review was deleted, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean delete(long reviewId) throws ServiceException {
        boolean flag;
        try {
            flag = dao.delete(reviewId);
        } catch (DaoException e) {
            logger.error("Error occurred while deleting review",e);
            throw new ServiceException("Error occurred while deleting review",e);
        }
        return flag;
    }

    /**
     * Deletes user's review.
     * @param reviewId Review's id.
     * @param accountId user's id.
     * @return True if review was deleted, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean delete(long reviewId, long accountId) throws ServiceException {
        boolean flag;
        try {
            flag = dao.delete(reviewId,accountId);
        } catch (DaoException e) {
            logger.error("Error occurred while deleting review",e);
            throw new ServiceException("Error occurred while deleting review",e);
        }
        return flag;
    }

    /**
     * Finds user's review.
     * @param accountId User's id.
     * @param creatureId Creature's id.
     * @return Optional of review.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public Optional<Review> findUserReview(long accountId,long creatureId) throws ServiceException {
        Optional<Review> review;
        try {
            review = dao.findReviewByAccountIdCreatureId(accountId,creatureId);
        } catch (DaoException e) {
            logger.error("Error occurred while finding user's review",e);
            throw new ServiceException("Error occurred while finding user's review",e);
        }
        return review;
    }
}
