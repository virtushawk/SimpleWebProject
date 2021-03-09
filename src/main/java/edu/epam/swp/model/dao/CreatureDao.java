package edu.epam.swp.model.dao;

import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.exception.DaoException;

import java.util.List;

/**
 * CreatureDao interface contains methods for working with Creature object.
 * Extends {@link BaseDao}.
 * @see Creature
 * @author romab
 */
public interface CreatureDao extends BaseDao<Creature> {

    /**
     * Finds the newest creatures.
     * @param limit The number of creatures that the method will return.
     * @return List of creatures.
     * @throws DaoException if SQLException was thrown.
     */
    List<Creature> findNewCreatures(int limit) throws DaoException;

    /**
     * Finds most popular creatures(creatures with the most reviews).
     * @param limit The number of creatures that the method will return.
     * @return List of creatures.
     * @throws DaoException if SQLException was thrown.
     */
    List<Creature> findPopularCreatures(int limit) throws DaoException;

    /**
     * Finds user's creatures.
     * @param accountId User's id.
     * @return List of creatures.
     * @throws DaoException if SQLException was thrown.
     */
    List<Creature> findCreaturesByAccountId(long accountId) throws DaoException;

    /**
     * Finds all unchecked creatures.
     * @return List of creatures.
     * @throws DaoException if SQLException was thrown.
     */
    List<Creature> findUncheckedCreatures() throws DaoException;

    /**
     * searches creatures by name.
     * @param name String containing search name.
     * @return List of creatures.
     * @throws DaoException if SQLException was thrown.
     */
    List<Creature> search(String name) throws DaoException;

    /**
     * Finds user's unchecked creatures.
     * @param accountId User's id.
     * @return List of creatures.
     * @throws DaoException if SQLException was thrown.
     */
    List<Creature> findUncheckedCreaturesByAccountId(long accountId) throws DaoException;

    /**
     * Updates creature's image.
     * @param creatureId Creature's id.
     * @param image String containing path to the image.
     * @return True if image was updated successfully, otherwise false.
     * @throws DaoException if SQLException was thrown.
     */
    boolean updateImageById(long creatureId,String image) throws DaoException;

    /**
     * Updates User's unchecked creatures.
     * @param creatureId Creature's id.
     * @param accountId User's id.
     * @param image String containing path to the image.
     * @return true if image was updated successfully, otherwise false.
     * @throws DaoException if SQLException was thrown.
     */
    boolean updateUncheckedImageByCreatureId(long creatureId, long accountId, String image) throws DaoException;

    /**
     * Updates user's unchecked creature.
     * @param accountId User's id.
     * @param creature New creature.
     * @return true if creature was updated successfully,otherwise false.
     * @throws DaoException if SQLException was thrown.
     */
    boolean updateUncheckedCreature(long accountId,Creature creature) throws DaoException;

    /**
     * Approves creature.
     * @param creatureId Creature's id.
     * @return true if creature was approved successfully, otherwise false.
     * @throws DaoException if SQLException was thrown.
     */
    boolean approveCreature(long creatureId) throws DaoException;

    /**
     * Deletes user's creature.
     * @param accountId User's id.
     * @param creatureId creature's id.
     * @return true if creature was deleted successfully, otherwise false.
     * @throws DaoException if SQLException was thrown.
     */
    boolean delete(long accountId,long creatureId) throws DaoException;
}
