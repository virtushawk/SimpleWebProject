package edu.epam.swp.model.service;

import edu.epam.swp.model.entity.Review;
import edu.epam.swp.model.exception.ServiceException;

import java.util.List;

public interface ReviewService {

    boolean createReview(Review review) throws ServiceException;
    List<Review> findReviewsCreature(long id) throws ServiceException;
    List<Review> findReviewsUser(long id) throws ServiceException;
    List<Review> findAll() throws ServiceException;
    boolean editReview(Review review) throws ServiceException;
    boolean delete(long id) throws ServiceException;
}
