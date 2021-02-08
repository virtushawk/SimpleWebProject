package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.Review;
import edu.epam.swp.model.exception.DaoException;

import java.util.List;

public interface ReviewDao extends BaseDao<Review>{

    List<Review> findReviewsByCreatureId(long id) throws DaoException;
    List<Review> findReviewsByUserId(long id) throws DaoException;
    boolean create(Review review) throws DaoException;
}
