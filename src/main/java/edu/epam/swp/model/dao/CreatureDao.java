package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface CreatureDao extends BaseDao<CreatureDao> {

    List<Creature> findNewCreatures() throws DaoException;
    boolean createCreature(Creature creature) throws DaoException;
    Optional<Creature> get(long id) throws DaoException;
}
