package edu.epam.swp.model.service.impl;

import edu.epam.swp.model.dao.CreatureDao;
import edu.epam.swp.model.dao.impl.CreatureDaoImpl;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.exception.DaoException;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.CreatureService;
import edu.epam.swp.model.validation.CreatureValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * The implementation of {@link CreatureService}. Contains methods to work with Creature object.
 * @author romab
 * @see Creature
 */
public class CreatureServiceImpl implements CreatureService {

    private static final Logger logger = LogManager.getLogger(CreatureServiceImpl.class);
    private static final CreatureService instance = new CreatureServiceImpl();
    private CreatureDao dao = CreatureDaoImpl.getInstance();

    private CreatureServiceImpl() {}

    /**
     * Gets instance.
     * @return the instance
     */
    public static CreatureService getInstance() {
        return instance;
    }

    /**
     * Finds new creatures.
     * @param limit number of creatures to return.
     * @return List of new creatures.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public List<Creature> findNewCreatures(int limit) throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.findNewCreatures(limit);
        } catch (DaoException e) {
            logger.error("An error occurred while finding new creatures",e);
            throw new ServiceException("An error occurred while finding new creatures",e);
        }
        return creatures;
    }

    /**
     * Finds popular creatures.
     * @param limit number of creatures to return.
     * @return List of popular creatures.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public List<Creature> findPopularCreatures(int limit) throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.findPopularCreatures(limit);
        } catch (DaoException e) {
            logger.error("An error occurred while finding popular creatures",e);
            throw new ServiceException("An error occurred while finding popular creatures",e);
        }
        return creatures;
    }

    /**
     * Creates creature.
     * @param creature Creature object.
     * @return True if creature was created, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean createCreature(Creature creature) throws ServiceException {
        boolean flag;
        String name = creature.getName();
        String description = creature.getDescription();
        if ((!CreatureValidator.isName(name)) || (!CreatureValidator.isDescription(description))) {
            logger.info("Invalid credentials for creature");
            return false;
        }
        try {
            flag = dao.create(creature);
        } catch (DaoException e) {
            logger.error("Error occurred while creating creature.",e);
            throw new ServiceException("Error occurred while creating creature",e);
        }
        return flag;
    }

    /**
     * Gets creature by id.
     * @param creatureId Creature's id.
     * @return Optional of creature.
     * @throws ServiceException if DaoException was thrown.
     */
    @Override
    public Optional<Creature> findCreature(long creatureId) throws ServiceException {
        Optional<Creature> creature;
        try {
            creature = dao.find(creatureId);
        } catch (DaoException e) {
            logger.error("An error occurred while finding a creature",e);
            throw new ServiceException("An error occurred while finding a creature",e);
        }
        return creature;
    }

