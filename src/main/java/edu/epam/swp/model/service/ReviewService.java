package edu.epam.swp.model.service;

import edu.epam.swp.model.entity.Review;
import edu.epam.swp.model.exception.ServiceException;

public interface ReviewService {
    boolean createReview(Review review) throws ServiceException;
}
