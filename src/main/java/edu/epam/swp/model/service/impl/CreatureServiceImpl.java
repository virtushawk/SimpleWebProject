package edu.epam.swp.model.service.impl;

import edu.epam.swp.model.dao.CreatureDao;
import edu.epam.swp.model.dao.impl.CreatureDaoImpl;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.exception.DaoException;
import edu.epam.swp.exception.ServiceException;
import edu.epam.swp.model.service.CreatureService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class CreatureServiceImpl implements CreatureService {
    private static final Logger logger = LogManager.getLogger(CreatureServiceImpl.class);
    private static final CreatureServiceImpl instance = new CreatureServiceImpl();
    private static final CreatureDao dao = CreatureDaoImpl.getInstance();

    private CreatureServiceImpl() {}

    public static CreatureServiceImpl getInstance() {
        return instance;
    }

    @Override
    public List<Creature> findNewCreatures() throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.findNewCreatures();
            return creatures;
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
    }

    @Override
    public List<Creature> findPopularCreatures() throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.findPopularCreatures();
            return creatures;
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
    }

    @Override
    public boolean createCreature(Creature creature) throws ServiceException {
        boolean flag;
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
            logger.error("An error occurred when requesting a database",e);
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return creature;
    }

    @Override
    public List<Creature> findAll() throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.findAll();
            return creatures;
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
    }

    @Override
    public boolean changeImage(long id, String image) throws ServiceException {
        boolean flag;
        try {
            flag = dao.updateImageById(id,image);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean changeUncheckedImage(long id, long accountId, String image) throws ServiceException {
        boolean flag;
        try {
            flag = dao.updateUncheckedImageById(id,accountId,image);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean editCreature(Creature creature) throws ServiceException {
        boolean flag;
        try {
            flag = dao.update(creature);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean editUncheckedCreature(long accountId, Creature creature) throws ServiceException {
        boolean flag;
        try {
            flag = dao.updateUncheckedCreature(accountId,creature);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.delete(id);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public boolean delete(long accountId, long creatureId) throws ServiceException {
        boolean flag;
        try {
            flag = dao.delete(accountId,creatureId);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public List<Creature> findUserCreatures(long id) throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.findCreaturesById(id);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return creatures;
    }

    @Override
    public List<Creature> findUncheckedCreatures() throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.findUncheckedCreatures();
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return creatures;
    }

    @Override
    public boolean approveCreature(long id) throws ServiceException {
        boolean flag;
        try {
            flag = dao.approveCreature(id);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return flag;
    }

    @Override
    public List<Creature> search(String text) throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.search(text);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return creatures;
    }

    @Override
    public List<Creature> findUserSuggestedCreatures(long id) throws ServiceException {
        List<Creature> creatures;
        try {
            creatures = dao.findUserUncheckedCreatures(id);
        } catch (DaoException e) {
            logger.error("An error occurred when requesting a database");
            throw new ServiceException("An error occurred when requesting a database",e);
        }
        return creatures;
    }
}
