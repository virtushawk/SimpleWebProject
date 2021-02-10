package edu.epam.swp.model.dao.impl;

import edu.epam.swp.model.dao.CreatureDao;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.exception.DaoException;
import edu.epam.swp.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreatureDaoImpl implements CreatureDao {
    private static final Logger logger = LogManager.getLogger(CreatureDaoImpl.class);
    private static final CreatureDao instance = new CreatureDaoImpl();
    private static final ConnectionPool pool = ConnectionPool.INSTANCE;
    private static final String SELECT_CREATURE_BY_LAST_UPDATED_LIMIT = "SELECT creatures.creature_id,creatures.name," +
            "creatures.picture,creatures.description,creatures.last_updated FROM creatures " +
            "ORDER BY creatures.last_updated DESC LIMIT 0,3";
    private static final String SELECT_CREATURE_BY_RATING_LIMIT = "SELECT reviews.creature_id,count(reviews.rating) AS rate," +
            "creatures.name,creatures.picture,creatures.description,creatures.last_updated FROM reviews INNER JOIN creatures ON " +
            "reviews.creature_id = creatures.creature_id GROUP BY reviews.creature_id ORDER BY rate DESC LIMIT 0,3";
    private static final String INSERT_CREATURE = "INSERT INTO creatures(name,picture,description,last_updated) " +
            "VALUES(?,?,?,?)";
    private static final String SELECT_CREATURE_BY_ID = "SELECT creatures.name,creatures.picture," +
            "creatures.description,creatures.last_updated FROM creatures WHERE creatures.creature_id = ?";

    private CreatureDaoImpl() {}

    public static CreatureDao getInstance() {
        return instance;
    }

    @Override
    public List<Creature> findAll() throws DaoException {
        return null;
    }

    //todo check and clean
    @Override
    public List<Creature> findNewCreatures() throws DaoException {
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_CREATURE_BY_LAST_UPDATED_LIMIT)) {
            ResultSet resultSet = statement.executeQuery();
            List<Creature> creatures = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String picture = resultSet.getString(3);
                String description = resultSet.getString(4);
                long lastUpdated = resultSet.getLong(5);
                Date date = new Date(lastUpdated);
                Creature creature = new Creature(name,picture,description,date);
                creature.setId(id);
                creatures.add(creature);
            }
            return creatures;
        } catch (SQLException e) {
            logger.error("An error occurred when requesting a database",e);
            throw new DaoException("An error occurred when requesting a database",e);
        }
    }

    @Override
    public List<Creature> findPopularCreatures() throws DaoException {
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_CREATURE_BY_RATING_LIMIT)) {
            ResultSet resultSet = statement.executeQuery();
            List<Creature> creatures = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                int count = resultSet.getInt(2);
                String name = resultSet.getString(3);
                String picture = resultSet.getString(4);
                String description = resultSet.getString(5);
                long lastUpdated = resultSet.getLong(6);
                Date date = new Date(lastUpdated);
                Creature creature = new Creature(name,picture,description,date);
                creature.setId(id);
                creatures.add(creature);
            }
            return creatures;
        } catch (SQLException e) {
            logger.error("An error occurred when requesting a database",e);
            throw new DaoException("An error occurred when requesting a database",e);
        }
    }

    @Override
    public boolean create(Creature creature) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_CREATURE)) {
            statement.setString(1,creature.getName());
            statement.setString(2,creature.getPicture());
            statement.setString(3,creature.getDescription());
            Date date = creature.getLastUpdated();
            long lastUpdated = date.getTime();
            statement.setLong(4,lastUpdated);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error occurred while creating creatureCreature : {}",creature,e);
            throw new DaoException("Error occurred while creating creature",e);
        }
        return flag;
    }

    @Override
    public boolean update(Creature creature) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        return false;
    }

    @Override
    public Optional<Creature> get(long id) throws DaoException {
        Optional<Creature> result = Optional.empty();
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_CREATURE_BY_ID)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(1);
                String picture = resultSet.getString(2);
                String description = resultSet.getString(3);
                long lastUpdated = resultSet.getLong(4);
                Date date = new Date(lastUpdated);
                Creature creature = new Creature(name,picture,description,date);
                creature.setId(id);
                result = Optional.of(creature);
            }
        } catch (SQLException e) {
            logger.error("An error occurred when requesting a database",e);
            throw new DaoException("An error occurred when requesting a database",e);
        }
        return result;
    }
}
