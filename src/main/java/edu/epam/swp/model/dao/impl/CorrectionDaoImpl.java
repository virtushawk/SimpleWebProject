package edu.epam.swp.model.dao.impl;

import edu.epam.swp.model.dao.CorrectionDao;
import edu.epam.swp.model.entity.Correction;
import edu.epam.swp.exception.DaoException;
import edu.epam.swp.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CorrectionDaoImpl implements CorrectionDao {

    private static final Logger logger = LogManager.getLogger(CorrectionDaoImpl.class);
    private static final CorrectionDao instance = new CorrectionDaoImpl();
    private ConnectionPool pool = ConnectionPool.INSTANCE;
    private static final String INSERT_CORRECTION = "INSERT INTO corrections(creature_id,account_id,description,name,date) " +
            "VALUES (?,?,?,?,?)";
    private static final String SELECT_CORRECTION = "SELECT correction_id,creature_id,account_id,description,name,date " +
            "FROM corrections";
    private static final String UPDATE_CREATURE = "UPDATE creatures SET name = ?,description = ?,last_updated = ? WHERE " +
            "creature_id = ?";
    private static final String SELECT_CORRECTION_BY_ID = "SELECT creature_id,account_id,description,name,date FROM corrections WHERE " +
            "correction_id = ?";
    private static final String DELETE_CORRECTION = "DELETE FROM corrections WHERE correction_id = ?";
    private static final String SELECT_CORRECTION_BY_ACCOUNT_ID = "SELECT correction_id,creature_id,description," +
            "name,date FROM corrections WHERE account_id = ?";
    private static final String UPDATE_CORRECTION_BY_ACCOUNT_ID = "UPDATE corrections SET name = ?,description = ?,date = ? " +
            "WHERE correction_id = ? AND account_id = ?";
    private static final String DELETE_CORRECTION_BY_ACCOUNT_ID = "DELETE FROM corrections WHERE correction_id = ? AND account_id = ?";

    private CorrectionDaoImpl() {}

    public static CorrectionDao getInstance() {
        return instance;
    }

    @Override
    public List<Correction> findAll() throws DaoException {
        List<Correction> corrections = new ArrayList<>();
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_CORRECTION)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long correctionId = resultSet.getLong(1);
                long creatureId = resultSet.getLong(2);
                long accountId = resultSet.getLong(3);
                String text = resultSet.getString(4);
                String name = resultSet.getString(5);
                long time = resultSet.getLong(6);
                Date date = new Date(time);
                Correction correction = new Correction.CorrectionBuilder().withCorrectionId(correctionId)
                        .withAccountId(accountId).withCreatureId(creatureId).withText(text).withName(name).withDate(date).build();
                corrections.add(correction);
            }
        } catch (SQLException e) {
            logger.error("Error occurred while finding all corrections",e);
            throw new DaoException("Error occurred while finding all corrections",e);
        }
        return corrections;
    }

    @Override
    public boolean create(Correction correction) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_CORRECTION)) {
            statement.setLong(1,correction.getCreatureId());
            statement.setLong(2,correction.getAccountId());
            statement.setString(3,correction.getText());
            statement.setString(4,correction.getName());
            long time = correction.getDate().getTime();
            statement.setLong(5,time);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error occurred while creating correction",e);
            throw new DaoException("Error occurred while creating correction",e);
        }
        return flag;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CORRECTION)) {
            statement.setLong(1,id);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error occurred while deleting correction",e);
            throw new DaoException("Error occurred while deleting correction",e);
        }
        return flag;
    }

    @Override
    public boolean approveCorrection(long id) throws DaoException {
        boolean flag = false;
        Connection connection = pool.getConnection();
        try(PreparedStatement correctionStatement = connection.prepareStatement(SELECT_CORRECTION_BY_ID);
            PreparedStatement creatureStatement = connection.prepareStatement(UPDATE_CREATURE);
            PreparedStatement deleteStatement = connection.prepareStatement(DELETE_CORRECTION)) {
            correctionStatement.setLong(1,id);
            deleteStatement.setLong(1,id);
            connection.setAutoCommit(false);
            ResultSet resultSet = correctionStatement.executeQuery();
            if (resultSet.next()) {
                long creatureId = resultSet.getLong(1);
                long accountId = resultSet.getLong(2);
                String description = resultSet.getString(3);
                String name = resultSet.getString(4);
                long time = System.currentTimeMillis();
                creatureStatement.setString(1,name);
                creatureStatement.setString(2,description);
                creatureStatement.setLong(3,time);
                creatureStatement.setLong(4,creatureId);
                creatureStatement.executeUpdate();
                flag = deleteStatement.executeUpdate() > 0;
                connection.commit();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                logger.error("An error occurred while rolling back",e);
                throw new DaoException("An error occurred while rolling back",e);
            }
            logger.error("Error occurred while approving correction",e);
            throw new DaoException("Error occurred while approving correction",e);
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                logger.error("An error occurred while requesting a database",e);
            }
        }
        return flag;
    }

    @Override
    public List<Correction> findCorrectionsByAccountId(long accountId) throws DaoException {
        List<Correction> corrections = new ArrayList<>();
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_CORRECTION_BY_ACCOUNT_ID)) {
            statement.setLong(1,accountId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long correctionId = resultSet.getLong(1);
                long creatureId = resultSet.getLong(2);
                String text = resultSet.getString(3);
                String name = resultSet.getString(4);
                long time = resultSet.getLong(5);
                Date date = new Date(time);
                Correction correction = new Correction.CorrectionBuilder().withCorrectionId(correctionId)
                        .withAccountId(accountId).withCreatureId(creatureId).withText(text).withName(name).withDate(date).build();
                corrections.add(correction);
            }
        } catch (SQLException e) {
            logger.error("Error occurred while finding user's corrections",e);
            throw new DaoException("Error occurred while finding user's corrections",e);
        }
        return corrections;
    }

    @Override
    public boolean update(long accountId, Correction correction) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CORRECTION_BY_ACCOUNT_ID)) {
            statement.setString(1,correction.getName());
            statement.setString(2,correction.getText());
            long time = correction.getDate().getTime();
            statement.setLong(3,time);
            statement.setLong(4,correction.getCorrectionId());
            statement.setLong(5,accountId);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error occurred while updating correction",e);
            throw new DaoException("Error occurred while updating correction",e);
        }
        return flag;
    }

    @Override
    public boolean delete(long accountId, long correctionId) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CORRECTION_BY_ACCOUNT_ID)) {
            statement.setLong(1,correctionId);
            statement.setLong(2,accountId);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error occurred while deleting correction",e);
            throw new DaoException("Error occurred while deleting correction",e);
        }
        return flag;
    }

    @Override
    public Optional<Correction> get(long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Correction correction) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
