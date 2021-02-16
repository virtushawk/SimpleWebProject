package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.Review;
import edu.epam.swp.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ReviewDao extends BaseDao<Review>{

    List<Review> findReviewsByCreatureId(long id) throws DaoException;
    List<Review> findReviewsByUserId(long id) throws DaoException;
    Optional<Review> findReviewByUserIdCreatureId(long userId,long creatureId) throws DaoException;
    boolean delete(long reviewId,long accountId) throws DaoException;
    boolean update(long accountId,Review review) throws DaoException;
}
