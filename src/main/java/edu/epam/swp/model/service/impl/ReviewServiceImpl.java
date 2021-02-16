package edu.epam.swp.model.service.impl;

import edu.epam.swp.model.dao.ReviewDao;
import edu.epam.swp.model.dao.impl.ReviewDaoImpl;
import edu.epam.swp.model.entity.Review;
import edu.epam.swp.exception.DaoException;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ReviewServiceImpl implements ReviewService {
    private static final Logger logger = LogManager.getLogger(ReviewServiceImpl.class);
    private static final ReviewService instance = new ReviewServiceImpl();
    private static final ReviewDao dao = ReviewDaoImpl.getInstance();

    private ReviewServiceImpl() {}

    public static ReviewService getInstance() {
        return instance;
    }

    @Override
    public boolean createReview(Review review) throws ServiceException {
        boolean flag;
        try {
            flag = dao.create(review);
        } catch (DaoException e) {
            logger.error("Error occurred while creating review. Review : {}",review,e);
            throw new ServiceException("Error occurred while creating review",e);
        }
        return flag;
    }

    @Override
    public List<Review> findReviewsCreature(long id) throws ServiceException {
        List<Review> reviews;
        try {
            reviews = dao.findReviewsByCreatureId(id);
            return reviews;
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
    }

    @Override
    public List<Review> findReviewsUser(long id) throws ServiceException {
        List<Review> reviews;
        try {
            reviews = dao.findReviewsByUserId(id);
            return reviews;
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
    }

    @Override
    public List<Review> findAll() throws ServiceException {
        List<Review> reviews;
        try {
            reviews = dao.findAll();
            return reviews;
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
    }

    @Override
    public boolean editReview(Review review) throws ServiceException {
        boolean flag;
        try {
            flag = dao.update(review);
        } catch (DaoException e) {
            logger.error("Error occurred while updating review",e);
            throw new ServiceException("Error occurred while creating review",e);
        }
        return flag;
    }

    @Override
    public boolean editReview(long accountId, Review review) throws ServiceException {
        boolean flag;
        try {
            flag = dao.update(accountId,review);
        } catch (DaoException e) {
            logger.error("Error occurred while updating review",e);
            throw new ServiceException("Error occurred while creating review",e);
        }
        return flag;
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.delete(id);
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
            logger.error("Error occurred while deleting review",e);
            throw new ServiceException("Error occurred while deleting review",e);
        }
        return review;
    }
}
