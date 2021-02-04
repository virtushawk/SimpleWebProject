package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.Review;
import edu.epam.swp.model.exception.DaoException;

public interface ReviewDao extends BaseDao<Review>{

    boolean create(Review review) throws DaoException;
}
