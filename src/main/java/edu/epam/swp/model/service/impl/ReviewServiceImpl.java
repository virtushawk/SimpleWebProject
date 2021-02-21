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

public class ReviewServiceImpl implements ReviewService {
    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);
    private static final ReviewService instance = new ReviewServiceImpl();
    private ReviewDao dao = ReviewDaoImpl.getInstance();

    private ReviewServiceImpl() {}

    public static ReviewService getInstance() {
        return instance;
    }

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

    @Override
    public List<Review> findCreatureReviews(long id) throws ServiceException {
        List<Review> reviews;
        try {
            reviews = dao.findReviewsByCreatureId(id);
        } catch (DaoException e) {
            logger.error("An error occurred while finding reviews");
            throw new ServiceException("An error occurred while finding reviews",e);
        }
        return reviews;
    }

    @Override
    public List<Review> findUserReviews(long id) throws ServiceException {
        List<Review> reviews;
        try {
            reviews = dao.findReviewsByUserId(id);
        } catch (DaoException e) {
            logger.error("An error occurred while finding user's reviews",e);
            throw new ServiceException("An error occurred while finding user's reviews",e);
        }
        return reviews;
    }

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

    @Override
    public Optional<Review> findUserReview(long userId,long creatureId) throws ServiceException {
        Optional<Review> review;
        try {
            review = dao.findReviewByUserIdCreatureId(userId,creatureId);
        } catch (DaoException e) {
            logger.error("Error occurred while finding user's review",e);
            throw new ServiceException("Error occurred while finding user's review",e);
        }
        return review;
    }
}
