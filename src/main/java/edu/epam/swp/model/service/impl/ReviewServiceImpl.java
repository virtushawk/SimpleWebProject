package edu.epam.swp.model.service.impl;

import edu.epam.swp.model.dao.ReviewDao;
import edu.epam.swp.model.dao.impl.ReviewDaoImpl;
import edu.epam.swp.model.entity.Review;
import edu.epam.swp.model.exception.DaoException;
import edu.epam.swp.model.exception.ServiceException;
import edu.epam.swp.model.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
}
