package edu.epam.swp.model.service.impl;

import edu.epam.swp.model.dao.CreatureDao;
import edu.epam.swp.model.dao.impl.CreatureDaoImpl;
import edu.epam.swp.model.entity.Creature;
import edu.epam.swp.model.exception.DaoException;
import edu.epam.swp.model.exception.ServiceException;
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
}
