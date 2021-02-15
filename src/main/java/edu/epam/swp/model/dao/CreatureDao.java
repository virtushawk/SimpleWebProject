package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.exception.DaoException;

import java.util.List;

public interface CreatureDao extends BaseDao<Creature> {

    List<Creature> findNewCreatures() throws DaoException;
    List<Creature> findPopularCreatures() throws DaoException;
    boolean updateImageById(long id,String image) throws DaoException;
    List<Creature> findCreaturesById(long id) throws DaoException;
    List<Creature> findUncheckedCreatures() throws DaoException;
    boolean approveCreature(long id) throws DaoException;
    List<Creature> search(String name) throws DaoException;
    List<Creature> findUserUncheckedCreatures(long id) throws DaoException;
}