    /**
     * Finds all creatures.
     * @return List of creatures.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public List<Creature> findAll() throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.findAll();
        } catch (DaoException e) {
            logger.error("An error occurred while finding all creatures",e);
            throw new ServiceException("An error occurred while finding all creatures",e);
        }
        return creatures;
    }

    /**
     * Changes creature's image.
     * @param creatureId Creature's id.
     * @param image String containing the path to the image.
     * @return True if image was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean changeImage(long creatureId, String image) throws ServiceException {
        boolean flag;
        try {
            flag = dao.updateImageById(creatureId,image);
        } catch (DaoException e) {
            logger.error("An error occurred while updating creature image",e);
            throw new ServiceException("An error occurred while updating creature image",e);
        }
        return flag;
    }

    /**
     * Changes unchecked creature's image.
     * @param creatureId Creature's id.
     * @param accountId User's id.
     * @param image String containing the path to the image.
     * @return True if image was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean changeUncheckedImage(long creatureId, long accountId, String image) throws ServiceException {
        boolean flag;
        try {
            flag = dao.updateUncheckedImageByCreatureId(creatureId,accountId,image);
        } catch (DaoException e) {
            logger.error("An error occurred while updating unchecked image",e);
            throw new ServiceException("An error occurred while updating unchecked image",e);
        }
        return flag;
    }

    /**
     * Edits creature.
     * @param creature Creature object.
     * @return True if creature was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean editCreature(Creature creature) throws ServiceException {
        boolean flag;
        String name = creature.getName();
        String description = creature.getDescription();
        if ((!CreatureValidator.isName(name)) || (!CreatureValidator.isDescription(description))) {
            logger.info("Invalid credentials for creature");
            return false;
        }
        try {
            flag = dao.update(creature);
        } catch (DaoException e) {
            logger.error("An error occurred while updating creature",e);
            throw new ServiceException("An error occurred while updating creature",e);
        }
        return flag;
    }

    /**
     * Edits unchecked creature.
     * @param accountId User's id.
     * @param creature Creature object.
     * @return true if creature was updated, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean editUncheckedCreature(long accountId, Creature creature) throws ServiceException {
        boolean flag;
        String name = creature.getName();
        String description = creature.getDescription();
        if ((!CreatureValidator.isName(name)) || (!CreatureValidator.isDescription(description))) {
            logger.info("Invalid credentials for creature");
            return false;
        }
        try {
            flag = dao.updateUncheckedCreature(accountId,creature);
        } catch (DaoException e) {
            logger.error("An error occurred while updating unchecked creature",e);
            throw new ServiceException("An error occurred while updating unchecked creature",e);
        }
        return flag;
    }

    /**
     * Deletes creature.
     * @param creatureId Creature's id.
     * @return true if Creature was deleted, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean delete(long creatureId) throws ServiceException {
        boolean flag;
        try {
            flag = dao.delete(creatureId);
        } catch (DaoException e) {
            logger.error("An error occurred while deleting creature",e);
            throw new ServiceException("An error occurred while deleting creature",e);
        }
        return flag;
    }

    /**
     * Deletes user's creature.
     * @param accountId User's id.
     * @param creatureId Creature's id.
     * @return True if creature was deleted, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean delete(long accountId, long creatureId) throws ServiceException {
        boolean flag;
        try {
            flag = dao.delete(accountId,creatureId);
        } catch (DaoException e) {
            logger.error("An error occurred while deleting creature",e);
            throw new ServiceException("An error occurred while deleting creature",e);
        }
        return flag;
    }

    /**
     * Finds user's creatures.
     * @param accountId User's id.
     * @return List of creatures.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public List<Creature> findUserCreatures(long accountId) throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.findCreaturesByAccountId(accountId);
        } catch (DaoException e) {
            logger.error("An error occurred while finding user's creatures",e);
            throw new ServiceException("An error occurred while finding user's creatures",e);
        }
        return creatures;
    }

    /**
     * Finds all unchecked creatures.
     * @return List of unchecked creatures.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public List<Creature> findUncheckedCreatures() throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.findUncheckedCreatures();
        } catch (DaoException e) {
            logger.error("An error occurred while finding unchecked creatures",e);
            throw new ServiceException("An error occurred while finding unchecked creatures",e);
        }
        return creatures;
    }

    /**
     * Approves creature.
     * @param creatureId Creature's id.
     * @return true if creature was approved, otherwise false.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public boolean approveCreature(long creatureId) throws ServiceException {
        boolean flag;
        try {
            flag = dao.approveCreature(creatureId);
        } catch (DaoException e) {
            logger.error("An error occurred while approving creature",e);
            throw new ServiceException("An error occurred while approving creature",e);
        }
        return flag;
    }

    /**
     * Finds suitable creatures
     * @param text String containing the search text.
     * @return List of creatures.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public List<Creature> search(String text) throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.search(text);
        } catch (DaoException e) {
            logger.error("An error occurred while searching",e);
            throw new ServiceException("An error occurred while searching",e);
        }
        return creatures;
    }

    /**
     * Finds user's suggested creatures.
     * @param accountId User's id.
     * @return List of suggested creatures.
     * @throws ServiceException If DaoException was thrown.
     */
    @Override
    public List<Creature> findUserSuggestedCreatures(long accountId) throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.findUncheckedCreaturesByAccountId(accountId);
        } catch (DaoException e) {
            logger.error("An error occurred while finding user's suggested creatures",e);
            throw new ServiceException("An error occurred while finding user's suggested creatures",e);
        }
        return creatures;
    }
}
