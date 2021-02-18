package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.exception.DaoException;

import java.util.List;

public interface CreatureDao extends BaseDao<Creature> {

    List<Creature> findNewCreatures(int limit) throws DaoException;
    List<Creature> findPopularCreatures(int limit) throws DaoException;
    boolean updateImageById(long id,String image) throws DaoException;
    boolean updateUncheckedImageById(long id,long accountId,String image) throws DaoException;
    boolean updateUncheckedCreature(long accountId,Creature creature) throws DaoException;
    List<Creature> findCreaturesById(long id) throws DaoException;
    List<Creature> findUncheckedCreatures() throws DaoException;
    boolean approveCreature(long id) throws DaoException;
    List<Creature> search(String name) throws DaoException;
    List<Creature> findUserUncheckedCreatures(long id) throws DaoException;
    boolean delete(long accountId,long creatureId) throws DaoException;
}
