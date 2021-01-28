package edu.epam.swp.model.dao.impl;

import edu.epam.swp.model.dao.CreatureDao;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.exception.DaoException;
import edu.epam.swp.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreatureDaoImpl implements CreatureDao {
    private static final Logger logger = LogManager.getLogger(CreatureDaoImpl.class);
    private static final CreatureDaoImpl instance = new CreatureDaoImpl();
    private static final ConnectionPool pool = ConnectionPool.INSTANCE;
    private static final String SELECT_CREATURE_BY_LAST_UPDATED_LIMIT = "SELECT creatures.creature_id,creatures.name," +
            "creatures.picture,creatures.description,creatures.rating,creatures.last_updated FROM creatures " +
            "ORDER BY creatures.last_updated DESC LIMIT 0,3";

    private CreatureDaoImpl() {}

    public static CreatureDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<CreatureDao> findAll() throws DaoException {
        return null;
    }

    @Override
    public CreatureDao get(String id) throws DaoException {
        return null;
    }

    @Override
    public List<Creature> findNewCreatures() throws DaoException {
        try(Connection connection = pool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_CREATURE_BY_LAST_UPDATED_LIMIT)) {
            ResultSet resultSet = statement.executeQuery();
            List<Creature> creatures = new ArrayList<>();
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String name = resultSet.getString(2);
                String picture = resultSet.getString(3);
                String description = resultSet.getString(4);
                int rating = resultSet.getInt(5);
                long lastUpdated = resultSet.getLong(6);
                Creature creature = new Creature(name,picture,description,rating,lastUpdated);
                creature.setId(id);
                creatures.add(creature);
            }
            return creatures;
        } catch (SQLException e) {
            logger.error("An error occurred when requesting a database",e);
            throw new DaoException("An error occurred when requesting a database",e);
        }
    }
}
