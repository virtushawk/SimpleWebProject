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

public class CreatureServiceImpl implements CreatureService {

    private static final Logger logger = LogManager.getLogger(CreatureServiceImpl.class);
    private static final CreatureService instance = new CreatureServiceImpl();
    private CreatureDao dao = CreatureDaoImpl.getInstance();

    private CreatureServiceImpl() {}

    public static CreatureService getInstance() {
        return instance;
    }

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

    @Override
    public Optional<Creature> get(long id) throws ServiceException {
        Optional<Creature> creature;
        try {
            creature = dao.get(id);
        } catch (DaoException e) {
            logger.error("An error occurred while finding a creature",e);
            throw new ServiceException("An error occurred while finding a creature",e);
        }
        return creature;
    }

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

    @Override
    public boolean changeImage(long id, String image) throws ServiceException {
        boolean flag;
        try {
            flag = dao.updateImageById(id,image);
        } catch (DaoException e) {
            logger.error("An error occurred while updating creature image",e);
            throw new ServiceException("An error occurred while updating creature image",e);
        }
        return flag;
    }

    @Override
    public boolean changeUncheckedImage(long id, long accountId, String image) throws ServiceException {
        boolean flag;
        try {
            flag = dao.updateUncheckedImageByCreatureId(id,accountId,image);
        } catch (DaoException e) {
            logger.error("An error occurred while updating unchecked image",e);
            throw new ServiceException("An error occurred while updating unchecked image",e);
        }
        return flag;
    }

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

    @Override
    public boolean delete(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.delete(id);
        } catch (DaoException e) {
            logger.error("An error occurred while deleting creature",e);
            throw new ServiceException("An error occurred while deleting creature",e);
        }
        return flag;
    }

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

    @Override
    public List<Creature> findUserCreatures(long id) throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.findCreaturesByAccountId(id);
        } catch (DaoException e) {
            logger.error("An error occurred while finding user's creatures",e);
            throw new ServiceException("An error occurred while finding user's creatures",e);
        }
        return creatures;
    }

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

    @Override
    public boolean approveCreature(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.approveCreature(id);
        } catch (DaoException e) {
            logger.error("An error occurred while approving creature",e);
            throw new ServiceException("An error occurred while approving creature",e);
        }
        return flag;
    }

    // todo : xss attack
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
