package edu.epam.swp.model.service;

import edu.epam.swp.model.entity.Review;
import edu.epam.swp.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<Review> findCreatureReviews(long id) throws ServiceException;
    List<Review> findUserReviews(long id) throws ServiceException;
    List<Review> findAll() throws ServiceException;
    boolean createReview(Review review) throws ServiceException;
    boolean editReview(Review review) throws ServiceException;
    boolean editReview(long accountId,Review review) throws ServiceException;
    boolean delete(long reviewId) throws ServiceException;
    boolean delete(long reviewId,long accountId) throws ServiceException;
    Optional<Review> findUserReview(long userId,long creatureId) throws ServiceException;
}
