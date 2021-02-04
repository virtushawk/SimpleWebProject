package edu.epam.swp.model.dao.impl;

import edu.epam.swp.model.dao.ReviewDao;
import edu.epam.swp.model.entity.Review;
import edu.epam.swp.model.exception.DaoException;
import edu.epam.swp.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ReviewDaoImpl implements ReviewDao {
    private static final Logger logger = LogManager.getLogger(ReviewDaoImpl.class);
    private static final ReviewDao instance = new ReviewDaoImpl();
    private static final ConnectionPool pool = ConnectionPool.INSTANCE;
    private static final String INSERT_REVIEW = "INSERT INTO reviews(account_id,creature_id,review,time,rating) " +
            "VALUES(?,?,?,?,?)";

    private ReviewDaoImpl() {}

    public static ReviewDao getInstance() {
        return instance;
    }

    @Override
    public List<Review> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Review> get(long id) throws DaoException {
        return null;
    }

    @Override
    public boolean create(Review review) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_REVIEW)) {
            statement.setLong(1,review.getAccountId());
            statement.setLong(2,review.getCreatureId());
            statement.setString(3,review.getText());
            statement.setLong(4,review.getTime().getTime());
            statement.setInt(5,review.getRating());
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error occurred while creating review.Review : {}",review,e);
            throw new DaoException("Error occurred while creating review",e);
        }
        return flag;
    }
}
