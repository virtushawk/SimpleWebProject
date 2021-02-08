package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.exception.DaoException;

import java.util.List;

public interface CreatureDao extends BaseDao<Creature> {

    List<Creature> findNewCreatures() throws DaoException;
    List<Creature> findPopularCreatures() throws DaoException;
}
