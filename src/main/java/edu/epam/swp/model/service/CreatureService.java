package edu.epam.swp.model.service;

import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * CreatureService interface contains method to work with Creature object.
 * @author romab
 * @see Creature
 */
public interface CreatureService {

    /**
     * Finds new creatures.
     * @param limit number of creatures to return.
     * @return List of new creatures.
     * @throws ServiceException If DaoException was thrown.
     */
    List<Creature> findNewCreatures(int limit) throws ServiceException;

    /**
     * Finds popular creatures.
     * @param limit number of creatures to return.
     * @return List of popular creatures.
     * @throws ServiceException If DaoException was thrown.
     */
    List<Creature> findPopularCreatures(int limit) throws ServiceException;

    /**
     * Finds user's creatures.
     * @param accountId User's id.
     * @return List of creatures.
     * @throws ServiceException If DaoException was thrown.
     */
    List<Creature> findUserCreatures(long accountId) throws ServiceException;

    /**
     * Finds all unchecked creatures.
     * @return List of unchecked creatures.
     * @throws ServiceException If DaoException was thrown.
     */
    List<Creature> findUncheckedCreatures() throws ServiceException;

    /**
     * Finds all creatures.
     * @return List of creatures.
     * @throws ServiceException If DaoException was thrown.
     */
    List<Creature> findAll() throws ServiceException;

    /**
     * Finds suitable creatures
     * @param text String containing the search text.
     * @return List of creatures.
     * @throws ServiceException If DaoException was thrown.
     */
    List<Creature> search(String text) throws ServiceException;

    /**
     * Finds user's suggested creatures.
     * @param accountId User's id.
     * @return List of suggested creatures.
     * @throws ServiceException If DaoException was thrown.
     */
    List<Creature> findUserSuggestedCreatures(long accountId) throws ServiceException;

    /**
     * Gets creature by id.
     * @param creatureId Creature's id.
     * @return Optional of creature.
     * @throws ServiceException if DaoException was thrown.
     */
    Optional<Creature> findCreature(long creatureId) throws ServiceException;

    /**
     * Creates creature.
     * @param creature Creature object.
     * @return True if creature was created, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean createCreature(Creature creature) throws ServiceException;

    /**
     * Changes creature's image.
     * @param creatureId Creature's id.
     * @param image String containing the path to the image.
     * @return True if image was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean changeImage(long creatureId,String image) throws ServiceException;

    /**
     * Changes unchecked creature's image.
     * @param creatureId Creature's id.
     * @param accountId User's id.
     * @param image String containing the path to the image.
     * @return True if image was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean changeUncheckedImage(long creatureId,long accountId,String image) throws ServiceException;

    /**
     * Edits creature.
     * @param creature Creature object.
     * @return True if creature was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean editCreature(Creature creature) throws ServiceException;

    /**
     * Edits unchecked creature.
     * @param accountId User's id.
     * @param creature Creature object.
     * @return true if creature was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean editUncheckedCreature(long accountId,Creature creature) throws ServiceException;

    /**
     * Deletes creature.
     * @param creatureId Creature's id.
     * @return true if Creature was deleted, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean delete(long creatureId) throws ServiceException;

    /**
     * Deletes user's creature.
     * @param accountId User's id.
     * @param creatureId Creature's id.
     * @return True if creature was deleted, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean delete(long accountId,long creatureId) throws ServiceException;

    /**
     * Approves creature.
     * @param creatureId Creature's id.
     * @return true if creature was approved, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    boolean approveCreature(long creatureId) throws ServiceException;
}
