package edu.epam.swp.model.dao.impl;

import edu.epam.swp.model.dao.CreatureDao;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.entity.CreatureStatus;
import edu.epam.swp.exception.DaoException;
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
            "creatures.picture,creatures.description,creatures.last_updated FROM creatures WHERE creatures.status_id = 1 " +
            "ORDER BY creatures.last_updated DESC LIMIT 0,3";
    private static final String SELECT_CREATURE_BY_RATING_LIMIT = "SELECT reviews.creature_id,count(reviews.rating) AS rate," +
            "creatures.name,creatures.picture,creatures.description,creatures.last_updated FROM reviews INNER JOIN creatures ON " +
            "reviews.creature_id = creatures.creature_id WHERE creatures.status_id = 1 GROUP BY reviews.creature_id ORDER BY rate DESC LIMIT 0,3";
    private static final String INSERT_CREATURE = "INSERT INTO creatures(account_id,name,picture,description,status_id,last_updated) " +
            "VALUES(?,?,?,?,?,?)";
    private static final String SELECT_CREATURE_BY_ID = "SELECT creatures.name,creatures.picture," +
            "creatures.description,creatures.last_updated FROM creatures WHERE creatures.creature_id = ?";
    private static final String SELECT_CREATURE = "SELECT creatures.creature_id,creatures.name,creatures.picture,creatures.description,creatures.last_updated,AVG(reviews.rating) AS rate " +
            "FROM creatures LEFT JOIN reviews ON creatures.creature_id = reviews.creature_id WHERE creatures.status_id = 1 GROUP BY creatures.creature_id " +
            "ORDER BY creatures.last_updated DESC";
    private static final String UPDATE_PICTURE = "UPDATE creatures SET picture = ? WHERE creature_id = ?";
    private static final String UPDATE_CREATURE = "UPDATE creatures SET name = ?,description = ?,last_updated = ? WHERE creature_id = ?";
    private static final String DELETE_CREATURE = "DELETE FROM creatures WHERE creature_id = ?";
    private static final String DELETE_REVIEW = "DELETE FROM reviews WHERE creature_id = ?";
    private static final String SELECT_CREATURE_BY_ACCOUNT_ID = "SELECT creature_id,name,picture,description,last_updated " +
            "FROM creatures WHERE account_id = ? AND status_id = 1";
    private static final String SELECT_CREATURE_BY_STATUS_ID = "SELECT creature_id,name,picture,description,last_updated FROM creatures " +
            "WHERE status_id = 2";
    private static final String UPDATE_CREATURE_STATUS_ID = "UPDATE creatures SET status_id = ? WHERE creature_id = ?";
    private static final String SELECT_CREATURE_BY_NAME_LIKE = "SELECT creature_id,name,picture,description,last_updated FROM creatures " +
            "WHERE name LIKE ? AND status_id = 1";

    private CreatureDaoImpl() {}

    public static CreatureDao getInstance() {
        return instance;
    }

    @Override
    public List<Creature> findAll() throws DaoException {
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_CREATURE)) {
            ResultSet resultSet = statement.executeQuery();
            List<Creature> creatures = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String picture = resultSet.getString(3);
                String description = resultSet.getString(4);
                long lastUpdated = resultSet.getLong(5);
                double averageRating = resultSet.getDouble(6);
                Date date = new Date(lastUpdated);
                Creature creature = new Creature(name,picture,description,date);
                creature.setId(id);
                creature.setAverageRating(averageRating);
                creatures.add(creature);
            }
            return creatures;
        } catch (SQLException e) {
            logger.error("An error occurred when requesting a database",e);
            throw new DaoException("An error occurred when requesting a database",e);
        }
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
    public boolean updateImageById(long id, String image) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_PICTURE)) {
            statement.setString(1,image);
            statement.setLong(2,id);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred while requesting a database",e);
            throw new DaoException("An error occurred while requesting a database",e);
        }
        return flag;
    }

    @Override
    public List<Creature> findCreaturesById(long id) throws DaoException {
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_CREATURE_BY_ACCOUNT_ID)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            List<Creature> creatures = new ArrayList<>();
            while (resultSet.next()) {
                long creatureId = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String picture = resultSet.getString(3);
                String description = resultSet.getString(4);
                long lastUpdated = resultSet.getLong(5);
                Date date = new Date(lastUpdated);
                Creature creature = new Creature(name,picture,description,date);
                creature.setId(creatureId);
                creatures.add(creature);
            }
            return creatures;
        } catch (SQLException e) {
            logger.error("An error occurred when requesting a database",e);
            throw new DaoException("An error occurred when requesting a database",e);
        }
    }

    @Override
    public List<Creature> findUncheckedCreatures() throws DaoException {
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_CREATURE_BY_STATUS_ID)) {
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
    public boolean approveCreature(long id) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CREATURE_STATUS_ID)) {
            int statusId = 1; // 1 = Approved;
            statement.setInt(1,statusId);
            statement.setLong(2,id);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred while requesting a database",e);
            throw new DaoException("An error occurred while requesting a database",e);
        }
        return flag;
    }

    @Override
    public List<Creature> search(String name) throws DaoException {
        List<Creature> creatures = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CREATURE_BY_NAME_LIKE)) {
            String parameter = '%' + name + '%';
            statement.setString(1,parameter);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String creatureName = resultSet.getString(2);
                String picture = resultSet.getString(3);
                String description = resultSet.getString(4);
                long lastUpdated = resultSet.getLong(5);
                Date date = new Date(lastUpdated);
                Creature creature = new Creature(creatureName,picture,description,date);
                creature.setId(id);
                creatures.add(creature);
            }
        } catch (SQLException e) {
            logger.error("Error occurred while searching creatures.",e);
            throw new DaoException("Error occurred while searching creatures",e);
        }
        return creatures;
    }

    @Override
    public boolean create(Creature creature) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_CREATURE)) {
            statement.setLong(1,creature.getAccountId());
            statement.setString(2,creature.getName());
            statement.setString(3,creature.getPicture());
            statement.setString(4,creature.getDescription());
            int statusId;
            if (creature.getCreatureStatus().equals(CreatureStatus.APPROVED)){
                statusId = 1;
            } else {
                statusId = 2;
            }
            statement.setInt(5,statusId);
            Date date = creature.getLastUpdated();
            long lastUpdated = date.getTime();
            statement.setLong(6,lastUpdated);
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error occurred while creating creature. Creature : {}",creature,e);
            throw new DaoException("Error occurred while creating creature",e);
        }
        return flag;
    }

    @Override
    public boolean update(Creature creature) throws DaoException {
        boolean flag;
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CREATURE)) {
            statement.setString(1,creature.getName());
            statement.setString(2,creature.getDescription());
            statement.setLong(3,creature.getLastUpdated().getTime());
            statement.setLong(4,creature.getId());
            flag = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("An error occurred while requesting a database",e);
            throw new DaoException("An error occurred while requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        boolean flag;
        Connection connection = pool.getConnection();
        try(PreparedStatement creatureStatement = connection.prepareStatement(DELETE_CREATURE);
            PreparedStatement reviewStatement = connection.prepareStatement(DELETE_REVIEW)) {
            creatureStatement.setLong(1,id);
            reviewStatement.setLong(1,id);
            connection.setAutoCommit(false);
            reviewStatement.execute();
            flag = creatureStatement.executeUpdate() > 0;
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                logger.error("An error occurred while rolling back",e);
                throw new DaoException("An error occurred while rolling back",e);
            }
            logger.error("An error occurred while requesting a database",e);
            throw new DaoException("An error occurred while requesting a database",e);
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
