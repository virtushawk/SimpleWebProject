package edu.epam.swp.model.dao.impl;

import edu.epam.swp.model.dao.ReviewDao;
import edu.epam.swp.model.entity.Review;
import edu.epam.swp.model.exception.DaoException;
import edu.epam.swp.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDaoImpl implements ReviewDao {
    private static final Logger logger = LogManager.getLogger(ReviewDaoImpl.class);
    private static final ReviewDao instance = new ReviewDaoImpl();
    private static final ConnectionPool pool = ConnectionPool.INSTANCE;
    private static final String INSERT_REVIEW = "INSERT INTO reviews(account_id,creature_id,review,time,rating) " +
            "VALUES(?,?,?,?,?)";
    private static final String SELECT_REVIEW_BY_CREATURE_ID = "SELECT reviews.review_id,reviews.account_id," +
            "reviews.review,reviews.time,reviews.rating,accounts.avatar,accounts.username FROM reviews INNER JOIN accounts " +
            "ON reviews.account_id = accounts.account_id WHERE reviews.creature_id = ?";
    private static final String SELECT_REVIEW_BY_USER_ID = "SELECT reviews.review_id,reviews.account_id," +
            "reviews.review,reviews.time,reviews.rating,accounts.avatar,accounts.username FROM reviews INNER JOIN accounts " +
            "ON reviews.account_id = accounts.account_id WHERE reviews.account_id = ?";
    private static final String SELECT_REVIEW = "SELECT reviews.creature_id,reviews.review_id,reviews.account_id,reviews.review,reviews.time," +
            "reviews.rating,accounts.avatar,accounts.username FROM reviews INNER JOIN accounts ON " +
            "reviews.account_id = accounts.account_id";
    private static final String UPDATE_REVIEW = "UPDATE reviews SET review = ?,time = ?,rating = ? WHERE review_id = ?";
    private static final String DELETE_REVIEW = "DELETE FROM reviews WHERE review_id = ?";

    private ReviewDaoImpl() {}

    public static ReviewDao getInstance() {
        return instance;
    }

    @Override
    public List<Review> findAll() throws DaoException {
        List<Review> reviews = new ArrayList<>();
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_REVIEW)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long creatureId = resultSet.getLong(1);
                long reviewId = resultSet.getLong(2);
                long accountId = resultSet.getLong(3);
                String text = resultSet.getString(4);
                long time = resultSet.getLong(5);
                int rating = resultSet.getInt(6);
                String avatar = resultSet.getString(7);
                String accountUsername = resultSet.getString(8);
                Date date = new Date(time);
                Review review = new Review(accountId,creatureId,text,date,rating);
                review.setReviewId(reviewId);
                review.setAvatar(avatar);
                review.setAccountName(accountUsername);
                reviews.add(review);
            }
            return reviews;
        } catch (SQLException e) {
            logger.error("An error occurred when requesting a database",e);
            throw new DaoException("An error occurred when requesting a database",e);
        }
    }

    @Override
    public Optional<Review> get(long id) throws DaoException {
        return null;
    }

    @Override
    public List<Review> findReviewsByCreatureId(long id) throws DaoException {
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_REVIEW_BY_CREATURE_ID)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            List<Review> reviews = new ArrayList<>();
            while (resultSet.next()) {
                long reviewId = resultSet.getLong(1);
                long accountId = resultSet.getLong(2);
                String text = resultSet.getString(3);
                long time = resultSet.getLong(4);
                int rating = resultSet.getInt(5);
                String avatar = resultSet.getString(6);
                String accountUsername = resultSet.getString(7);
                Date date = new Date(time);
                Review review = new Review(accountId,id,text,date,rating);
                review.setReviewId(reviewId);
                review.setAvatar(avatar);
                review.setAccountName(accountUsername);
                reviews.add(review);
            }
            return reviews;
        } catch (SQLException e) {
            logger.error("An error occurred when requesting a database",e);
            throw new DaoException("An error occurred when requesting a database",e);
        }
    }

    @Override
    public List<Review> findReviewsByUserId(long id) throws DaoException {
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_REVIEW_BY_USER_ID)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            List<Review> reviews = new ArrayList<>();
            while (resultSet.next()) {
                long reviewId = resultSet.getLong(1);
                long accountId = resultSet.getLong(2);
                String text = resultSet.getString(3);
                long time = resultSet.getLong(4);
                int rating = resultSet.getInt(5);
                String avatar = resultSet.getString(6);
                String accountUsername = resultSet.getString(7);
                Date date = new Date(time);
                Review review = new Review(accountId,id,text,date,rating);
                review.setReviewId(reviewId);
                review.setAvatar(avatar);
                review.setAccountName(accountUsername);
                reviews.add(review);
            }
            return reviews;
        } catch (SQLException e) {
            logger.error("An error occurred when requesting a database",e);
            throw new DaoException("An error occurred when requesting a database",e);
        }
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

    @Override
    public boolean update(Review review) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_REVIEW)) {
            statement.setString(1,review.getText());
            statement.setLong(2,review.getTime().getTime());
            statement.setInt(3,review.getRating());
            statement.setLong(4,review.getReviewId());
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred when requesting a database",e);
            throw new DaoException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_REVIEW)) {
            statement.setLong(1,id);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred while requesting a database",e);
            throw new DaoException("An error occurred while requesting a database",e);
        }
        return flag;
    }

}
